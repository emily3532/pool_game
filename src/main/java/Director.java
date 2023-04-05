import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Director {
    Ball buildBall(Builder builder, ArrayList<String> ls, double r){
        builder.reset();
        builder.setColour(Paint.valueOf(ls.get(0)));
        builder.setxPos(Double.parseDouble(ls.get(1)));
        builder.setxPosOG(Double.parseDouble(ls.get(1)));
        builder.setyPos(Double.parseDouble(ls.get(2)));
        builder.setyPosOG(Double.parseDouble(ls.get(2)));
        builder.setxVel(Double.parseDouble(ls.get(3)));
        builder.setyVel(Double.parseDouble(ls.get(4)));
        builder.setMass(Double.parseDouble(ls.get(5)));
        builder.setRadius(r);
        return builder.build();
    }
}
