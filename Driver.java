import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
            Files.createDirectories(CleanerConstants.userDocumentsPath); // Make the Folder-Sorter folder if it does not
                                                                         // already exist.
            InputStream is = Driver.class.getResourceAsStream(CleanerConstants.defaultConfigPath);
            System.out.println(CleanerConstants.userConfigPath.toString());
            System.out.println(CleanerConstants.defaultConfigPath.toString());

            if (Files.notExists(CleanerConstants.userConfigPath)) {    // Copy Config.txt without replacing old one.
                Files.copy(is, CleanerConstants.userConfigPath);
            } 
        } catch (IOException e) {
            // TODO make a error pop up here.
        }

        // Make and show the GUI.
        CleanerGUI cGUI = new CleanerGUI();
        cGUI.makeGUI();
    }

}
