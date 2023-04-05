import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class Ball {
    private Paint colour;
    private double xPos;
    private double yPos;
    private double xVel;
    private double yVel;
    private double mass;
    private double radius;
    private boolean pocketed;
    private double xPosOG;
    private double yPosOG;
    private BallFall bf;
    private int resetCount = 0;

    /**
     * Balls are instantiated using the builder method, therefore lots of getter and setter methods
     */
    public Ball(){
    }

    void setColour(Paint colour){
        this.colour = colour;
    };

    void setxPos(double x){
        this.xPos = x;
    }

    void setyPos(double y){
        this.yPos = y;
    }

    void setxVel(double xVel) {
        this.xVel = xVel;
    }

    void setyVel(double yVel) {
        this.yVel = yVel;
    }

    void setMass(double mass){
        this.mass = mass;
    }

    void setRadius(double radius){this.radius = radius;}

    void setPocketed(boolean pocketed){this.pocketed = pocketed;}

    void setxPosOG(double x){
        this.xPosOG = x;
    }

    void setyPosOG(double y){
        this.yPosOG = y;
    }

    void setResetCount(int r){
        resetCount = r;
    }

    int getResetCount(){
        return this.resetCount;
    }


    Paint getColour(){
        return this.colour;
    }

    double getxPos(){
        return this.xPos;
    }

    double getyPos(){
        return this.yPos;
    }

    double getxVel(){
        return this.xVel;
    }

    double getyVel(){
        return this.yVel;
    }

    double getMass(){
        return this.mass;
    }

    double getRadius(){return this.radius;}

    boolean getPocketed(){return this.pocketed;}

    /**
     * This resets the Ball so its back to original position - used when resetting the board
     */
    void reset(){
        this.xPos = xPosOG;
        this.yPos = yPosOG;
        this.xVel = 0;
        this.yVel = 0;
        this.pocketed = false;
    }

    /**
     * This holds the logic for the ball movement
     * @param friction amount of Table friction required
     */
    void tick(double friction) {
        double newxVel;
        double newyVel;
        //The friction was much too large, so this ensures its only a minute amount
        friction = friction/588;
        if ( xVel > 0){
            newxVel = xVel - friction;
        }
        else{
            newxVel = xVel + friction;
        }
        if (yVel > 0){
            newyVel = yVel - friction;
        }
        else{
            newyVel = yVel +friction;
        }
        setxVel(newxVel);
        setyVel(newyVel);
        xPos += xVel ;
        yPos += yVel ;
    }

    /**
     * This checks if the ball is still moving
     */
    boolean getMvmt(){
        //Vel never gets to 0.....
        if(Math.abs(xVel) > 0.002 || Math.abs(yVel) > 0.002 ){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This is checks for the logic required for falling into a pocket
     */
    void think(){
        if (this.colour == Color.RED) {
            bf = new BallFallRed();
        }
        else if(this.colour == Color.BLUE){
            bf = new BallFallBlue();
        }
        else{
            return;
        }
        bf.think(this);
    }
}
