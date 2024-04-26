package main;

import controllers.Welcome;
import test.dataPersistenceTest;
import utils.FileCleanupUtility;

public class FomsApp {
    public static void main(String[] args) throws Exception {

        //dataPersistenceTest.test();

        //FileCleanupUtility.deleteSerFiles("foms/data"); //Resets before sessions.

        Welcome.welcome();

    }
}
