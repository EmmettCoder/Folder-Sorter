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
        CleanerGUI cGUI = new CleanerGUI();
        cGUI.makeGUI();
    }
}
