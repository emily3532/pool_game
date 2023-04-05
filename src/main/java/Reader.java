import java.util.ArrayList;
import java.util.List;

public interface Reader {
    String getPath();
    List<ArrayList<String>> parse();
}
