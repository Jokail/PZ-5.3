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
        locker.lock();
        try {
            while (stack.size() == CAPACITY) {
                isFull.await();
            }
            stack.push(task);
            System.out.printf("element %s was add \n", task );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            isFull.signalAll();
            isEmpty.signalAll();
            locker.unlock();
        }

    }

    public T pop() {
        locker.lock();
        try {
            while (stack.isEmpty()) {
                isEmpty.await();
            }
            T element = stack.pop();
            System.out.printf("element %s was pop\n", element);
            return element;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            isFull.signalAll();
            isEmpty.signalAll();
            locker.unlock();
        }
    }
}
