import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;

/**
 * Handles all cleaning logic.
 * 
 * @author Emmett Grebe
 * @version 3-4-2026
 */
public class CleanerLogic {
    final static ConfigObject co = ConfigReader.read();

    /**
     * Cleans the current folder.
     */
    public static boolean cleanHere() {
        // Current directory.
        String currDirectory = System.getProperty("user.dir");
        // All files in the current directory.
        File[] files = new File(currDirectory).listFiles();

        makeFolders(currDirectory);

        // Null check.
        if (files == null)
            return false;

        // Logic.
        for (File f : files) {
            // Checks if the current file is a folder. If it is, skip.
            if (!f.isDirectory()) {
                // Some variables to avoid magic numbers and magic strings.
                int fileNameLength = f.getName().toString().length();
                int destinationFirstHalf = f.getAbsolutePath().length() - fileNameLength;
                String[] fileSplit = f.getName().split("\\.");

                String destBeginning = f.getAbsolutePath().substring(0, destinationFirstHalf);
                System.out.println(fileSplit[0]);
                // If the file name has an extension.
                if (fileSplit.length > 1) {
                    // Get the folder for the file's extension.
                    String destFolder = co.getFolderName(fileSplit[fileSplit.length - 1]);

                    // If there is not a folder for the extension, do nothing.
                    if (!(destFolder == null)) {
                        // Path of the file.
                        Path source = Paths.get(f.getAbsolutePath());
                        // Path of where the file will go.
                        Path destination = Paths.get(destBeginning + "/" + destFolder + "/" + f.getName());

                        try {
                            Files.move(source, destination);
                            // If the file already exists, add a 1 to the end of the filename.
                        } catch (FileAlreadyExistsException faee) {
                            // Get the base name (everything before the last dot)
                            String baseName = fileSplit[0];
                            String extension = fileSplit[fileSplit.length - 1];

                            // Look for the last "(" to see if there's already a number
                            int lastOpenParen = baseName.lastIndexOf("(");
                            int lastCloseParen = baseName.lastIndexOf(")");

                            if (lastOpenParen != -1 && lastCloseParen != -1 && lastCloseParen > lastOpenParen) {
                                try {
                                    // Extract the string between ( and )
                                    String numStr = baseName.substring(lastOpenParen + 1, lastCloseParen).trim();
                                    int oldNum = Integer.parseInt(numStr);
                                    int newNum = oldNum + 1;

                                    // Strip the old (n) from the base name
                                    String cleanBaseName = baseName.substring(0, lastOpenParen).trim();

                                    destination = Paths.get(destBeginning + "/" + destFolder + "/" + cleanBaseName
                                            + " (" + newNum + ")." + extension);
                                } catch (NumberFormatException e) {
                                    // If the stuff in () wasn't actually a number, treat it as a new file
                                    destination = Paths.get(
                                            destBeginning + "/" + destFolder + "/" + baseName + " (1)." + extension);
                                }
                            } else {
                                // No parentheses found, start with (1)
                                destination = Paths
                                        .get(destBeginning + "/" + destFolder + "/" + baseName + " (1)." + extension);
                            }

                            // Attempt the move with the new destination
                            try {
                                Files.move(source, destination);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException ioe) {

                        }
                    }
                }

            }

        }

        return true;
    }

    /**
     * Cleans the given folder.
     * 
     * @param folderPath
     */
    public static boolean cleanThere(String folderPath) {
        return true;
    }

    /**
     * Makes the folders given from the ConfigObject. If the folder already exists,
     * it skips the folder.
     * 
     * @param directory The directory to make the new folders in.
     * @return True if successful, false if not successful.
     */
    private static boolean makeFolders(String directory) {
        // Get all the folder names and their correlated extension.
        Collection<String> foldersList = co.getAllFolderNames();
        // Iterate over each folder.
        for (String currFolder : foldersList) {
            try {
                // Make the path of the new folder in the directory given.
                Path folderPath = Paths.get(directory + "/" + currFolder);
                // Make the folder in the directory given.
                Files.createDirectories(folderPath);
            } catch (IOException ioe) {
                return false;
            }
        }
        return true;
    }
}
