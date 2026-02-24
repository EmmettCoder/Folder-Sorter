import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * The main GUI for Folder Cleaner.
 * 
 * @author Emmett Grebe
 * @version 2-19-2026
 */
public class CleanerGUI implements ActionListener {
    // Swing components:
    JFrame frame;
    JLabel titleLabel, folderLabel, currentFolderLabel;
    JPanel titlePanel, selectContainer;
    JMenuBar topMenuBar;
    JMenu configMenu, helpMenu;
    JMenuItem openConfigMenuItem, aboutMenuItem, guideMenuItem;
    JFileChooser folderChooser;
    JButton chooseBtn, currentBtn;

    // Numbers:
    final Dimension frameStartSize = new Dimension(500, 600);
    final Dimension frameMinimumSize = new Dimension(500, 600);
    final Dimension btnSize = new Dimension(200, 100);
    final Color frameColor = new Color(231, 240, 180);
    final Color topMenuColor = new Color(167, 173, 130);
    final Color topMenuOptionColor = new Color(231, 240, 180);
    final Color paddingColor = new Color(167, 173, 130);
    final Color btnColor = new Color(241, 245, 196);

    // Strings:
    final String frameTitleStr = "Folder Cleaner";
    final String titleStr = "Welcome to Folder Cleaner";
    final String configStr = "Configuration";
    final String openConfigStr = "Open configuration";
    final String helpStr = "Help";
    final String aboutStr = "About";
    final String guideStr = "Guide";
    final String folderChooseStr = "Select a folder";
    final String currentFolderStr = "Use the folder the program is in";

    /**
     * Does all the handler methods in order.
     * Handler methods are seperated by category for ease of finding them.
     */
    public void makeGUI() {
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
    private void makeFrame() {
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
    private void makeTopMenu() {
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
    private void makeTitle() {
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
    private void makePadding() {
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

    /**
     * Makes the folder selector buttons.
     */
    private void makeFolderSelector() {
        // Constructing:
        selectContainer = new JPanel();
        JPanel padding = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        chooseBtn = new JButton(folderChooseStr);
        currentBtn = new JButton(currentFolderStr);

        // Listeners:
        chooseBtn.setActionCommand(folderChooseStr);
        currentBtn.setActionCommand(currentFolderStr);
        chooseBtn.addActionListener(this);
        currentBtn.addActionListener(this);

        // Style:
        selectContainer.setLayout(new GridBagLayout());
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        selectContainer.setBorder(blackLine);
        selectContainer.setBackground(frameColor);
        chooseBtn.setBackground(btnColor);
        padding.setBackground(frameColor);
        currentBtn.setBackground(btnColor);
        chooseBtn.setPreferredSize(btnSize);
        currentBtn.setPreferredSize(btnSize);

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
            case currentFolderStr: {
                handleCurrFolder();
                break;
            }

        }
    }

    private void handleCurrFolder() {
        CleanerLogic.cleanHere();
    }
}
