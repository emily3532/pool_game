import javafx.scene.paint.Paint;

public class BallBuilder implements Builder{
    private Ball ball;
    BallBuilder(){
        reset();
    }

    public void reset() {
        ball = new Ball();
    }

    @Override
    public void setColour(Paint colour) {
        ball.setColour(colour);
    }

    @Override
    public void setxPos(double xPos){
        ball.setxPos(xPos);
    }

    @Override
    public void setyPos(double yPos){
        ball.setyPos(yPos);
    }

    @Override
    public void setxVel(double xVel) {
        ball.setxVel(xVel);
    }

    @Override
    public void setyVel(double yVel) {
        ball.setyVel(yVel);
    }

    @Override
    public void setMass(double mass){
        ball.setMass(mass);
    }

    @Override
    public void setxPosOG(double x) {
        ball.setxPosOG(x);
    }

    @Override
    public void setyPosOG(double y) {
        ball.setyPosOG(y);
    }

    @Override
    public void setRadius(double r) {
        ball.setRadius(r);
    }

    /**
     * @return a new ball
     */
    public Ball build(){
        Ball b = this.ball;
        return b;
    }
}
