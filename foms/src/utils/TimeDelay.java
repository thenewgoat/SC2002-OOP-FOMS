package utils;

/**
 * The {@link TimeDelay} class provides a utility method to introduce a delay in the execution of a program.
 */
public class TimeDelay {
    /**
     * Delays the execution of the program for the specified amount of time.
     *
     * @param time the time to delay in milliseconds
     */
    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
