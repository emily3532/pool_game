public class BallReaderCreator implements ReaderCreator{
    public Reader create(String path){
        return new BallReader(path);
    }
}
