import java.lang.reflect.Type;
import java.util.ArrayList;
interface DataService{
    abstract public void showList();
    abstract public void writeToFIle(String fileName, ArrayList<Type> list );
    abstract public ArrayList<Type> readFromFile(String fileName);
    abstract public void add();
    abstract public void remove();
    abstract public boolean search(ArrayList<Type> list, Type value);
}