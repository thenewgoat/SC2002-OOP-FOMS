package main;

import java.util.Scanner;

import controllers.Welcome;
import test.dataPersistenceTest;
import utils.FileCleanupUtility;

public class main {
    public static void main(String[] args) throws Exception {

        //dataPersistenceTest.test();

        //FileCleanupUtility.deleteSerFiles("foms/data"); //Resets before sessions.

        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                System.out.println("windows");
            } else {
                System.out.println("unix");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        Welcome.welcome();

         
    }
}
