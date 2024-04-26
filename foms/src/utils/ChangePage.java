package utils;


import java.io.IOException;

/**
 * The ChangePage class is responsible for changing the console output screen to a new blank screen.
 * This is done using the appropriate command depending on the operating system.
 */
public class ChangePage {

    /**
     * Changes the console output screen to a new blank screen.
     * This is done using the appropriate command depending on the operating system.
     * The command for Windows is "cls", and for Unix-like systems it is "\033[H\033[2J".
     * 
     * <p>Process Builder is run twice to ensure that the screen is cleared.
     */
    public static void changePage() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("/bin/bash", "-c", "clear").inheritIO().start().waitFor();
                new ProcessBuilder("/bin/bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
