package main;

import controllers.Welcome;

/**
 * The main class of the FOMS application.
 * This class contains the entry point of the application.
 */
public class FomsApp {

    /**
     * Private constructor to prevent instantiation.
     */
    private FomsApp() {
    }

    /**
     * The entry point of the application.
     *
     * @param args the command line arguments
     * @throws Exception Throws an exception if there are issues during the execution, such as input/output errors.
     */
    public static void main(String[] args) throws Exception {

        Welcome.welcome();
    }
}
