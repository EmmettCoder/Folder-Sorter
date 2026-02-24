import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            System.out.println(f.getAbsolutePath());
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
