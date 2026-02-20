import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * The main GUI for Folder Cleaner.
 */
public class CleanerGUI implements ActionListener {
    // Swing components:
    static JFrame frame;
    static JLabel titleLabel, folderLabel, currentFolderLabel;
    static JPanel titlePanel, selectContainer;
    static JMenuBar topMenuBar;
    static JMenu configMenu, helpMenu;
    static JMenuItem openConfigMenuItem, aboutMenuItem, guideMenuItem;
    static JFileChooser folderChooser;
    static JButton chooseBtn, currentBtn;

    // Numbers:
    final static Dimension frameStartSize = new Dimension(500, 600);
    final static Dimension frameMinimumSize = new Dimension(500, 600);
    final static Color frameColor = new Color(231, 240, 180);
    final static Color topMenuColor = new Color(167, 173, 130);
    final static Color topMenuOptionColor = new Color(231, 240, 180);
    final static Color paddingColor = new Color(167, 173, 130);
    final static Color btnColor = new Color(241, 245, 196);

    // Strings:
    final static String frameTitleStr = "Folder Cleaner";
    final static String titleStr = "Welcome to Folder Cleaner";
    final static String configStr = "Configuration";
    final static String openConfigStr = "Open configuration";
    final static String helpStr = "Help";
    final static String aboutStr = "About";
    final static String guideStr = "Guide";
    final static String folderChooseStr = "Select a folder";
    final static String currentFolderStr = "Use the folder the program is in";

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

    /**
     * Makes the top menu. Makes these menus:
     * 
     * Configuration:
     * - Open configuration
     * Help:
     * - About
     * - Guide
     */
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

    /**
     * Makes the title at the top.
     */
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

    /**
     * Makes some padding style to the frame.
     */
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

    private static void makeFolderSelector() {
        // Constructing:
        selectContainer = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        chooseBtn = new JButton(folderChooseStr);
        currentFolderLabel = new JLabel(currentFolderStr);
        currentBtn = new JButton(currentFolderStr);
        JPanel padding = new JPanel();

        // Style:
        selectContainer.setLayout(new GridBagLayout());
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        selectContainer.setBorder(blackLine);
        selectContainer.setBackground(frameColor);
        chooseBtn.setBackground(btnColor);
        padding.setBackground(frameColor);
        currentBtn.setBackground(btnColor);
        chooseBtn.setPreferredSize(new Dimension(200, 100));
        currentBtn.setPreferredSize(new Dimension(200, 100));

        // Adding to frame:
        frame.add(selectContainer, BorderLayout.CENTER);
        selectContainer.add(chooseBtn, c);
        c.gridy = 1;
        selectContainer.add(padding, c);
        c.gridy = 2;
        selectContainer.add(currentBtn, c);
    }

    /**
     * Handles button presses. Does not do the logic behind each button. Logic is
     * delegated to each action's seperate method for readability.
     * 
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            // Handles open congiguration menu button.
            case openConfigStr: {
                break;
            }
            // Handles about menu button.
            case aboutStr: {
                break;
            }
            // Handles guide menu button.
            case guideStr: {
                break;
            }
            // Handles choose folder button.
            case folderChooseStr: {
                break;
            }

        }
    }
}
