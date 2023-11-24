package lifeCycle;

public class PrintLifeCycle {

    public static void printThreadState(Thread threadToCheck) {
        System.out.println("The thread is in the \"" + threadToCheck.getState() + "\" state.");
    }

}
