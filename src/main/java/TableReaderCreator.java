public class TableReaderCreator implements ReaderCreator{
    public Reader create(String path){
        return new TableReader(path);
    };
}
