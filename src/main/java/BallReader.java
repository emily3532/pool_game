import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BallReader implements Reader{
    private String path;

    public BallReader(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    /**
     * This reads the ball part of the JSON file
     * @return List of Arrays that have string values for each individual ball.
     */
    public List<ArrayList<String>> parse() {
        JSONParser parser = new JSONParser();
        List<ArrayList<String>> ballvalues = new ArrayList<>();
        try {
            Object object = parser.parse(new FileReader(path));

            // convert Object to JSONObject
            JSONObject jsonObject = (JSONObject) object;

            // reading the "Balls" section:
            JSONObject jsonBalls = (JSONObject) jsonObject.get("Balls");

            // reading the "Balls: ball" array:
            JSONArray jsonBallsBall = (JSONArray) jsonBalls.get("ball");

            // reading from the array:
            for (Object obj : jsonBallsBall) {
                JSONObject jsonBall = (JSONObject) obj;
                ArrayList<String> bval = new ArrayList<>();
                // the ball colour is a String
                String colour = (String) jsonBall.get("colour");
                bval.add(colour);
                // the ball position, velocity, mass are all doubles
                Double positionX = (Double) ((JSONObject) jsonBall.get("position")).get("x");
                bval.add(positionX.toString());
                Double positionY = (Double) ((JSONObject) jsonBall.get("position")).get("y");
                bval.add(positionY.toString());
                Double velocityX = (Double) ((JSONObject) jsonBall.get("velocity")).get("x");
                bval.add(velocityX.toString());
                Double velocityY = (Double) ((JSONObject) jsonBall.get("velocity")).get("y");
                bval.add(velocityY.toString());
                Double mass = (Double) jsonBall.get("mass");
                bval.add(mass.toString());
                ballvalues.add(bval);
            }
            return ballvalues;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
