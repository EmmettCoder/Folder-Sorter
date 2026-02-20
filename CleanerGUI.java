import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * The main GUI for Folder Cleaner.
 */
public class CleanerGUI {
    // Swing components:
    static JFrame frame;
    static JLabel titleLabel;
    static JPanel titlePanel, selectContainer;
    static JMenuBar topMenuBar;
    static JMenu configMenu, helpMenu;
    static JMenuItem openConfigMenuItem, aboutMenuItem, guideMenuItem;

    // Numbers:
    final static Dimension frameStartSize = new Dimension(500, 600);
    final static Dimension frameMinimumSize = new Dimension(500, 600);
    final static Color frameColor = new Color(231, 240, 180);
    final static Color topMenuColor = new Color(167, 173, 130);
    final static Color topMenuOptionColor = new Color(231, 240, 180);
    final static Color paddingColor = new Color(167, 173, 130);

    // Strings:
    final static String frameTitleStr = "Folder Cleaner";
    final static String titleStr = "Welcome to Folder Cleaner";
    final static String configStr = "Configuration";
    final static String openConfigStr = "Open configuration";
    final static String helpStr = "Help";
    final static String aboutStr = "About";
    final static String guideStr = "Guide";

    /**
     * Does all the handler methods in order.
     * Handler methods are seperated by category for ease of finding them.
     */
    protected static void makeGUI() {
        makeFrame();
        makeTopMenu();
        makeTitle();
        makeFolderSelector();
        makePadding();
        
        frame.setVisible(true);
    }

    /**
     * Handles frame creation.
     */
    private static void makeFrame() {
        // Constructing:
        frame = new JFrame(frameTitleStr);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sizing:
        frame.setSize(frameStartSize);
        frame.setMinimumSize(frameMinimumSize);

        // Styling
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(frameColor);
    }

    private static void makeTitle() {
        // Constructing:
        titlePanel = new JPanel();
        titleLabel = new JLabel(titleStr);

        // Style:
        titlePanel.setBackground(paddingColor);

        // Adding to frame:
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.PAGE_START);
    }

    private static void makePadding() {
        // Constructing:
        JPanel paddingLeft = new JPanel();
        JPanel paddingRight = new JPanel();
        JPanel paddingBottom = new JPanel();

        // Style:
        paddingLeft.setBackground(paddingColor);
        paddingRight.setBackground(paddingColor);
        paddingBottom.setBackground(paddingColor);

        // Adding to frame:
        frame.add(paddingLeft, BorderLayout.LINE_START);
        frame.add(paddingRight, BorderLayout.LINE_END);
        frame.add(paddingBottom, BorderLayout.PAGE_END);
    }

    private static void makeTopMenu() {
        // Constructing:
        topMenuBar = new JMenuBar();
        configMenu = new JMenu(configStr);
        openConfigMenuItem = new JMenuItem(openConfigStr);
        helpMenu = new JMenu(helpStr);
        aboutMenuItem = new JMenuItem(aboutStr);
        guideMenuItem = new JMenuItem(guideStr);

        // Style:
        configMenu.setBackground(topMenuColor);
        openConfigMenuItem.setBackground(topMenuOptionColor);
        topMenuBar.setBackground(topMenuColor);
        aboutMenuItem.setBackground(topMenuOptionColor);
        guideMenuItem.setBackground(topMenuOptionColor);

        // Adding to frame:
        configMenu.add(openConfigMenuItem);
        helpMenu.add(aboutMenuItem);
        helpMenu.add(guideMenuItem);
        topMenuBar.add(configMenu);
        topMenuBar.add(helpMenu);
        frame.setJMenuBar(topMenuBar);
    }

    private static void makeFolderSelector() {
        // Constructing:
        selectContainer = new JPanel();
        
        // Style:
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        selectContainer.setBorder(blackLine);
        selectContainer.setBackground(frameColor);
        
        // Adding to frame:
        frame.add(selectContainer, BorderLayout.CENTER);
    }
}
