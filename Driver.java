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
    final static Path documentsPath = Paths.get(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Folder-Sorter");
    final static Path configPath = Paths.get(documentsPath.toString() + File.separator + "Config.txt");        

    /**
     * Main method. Creates config folder and creates GUI.
     * 
     * @param args The arguments given through the command line.
     */
    public static void main(String[] args) {
        try {
            Files.createDirectories(documentsPath); // Make the Folder-Sorter folder if it does not already exist.
            String defaultConfigPath = "Config.txt";
            InputStream is = Driver.class.getResourceAsStream(defaultConfigPath);
            Files.copy(is, configPath);   // Copy Config.txt without replacing old one.
        } catch (IOException e) {
            // TODO make a error pop up here.
        }

        // Make and show the GUI.
        CleanerGUI cGUI = new CleanerGUI();
        cGUI.makeGUI();
    }

}
