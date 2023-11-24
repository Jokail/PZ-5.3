package produserCustomer;

public class Printer implements Runnable {


    private final PrinterServer printerServer;

    @Override
    public void run() {

        try {
            print();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public Printer(PrinterServer printerServer) {
        this.printerServer = printerServer;
    }

    private void print() throws InterruptedException {
        synchronized (printerServer) {
            while (true) {
                if (PrinterServer.isEmpty()) {
                    System.out.println("printer slip");
                    printerServer.wait();
                }
                if (!PrinterServer.isEmpty()) {
                    printerServer.delete();
                    System.out.println("print 1 executed");
                    printerServer.notifyAll();
                }
            }
        }
    }
}

