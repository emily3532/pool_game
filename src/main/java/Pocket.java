import javafx.scene.paint.Paint;


public class Pocket {
    private Paint colour;
    private double xPos;
    private double yPos;
    private double radius;

    /**
     * Balls are instantiated using the builder method, therefore lots of getter and setter methods
     */
    public Pocket(){
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

    void setRadius(double radius){this.radius = radius;}


    Paint getColour(){
        return this.colour;
    }

    double getxPos(){
        return this.xPos;
    }

    double getyPos(){
        return this.yPos;
    }

    double getRadius(){return this.radius;}

}
