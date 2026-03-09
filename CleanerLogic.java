import java.io.*;
import java.nio.file.*;
import java.util.Collection;

/**
 * Handles all cleaning logic.
 * 
 * @author Emmett Grebe
 * @version 3-5-2026
 */
public class CleanerLogic {
    static ConfigObject co;

    final static String jarName = "Folder-Cleaner";

    /**
     * Cleans the given folder.
     * 
     * @param folderPath
     */
    public static boolean cleanThere(String directory) {
        co = ConfigReader.read();
        File[] files = new File(directory).listFiles();     // All files in the directory.

        makeFolders(directory);

        if (files == null) return false;  

        // Logic.
        for (File f : files) {
            if (!f.isDirectory()) {     // Checks if the current file is a folder. If it is, skip.
                // Some variables to avoid magic numbers and magic strings.
                int fileNameLength = f.getName().toString().length();
                int destinationFirstHalf = f.getAbsolutePath().length() - fileNameLength;
                String[] fileSplit = f.getName().split("\\.");

                String destBeginning = f.getAbsolutePath().substring(0, destinationFirstHalf);

                if (destBeginning.equals(jarName)) continue;   // Check if the file is this program. If so, skip it.
                
                if (fileSplit.length > 1) {                                                 // If the file name has an extension. If not, skip.
                    String destFolder = co.getFolderName(fileSplit[fileSplit.length - 1]);  // Get the folder for the file's extension.

                    if (!(destFolder == null)) {                        // If there is not a folder for the extension, do nothing.
                        Path source = Paths.get(f.getAbsolutePath());   // Path of the file.
                        Path destination = Paths.get(destBeginning +    // Path of where the file will go.
                                                     File.separator + 
                                                     destFolder + 
                                                     File.separator + 
                                                     f.getName());

                        try {
                            Files.move(source, destination);
                        } catch (FileAlreadyExistsException faee) {      // If the file already exists, add a 1 to the end of the filename.
                            // Get the base name (everything before the last dot)
                            String baseName = fileSplit[0];
                            String extension = fileSplit[fileSplit.length - 1];

                            // Look for the last "(" to see if there's already a number
                            int lastOpenParen = baseName.lastIndexOf("(");
                            int lastCloseParen = baseName.lastIndexOf(")");

                            if (lastOpenParen != -1 && lastCloseParen != -1 && lastCloseParen > lastOpenParen) {
                                try {
                                    String numStr = baseName.substring(lastOpenParen + 1, lastCloseParen).trim(); // Extract the string between ( and )
                                    int oldNum = Integer.parseInt(numStr);
                                    int newNum = oldNum + 1;

                                    
                                    String cleanBaseName = baseName.substring(0, lastOpenParen).trim();   // Strip the old (n) from the base name

                                    destination = Paths.get(destBeginning + 
                                                            File.separator + 
                                                            destFolder + 
                                                            File.separator + 
                                                            cleanBaseName +
                                                            " (" + newNum + ")." + 
                                                            extension);
                                } catch (NumberFormatException e) {     // If the stuff in () wasn't actually a number, treat it as a new file
                                    destination = Paths.get(destBeginning + 
                                                            File.separator + 
                                                            destFolder + 
                                                            File.separator + 
                                                            baseName + 
                                                            " (1)." + 
                                                            extension);
                                }
                            } else {   // No parentheses found, start with (1)
                                
                                destination = Paths
                                        .get(destBeginning + File.separator + destFolder + File.separator + baseName + " (1)." + extension);
                            }

                            
                            try {  // Attempt the move with the new destination
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
     * Makes the folders given from the ConfigObject. If the folder already exists,
     * it skips the folder.
     * 
     * @param directory The directory to make the new folders in.
     * @return True if successful, false if not successful.
     */
    private static boolean makeFolders(String directory) {
        Collection<String> foldersList = co.getAllFolderNames();                      // Get all the folder names and their correlated extension.
        for (String currFolder : foldersList) {                                       // Iterate over each folder.
            try {
                Path folderPath = Paths.get(directory + File.separator + currFolder); // Make the path of the new folder in the directory given.
                Files.createDirectories(folderPath);                                  // Make the folder in the directory given.
            } catch (IOException ioe) {
                return false;
            }
        }
        return true;
    }
}
