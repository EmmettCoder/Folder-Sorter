import java.io.*;
import java.nio.file.Files;

import javax.swing.JOptionPane;

import GUI.SorterGUI;
import Logic.SorterConstants;

/**
 * The driver for Folder Sorter.
 * 
 * @author Emmett Grebe
 * @version 3-15-2026
 */
public class Driver {

    /**
     * Main method. Creates config folder and creates GUI.
     * 
     * @param args The arguments given through the command line.
     */
    public static void main(String[] args) {
        try {
            Files.createDirectories(SorterConstants.userDocumentsPath); // Make the Folder-Sorter folder if it does not
                                                                         // already exist.
            InputStream is = Driver.class.getResourceAsStream(SorterConstants.defaultConfigPath);

            if (Files.notExists(SorterConstants.userConfigPath)) {    // Copy Config.txt without replacing old one.
                Files.copy(is, SorterConstants.userConfigPath);
            } 
        } catch (IOException e) {
            int result = JOptionPane.showConfirmDialog(null,
                    "There was a error when trying to make config file: " + e.getMessage(),
                    "Error",
                    JOptionPane.OK_OPTION);
            if (result == JOptionPane.OK_OPTION)
                return; // Stops program from making GUI if the config is never made.
        }

        // Make and show the GUI.
        SorterGUI cGUI = new SorterGUI();
        cGUI.makeGUI();
    }

}
