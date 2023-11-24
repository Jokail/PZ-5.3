import produserCustomer.Printer;
import produserCustomer.PrinterServer;
import produserCustomer.User;
import threadSafe.BlockingStack;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

import static lifeCycle.PrintLifeCycle.printThreadState;

public class Main {

    static Thread lifeCycle;


    public static void main(String[] args) throws InterruptedException {

        blockingStack();

    }

    static void blockingStack() {

        BlockingStack<Integer> integerBlockingStack = new BlockingStack<>();
        Runnable adder = () -> IntStream.of(new Random().nextInt(1,10)).forEach(integerBlockingStack::push);
        Runnable remover = () -> IntStream.of(5).forEach(value -> integerBlockingStack.pop());
        Runnable remover2 = () -> IntStream.of(5).forEach(value -> integerBlockingStack.pop());

        new Thread(adder).start();
        new Thread(remover2).start();
        new Thread(remover).start();

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

    static void lifeCycle() throws InterruptedException {

        Thread threadByBlocked = new Thread(() -> {
        });
        threadByBlocked.start();

        lifeCycle = new Thread(() -> {
        });

        printThreadState(lifeCycle);
        lifeCycle.start();
        printThreadState(lifeCycle);

        Thread.sleep(200);

        printThreadState(lifeCycle);

    }

}
