package produserCustomer;

import java.util.LinkedList;
import java.util.List;

public class PrinterServer {

    private static final List<Task> printerServer = new LinkedList<>();
    public static final int CAPACITY = 10;


    public void add(Task task){
        printerServer.add(task);
    }

    public void delete(){
        printerServer.remove(0);
    }

    public static boolean isEmpty(){
       return printerServer.isEmpty();
    }

    public static boolean isFull(){
        return CAPACITY == printerServer.size();
    }
}
