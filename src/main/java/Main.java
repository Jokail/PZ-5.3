import produserCustomer.Printer;
import produserCustomer.PrinterServer;
import produserCustomer.User;
import threadSafe.BlockingStack;

import java.util.stream.IntStream;

import static lifeCycle.PrintLifeCycle.printThreadState;

public class Main {

    static Thread lifeCycle;
    static final Object lock = 1;


    public static void main(String[] args) throws InterruptedException {
        blockingStack();
    }

    static void blockingStack() {

        BlockingStack<Integer> integerBlockingStack = new BlockingStack<>();
        Runnable adder = () -> IntStream.rangeClosed(1, 10).forEach(integerBlockingStack::push);

        Runnable remover = () -> IntStream.rangeClosed(1, 5).forEach(value -> {
            integerBlockingStack.pop();
        });
        Runnable remover2 = () -> IntStream.rangeClosed(1, 5).forEach(value -> {
            integerBlockingStack.pop();
        });

        new Thread(adder).start();
        new Thread(remover).start();
        new Thread(remover2).start();

    }

    static void producerCustomer() {

        PrinterServer printerServer = new PrinterServer();
        Printer printer = new Printer(printerServer);
        Printer printer2 = new Printer(printerServer);
        User user = new User(printerServer);
        User user2 = new User(printerServer);
        User user3 = new User(printerServer);
        User user4 = new User(printerServer);
        new Thread(printer).start();
        new Thread(printer2).start();
        new Thread(user).start();
        new Thread(user2).start();
        new Thread(user3).start();
        new Thread(user4).start();

    }

    static void lifeCycleThread() throws InterruptedException {

        Thread threadToBlockTheMainThread = new Thread(() -> {
            toDoSomeJobForBlocked();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            toDoSomeJobForWaiting();
        });
        threadToBlockTheMainThread.start();

        lifeCycle = new Thread(() -> {
            toDoSomeJobForBlocked();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            toDoSomeJobForWaiting();
        });
        printThreadState(lifeCycle);

        lifeCycle.start();
        printThreadState(lifeCycle);

        Thread.sleep(100);
        printThreadState(lifeCycle);


        Thread.sleep(200);
        printThreadState(lifeCycle);

        Thread.sleep(700);
        printThreadState(lifeCycle);

        Thread.sleep(500);
        printThreadState(lifeCycle);

    }

    static private synchronized void toDoSomeJobForBlocked() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    static private void toDoSomeJobForWaiting() {
        synchronized (lock){
            try {
                if (Thread.currentThread() == lifeCycle) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock.notifyAll();
        }
    }

}
