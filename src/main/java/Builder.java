import javafx.scene.paint.Paint;

public interface Builder {
    void reset();
    void setColour(Paint colour);
    void setxPos(double xPos);
    void setyPos(double yPos);
    void setxVel(double xVel);
    void setyVel(double yVel);
    void setMass(double mass);
    void setxPosOG(double x);
    void setyPosOG(double y);
    void setRadius(double r);
    Ball build();
}
