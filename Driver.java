import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The driver for Folder Cleaner.
 * 
 * @author Emmett Grebe
 * @version 2-19-2026
 */
public class Driver {
    /**
     * Main method. Creates config folder and creates GUI.
     * 
     * @param args The arguments given through the command line.
     */
    public static void main(String[] args) {
        // Make the Folder-Seperator folder in user's documents if it does not already exist.
        Path documentsPath = Paths.get(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Folder-Sorter");
        try {
            Files.createDirectories(documentsPath);
        } catch (IOException e) {
            // TODO make a error pop up here.
        }

        // Make the GUI.
        CleanerGUI cGUI = new CleanerGUI();
        cGUI.makeGUI();
    }

}
