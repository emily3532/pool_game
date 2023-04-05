import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<ArrayList<String>> tablevalues;
    private List<Ball> balls;
    private GameWindow window;
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Creates the JSON file readers and the Game window from a Table
     */
    private void create(){
        //Todo potentially later, make the configPath configureable
        String configPath = "src/main/resources/config.json";


        ReaderCreator tcreator = new TableReaderCreator();
        Reader treader = tcreator.create(configPath);
        this.tablevalues = treader.parse();

        ReaderCreator bcreator = new BallReaderCreator();
        Reader breader = bcreator.create(configPath);
        List<ArrayList<String>> ballvalues = breader.parse();

        Director director = new Director();
        BallBuilder bbuilder = new BallBuilder();
        this.balls = new ArrayList<>();
        for(ArrayList<String> bval : ballvalues){
            director.buildBall(bbuilder,bval, 10);
            Ball b = bbuilder.build();
            balls.add(b);
        }
        this.window = new GameWindow(new Table(tablevalues.get(0), balls,1.0/60));
    }

    /**
     * Start the stage and running of the game
     */
    @Override
    public void start(Stage primaryStage) {
        create();
        primaryStage.setTitle("Pool Game");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
        window.run();

    }
}
