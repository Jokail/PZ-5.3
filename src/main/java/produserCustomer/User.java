package produserCustomer;

import java.util.Random;

public class User implements Runnable {

    private static int id;
    private final String NAME = "User" + id;
    final PrinterServer printerServer;
    private final int RANDOM_TASK = new Random().nextInt(2,6);


    public User(PrinterServer printerServer) {
        ++id;

        this.printerServer = printerServer;
    }

    @Override
    public void run() {
            try {
                this.addTask();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }

    private void addTask() throws InterruptedException {
        int readyTask = 0;

        synchronized (printerServer) {
            while (readyTask != RANDOM_TASK) {
                if (PrinterServer.isFull()) {
                    System.out.printf("%s wait \n", NAME);
                    printerServer.wait();
                }
                printerServer.add(new Task("task"));
                System.out.printf("%s add task \n", NAME);
                printerServer.notifyAll();
                readyTask++;
            }

        }
    }

}
