import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableReader implements Reader{
    private String path;
    public TableReader(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    public List<ArrayList<String>> parse() {
        JSONParser parser = new JSONParser();
        ArrayList<String> tvalues = new ArrayList<String>();
        try {
            Object object = parser.parse(new FileReader(this.path));

            // convert Object to JSONObject
            JSONObject jsonObject = (JSONObject) object;

            // reading the Table section:
            JSONObject jsonTable = (JSONObject) jsonObject.get("Table");

            // reading a value from the table section
            String tableColour = (String) jsonTable.get("colour");
            tvalues.add(tableColour);
            // reading a coordinate from the nested section within the table
            // note that the table x and y are of type Long (i.e. they are integers)
            Long tableX = (Long) ((JSONObject) jsonTable.get("size")).get("x");
            tvalues.add(tableX.toString());
            Long tableY = (Long) ((JSONObject) jsonTable.get("size")).get("y");
            tvalues.add(tableY.toString());
            // getting the friction level.
            // This is a double which should affect the rate at which the balls slow down
            Double tableFriction = (Double) jsonTable.get("friction");
            tvalues.add(tableFriction.toString());
            List<ArrayList<String>> tablevalues = new ArrayList<>();
            tablevalues.add(tvalues);
            return tablevalues;
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
