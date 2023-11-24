package threadSafe;

import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingStack<T> {
    private final Stack<T> stack = new Stack<>();
    private final Lock locker = new ReentrantLock();
    private static final int CAPACITY = 4;
    private final Condition isFull = locker.newCondition();
    private final Condition isEmpty = locker.newCondition();

    public void push(T task) {

        stack.push(task);

    }

    public T pop() {
        locker.lock();
        try {
            while (stack.isEmpty()) {
                isEmpty.await();
            }
            System.out.println("element was pop");
            return stack.pop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            isEmpty.signalAll();
            locker.unlock();
        }
    }
}
