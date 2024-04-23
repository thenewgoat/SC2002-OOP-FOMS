package utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Utility class for cleaning up .ser files in a specified directory.
 */
public class FileCleanupUtility {

    /**
     * Deletes all .ser files in the specified directory.
     *
     * @param directoryPath the path of the directory to clean up
     */
    public static void deleteSerFiles(String directoryPath) {
        File directory = new File(directoryPath);

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".ser");
            }
        };

        File[] files = directory.listFiles(filter);

        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    System.err.println("Failed to delete " + file.getAbsolutePath());
                } else {
                    System.out.println(file.getAbsolutePath() + " deleted successfully.");
                }
            }
        } else {
            System.out.println("No .ser files found or directory does not exist.");
        }
    }
}

