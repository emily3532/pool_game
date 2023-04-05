import java.awt.*;

public class Cue {
    private double xPos;
    private double yPos;
    private double xPosEND;
    private double yPosEND;
    private double drawBack;

    public Cue(double x, double y, double xPosEND, double yPosEND, double drawBack){
        this.xPos = x;
        this.yPos = y;
        this.drawBack = drawBack;
        this.xPosEND = xPosEND;
        this.yPosEND = yPosEND;
    }

    public double getDrawBack(){
        return this.drawBack;
    }

    public double getxPos(){
        return this.xPos;
    }

    public double getyPos(){
        return this.yPos;
    }

    public double getxPosEND(){
        return this.xPosEND;
    }

    public double getyPosEND(){
        return this.yPosEND;
    }
}
