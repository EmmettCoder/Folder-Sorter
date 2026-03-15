package GUI;

import java.awt.*;
import javax.swing.*;

/**
 * GUI for changing the configuration.
 * 
 * @author Emmett Grebe
 * @version 3-15-2026
 */
public class ConfigGUI {
    // Swing components:
    private static JFrame frame;

    // Numbers:
    private final static Dimension frameStartSize = new Dimension(500, 600);
    private final static Dimension frameMinimumSize = new Dimension(500, 600);

    // Strings:
    private final static String frameTitleStr = "Folder Cleaner Configuration Editor";

    /**
     * Makes the GUI. Singleton pattern so only one can be open at a time.
     */
    public static void makeGUI() {
        if (frame == null) {
            makeFrame();
            frame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, 
                                 "Only one Configuration Editor may be open at a time.",
                                   "Info", 
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void makeFrame() {
        // Constructing:
        frame = new JFrame(frameTitleStr);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sizing:
        frame.setSize(frameStartSize);
        frame.setMinimumSize(frameMinimumSize);
    }
}
