import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private final Paint colour;
    private final double friction;
    private final double width;
    private final double height;
    private final double g;
    private long tickCount = 0;
    private static List<Ball> balls;
    private static List<Pocket> pockets;
    private boolean mvmt = false;


    Table(ArrayList<String> ls, List<Ball> b, double frameDuration){
        this.colour = Paint.valueOf(ls.get(0));
        this.friction = Double.parseDouble(ls.get(3));
        this.width = Double.parseDouble(ls.get(1));
        this.height = Double.parseDouble(ls.get(2));
        this.balls = b;
        this.pockets = addPockets();
        g = 1.0 * frameDuration;

    }
    /**
     * Create "Pockets", since these are read thru a JSON file, I have created ArrayLists with the values.
     * @return List of 6 pocket classes
     * **/
    private List<Pocket> addPockets(){
        List<ArrayList<String>> pvalues = new ArrayList<>();
        ArrayList<String> p1 = new ArrayList<>(){
            {
                add("CHOCOLATE");
                add("5");
                add("5");
                add("20");
            }
        };
        Double p2_x = this.width -5;
        ArrayList<String> p2 = new ArrayList<>(){
            {
                add("CHOCOLATE");
                add(p2_x.toString());
                add("5");
                add("20");
            }
        };
        Double p3_y = this.height - 5;
        ArrayList<String> p3 = new ArrayList<>(){
            {
                add("CHOCOLATE");
                add("5");
                add(p3_y.toString());
                add("20");
            }
        };
        ArrayList<String> p4 = new ArrayList<>(){
            {
                add("CHOCOLATE");
                add(p2_x.toString());
                add(p3_y.toString());
                add("20");
            }
        };
        Double p5_x = this.width/2 - 5;
        ArrayList<String> p5 = new ArrayList<>(){
            {
                add("CHOCOLATE");
                add(p5_x.toString());
                add("5");
                add("20");
            }
        };
        ArrayList<String> p6 = new ArrayList<>(){
            {
                add("CHOCOLATE");
                add(p5_x.toString());
                add(p3_y.toString());
                add("20");
            }
        };

        pvalues.add(p1);
        pvalues.add(p2);
        pvalues.add(p3);
        pvalues.add(p4);
        pvalues.add(p5);
        pvalues.add(p6);

        //Using the same method to make balls to make pockets here. Radius is bigger.
        List<Pocket> pockets = new ArrayList<>();
        for(ArrayList<String> pval : pvalues){
            Pocket p = new Pocket();
            p.setColour(Paint.valueOf(pval.get(0)));
            p.setxPos(Double.parseDouble(pval.get(1)));
            p.setyPos(Double.parseDouble(pval.get(2)));
            p.setRadius(Double.parseDouble(pval.get(3)));
            pockets.add(p);
        }
        return pockets;
    }

    Paint getColour(){return colour;}

    double getHeight() {
        return height;
    }

    double getWidth() {
        return width;
    }

    List<Ball> getBalls() {
        return balls;
    }

    List<Pocket> getPockets(){return pockets;}

    boolean getMvmt(){return this.mvmt;}

    void setMvmt(boolean m){this.mvmt = m;}

    /**
     * Checks if a Ball will collide with the any of the balls already on the table when reset, static to be accessed outside the table.
     * @param b Ball that we want to check
     * @return boolean whether it collides - True, or doesn't collide - False
     * **/
    static boolean resetCheck(Ball b){
        for(Ball ball: balls){
            return checkCollision(ball, b);
        }
        return false;
    }

    /**
     * Contains logic for all ball movment on the table, including hitting walls, hitting pockets, hitting each other and adding friction
     * logic referenced from tutorial tasks: coloured balls
     * **/
    void tick() {
        tickCount++;

        for(Ball ball: balls) {
            if(getMvmt()){
                ball.tick(friction);
            }

            if (ball.getxPos() + ball.getRadius() > width) {
                ball.setxPos(width - ball.getRadius());
                ball.setxVel(ball.getxVel() * -1/2);
            }
            if (ball.getxPos() - ball.getRadius() < 0) {
                ball.setxPos(0 + ball.getRadius());
                ball.setxVel(ball.getxVel() * -1/2);
            }
            if (ball.getyPos() + ball.getRadius() > height) {
                ball.setyPos(height - ball.getRadius());
                ball.setyVel(ball.getyVel() * -1/2);
            }
            if (ball.getyPos() - ball.getRadius() < 0) {
                ball.setyPos(0 + ball.getRadius());
                ball.setyVel(ball.getyVel() * -1/2);
            }

            for(Ball ballB: balls) {
                if (checkCollision(ball, ballB)) {
                    handleCollision(ball, ballB);
                }
            }
            for(Pocket poc: pockets){
                if (checkCollisionPoc(ball, poc)){
                    ball.setPocketed(true);
                    ball.think();
                }
            }
        }
    }

    /**
     * Finds X and Y velocities using simple physics formula velocity = magnitude<cos theta, sin theta>
     * @param angle angle created from cue Stick vector
     * @param mag magnitude created from cue Stick vector, also called drawback - so how long the line is
     * @return ArrayList<Double> where first value is X velocity and second is Y velocity of the stick
     * **/
    private ArrayList<Double> findVel(double angle, double mag){
        ArrayList<Double> coords = new ArrayList<>();
        Double xCoord = mag * Math.cos(angle);
        Double yCoord = mag * Math.sin(angle);
        coords.add(xCoord);
        coords.add(yCoord);
        return coords;
    }

    /**
     * Using the collision logic of balls, creating a fake ball vector from the Cue Stick to hit the Cue ball
     * @param cue Cue ball to be hit
     * @param cuestick Stick that is hitting the ball
     * **/
    public void hitCue(Ball cue, Cue cuestick){
        Ball fake = new Ball();
        //calculate angle of cuestick vector and get velocity of the stick to hit Cue
        Double angle = Math.atan2(cuestick.getyPos()-cuestick.getyPosEND(), cuestick.getxPos()-cuestick.getxPosEND());
        ArrayList<Double> cueCoord = findVel(angle, cuestick.getDrawBack());
        fake.setxPos(cuestick.getxPosEND());
        fake.setyPos(cuestick.getyPosEND());
        fake.setxVel(cueCoord.get(0)/2);
        fake.setyVel(cueCoord.get(1)/2);
        fake.setMass(1);
        handleCollision(cue, fake);
    }

    /**
     * checks collision of a ball and a pocket ONLY
     *  logic referenced from tutorial tasks: coloured balls
     * @param ballA the actual ball
     * @param pocB a pocket
     * @return True if ball in pocket, false if not
     * **/
    private boolean checkCollisionPoc(Ball ballA, Pocket pocB) {
        if(ballA.getPocketed()){
            return false;
        }

        //Had to reduce the radius size of Pocket ball so that the ball didn't fall in right from the corners of the pocket
        return Math.abs(ballA.getxPos() - pocB.getxPos()) < ballA.getRadius() + pocB.getRadius()-5 &&
                Math.abs(ballA.getyPos() - pocB.getyPos()) < ballA.getRadius() + pocB.getRadius()-5;
    }

    /**
     * checks collision of a ball and another ball
     * logic referenced from tutorial tasks: coloured balls
     * @param ballA ball
     * @param ballB a pocket
     * @return True if ball in pocket, false if not
     * **/
    private static boolean checkCollision(Ball ballA, Ball ballB) {
        if(ballA.getPocketed() || ballB.getPocketed()){
            return false;
        }

        if (ballA == ballB) {
            return false;
        }

        return Math.abs(ballA.getxPos() - ballB.getxPos()) < ballA.getRadius() + ballB.getRadius() &&
                Math.abs(ballA.getyPos() - ballB.getyPos()) < ballA.getRadius() + ballB.getRadius();
    }

    /**
     * checks collision of a ball and another ball
     * logic referenced from tutorial tasks: coloured balls
     * @param ballA ball
     * @param ballB a pocket
     * @return True if ball in pocket, false if not
     * **/
    private void handleCollision(Ball ballA, Ball ballB) {
        //Properties of two colliding balls
        Point2D posA = new Point2D(ballA.getxPos(), ballA.getyPos());
        Point2D posB = new Point2D(ballB.getxPos(), ballB.getyPos());
        Point2D velA = new Point2D(ballA.getxVel(), ballA.getyVel());
        Point2D velB = new Point2D(ballB.getxVel(), ballB.getyVel());

        //calculate the axis of collision
        Point2D collisionVector = posB.subtract(posA);
        collisionVector = collisionVector.normalize();

        //the proportion of each balls velocity along the axis of collision
        double vA = collisionVector.dotProduct(velA);
        double vB = collisionVector.dotProduct(velB);

        //if balls are moving away from each other do nothing
        if (vA <= 0 && vB >= 0) {
            return;
        }

        //double mR = massB/massA;
        double mR = ballB.getMass()/ballA.getMass();

        //The velocity of each ball after a collision can be found by solving the quadratic equation
        //given by equating momentum energy and energy before and after the collision and finding the
        //velocities that satisfy this
        //-(mR+1)x^2 2*(mR*vB+vA)x -((mR-1)*vB^2+2*vA*vB)=0
        //first we find the discriminant
        double a = -(mR + 1);
        double b = 2 * (mR * vB + vA);
        double c = -((mR - 1) * vB * vB + 2 * vA * vB);
        double discriminant = Math.sqrt(b * b - 4 * a * c);
        double root = (-b + discriminant)/(2 * a);

        //only one of the roots is the solution, the other pertains to the current velocities
        if (root - vB < 0.01) {
            root = (-b - discriminant)/(2 * a);
        }

        //The resulting changes in velocity for ball A and B
        Point2D deltaVA = collisionVector.multiply(mR * (vB - root));
        Point2D deltaVB = collisionVector.multiply(root - vB);
        ballA.setxVel(ballA.getxVel() + deltaVA.getX());
        ballA.setyVel(ballA.getyVel() + deltaVA.getY());
        ballB.setxVel(ballB.getxVel() + deltaVB.getX());
        ballB.setyVel(ballB.getyVel() + deltaVB.getY());

    }

}


