import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

/**
 * Handles all cleaning logic.
 * 
 * @author Emmett Grebe
 * @version 2-19-2026
 */
public class CleanerLogic {
    final static ConfigObject co = ConfigReader.read();

    /**
     * Cleans the current folder.
     */
    public static boolean cleanHere() {
        System.out.println(co.getAllFolderNames());
        String currDirectory = System.getProperty("user.dir");
        File[] files = new File(currDirectory).listFiles();

        makeFolders(currDirectory);

        // Null check.
        if (files == null)
            return false;

        // Logic.
        for (File f : files) {
            // System.out.println(f.getAbsolutePath());

            // Checks if the current file is a folder. If it is, skip.
            if (!f.isDirectory()) {
                // Some variables to avoid magic numbers and magic strings.
                int fileNameLength = f.getName().toString().length();
                int destinationFirstHalf = f.getAbsolutePath().length() - fileNameLength;
                String[] fileSplit = f.getName().split("\\.");
                
                String destBeginning = f.getAbsolutePath().substring(0, destinationFirstHalf);
                System.out.println(fileSplit[0]);
                if (fileSplit.length > 1) {
                String destFolder = co.getFolderName(fileSplit[1]);

                if (!(destFolder == null)) {
                    Path source = Paths.get(f.getAbsolutePath());
                    Path destination = Paths.get(destBeginning + "/" + destFolder + "/" + f.getName());

                    //System.out.println(destination.toAbsolutePath());
                    
                    try {
                        Files.move(source, destination);
                    // If the file already exists, add a 1 to the end of the filename.
                    } catch (FileAlreadyExistsException faee) {
                        String[] splitByPerenthesis = fileSplit[0].split("\\(");
                        //System.out.println(splitByPerenthesis[0]);
                        if (splitByPerenthesis.length > 1) {
                            int oldNum = Integer.parseInt(splitByPerenthesis[splitByPerenthesis.length-2]);
                            int newNum = oldNum + 1;
                            destination = Paths.get(destBeginning + "/" + destFolder + "/" + fileSplit[0] + "\\(" + newNum + "\\)" + fileSplit[1]);
                            try {
                                Files.move(source, destination);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            destination = Paths.get(destBeginning + "/" + destFolder + "/" + fileSplit[0] + "\\(0\\)" + fileSplit[1]);
                            try {
                                Files.move(source, destination);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
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
        Collection<String> foldersList = co.getAllFolderNames();
        for (String currFolder : foldersList) {
            try {
                Path folderPath = Paths.get(directory + "/" + currFolder);
                Files.createDirectories(folderPath);
            } catch (IOException ioe) {
                return false;
            }
        }
        return true;
    }
}
