import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        try {
            Files.createDirectories(CleanerConstants.documentsPath); // Make the Folder-Sorter folder if it does not already exist.
            String defaultConfigPath = "Resources/Config.txt";
            InputStream is = Driver.class.getResourceAsStream(defaultConfigPath);
            Files.copy(is, CleanerConstants.configPath);   // Copy Config.txt without replacing old one.
        } catch (IOException e) {
            // TODO make a error pop up here.
        }

        // Make and show the GUI.
        CleanerGUI cGUI = new CleanerGUI();
        cGUI.makeGUI();
    }

}
