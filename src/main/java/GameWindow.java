import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

class GameWindow {
    private final GraphicsContext gc;
    private Scene scene;
    private Table model;
    private boolean dragging;
    private double mouseX;
    private double mouseY;
    private Ball cueBall;
    private Pane pane;
    private Canvas canvas;
    private Cue cue;
    private boolean endGame = false;
    private boolean winGame = false;
    private boolean moving = false;

    /**
     * Initialises GameWindow from table and prepares mouse click events
     */
    GameWindow(Table model) {
        this.model = model;
        this.pane = new Pane();
        this.scene = new Scene(pane, model.getWidth(), model.getHeight());
        this.canvas = new Canvas(model.getWidth(),model.getHeight());
        this.scene.setFill(model.getColour());
        gc = canvas.getGraphicsContext2D();
        this.pane.getChildren().add(canvas);
        for(Ball b: model.getBalls()) {
            if (Paint.valueOf("WHITE") == b.getColour()) {
                cueBall = b;
            }
        }
        //Ensures that mouse clicking sets X and Y of mouse to track
        canvas.setOnMouseClicked((MouseEvent event) -> {
            if(!moving){
                mouseX = event.getX();
                mouseY = event.getY();
                dragging = false;
            }
        });
        //Ensures that mouse dragging will follow mouse X and Y and create a cue stick
        canvas.setOnMouseDragged((MouseEvent e) -> {
            if(!moving){
                dragging = true;
                mouseX = e.getX();
                mouseY = e.getY();
                this.cue = new Cue(cueBall.getxPos(), cueBall.getyPos(), mouseX, mouseY, Point2D.distance(mouseX, mouseY, cueBall.getxPos(), cueBall.getyPos()));
            }
        });

    }

    /**
     * Resets Balls on the scene and restarts the game
     */
    void resetScene(){
        for(Ball b: model.getBalls()){
            b.reset();
            b.setResetCount(0);
        }
        endGame = false;
    }

    Scene getScene() {
        return this.scene;
    }

    void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Creates Cue actions and sets movement of balls to true
     */
    private void createCue(){
        canvas.setOnMouseReleased((MouseEvent e) ->{
            if(!moving){
                dragging = false;
                model.setMvmt(true);
                model.hitCue(cueBall, this.cue);
            }
        });
    }

    /**
     * Checks if Cue Ball has been clicked first before creating cue etc
     */
    private void checkMouse(){
        if(mouseX<= cueBall.getxPos() + cueBall.getRadius() +1 && mouseX >= cueBall.getxPos() - cueBall.getRadius() -1 && mouseY<= cueBall.getyPos() + cueBall.getRadius() +1 && mouseY>= cueBall.getyPos() - cueBall.getRadius() -1 ){
            dragging = true;
        }
    }

    /**
     * Creates all shape drawings and adds it to the GraphicsContext
     */
    private void draw() {
        //Checks if the Cue Ball is still there first
        if(cueBall.getPocketed()){
            endGame = true;
        }
        //Checks if game screen needs restart
        if(endGame){
            try {
                Thread.sleep(300);
                resetScene();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Checks if game has already been won
        if(winGame){
            System.out.println("winning");
            gc.clearRect(0, 0, model.getWidth(), model.getHeight());
            gc.setFill(Paint.valueOf("PINK"));
            gc.fillText(
                    "Win & Bye :)", 40, 200
            );
            return;
        }

        moving = false;
        model.tick();
        gc.clearRect(0, 0, model.getWidth(), model.getHeight());

        //Draws pockets
        for (Pocket poc: model.getPockets()) {
            gc.setFill(poc.getColour());
            gc.fillOval(poc.getxPos() - poc.getRadius(),
                    poc.getyPos() - poc.getRadius(),
                    poc.getRadius() * 2,
                    poc.getRadius() * 2);
        }

        winGame = true;
        //Draws balls
        for (Ball ball: model.getBalls()) {
            if(!ball.getPocketed()){
                //If any of the balls are still on screen, the game has yet to be won
                if(ball != cueBall){
                    winGame = false;
                }
                gc.setFill(ball.getColour());
                gc.fillOval(ball.getxPos() - ball.getRadius(),
                        ball.getyPos() - ball.getRadius(),
                        ball.getRadius() * 2,
                        ball.getRadius() * 2);
                if(ball.getMvmt()){
                    moving = true;
                }
            }
        }

        //Once all the balls stopped moving, then can create cue and go again
        if(!moving){
            checkMouse();
            if(dragging){
                createCue();
                gc.strokeLine(this.cue.getxPos(), this.cue.getyPos(), this.cue.getxPosEND(), this.cue.getyPosEND());
            };
        }
    }
}
