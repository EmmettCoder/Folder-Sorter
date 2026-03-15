package GUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import Logic.CleanerConstants;
import Logic.CleanerLogic;

/**
 * The main GUI for Folder Cleaner.
 * 
 * @author Emmett Grebe
 * @version 3-15-2026
 */
public class CleanerGUI implements ActionListener {
    // Swing components:
    private JButton chooseBtn, currentBtn;
    private JFrame frame;
    private JLabel titleLabel;
    private JMenuBar topMenuBar;
    private JMenu configMenu, helpMenu;
    private JMenuItem openConfigMenuItem, aboutMenuItem, guideMenuItem;
    private JPanel titlePanel, padding;
    private GradientPanel selectContainer;

    // Numbers:
    private final Color btnColor = new Color(102, 183, 255);
    private final Color clearColor = new Color(0, 0, 0, 0);
    private final Color frameColor = new Color(102, 183, 255);
    private final Color gradientColor1 = new Color(102, 183, 255);
    private final Color gradientColor2 = new Color(0, 135, 255);
    private final Color paddingColor = new Color(191, 216, 255);
    private final Color topMenuColor = new Color(167, 173, 130);
    private final Color topMenuOptionColor = new Color(231, 240, 180);

    private final Dimension btnSize = new Dimension(200, 100);
    private final Dimension frameStartSize = new Dimension(500, 600);
    private final Dimension frameMinimumSize = new Dimension(500, 600);

    // Strings:
    private final String aboutStr = "About";
    private final String configStr = "Configuration";
    private final String confirmText = "Do not turn off this program or computer while it is cleaning. Are you sure you want to continue?";
    private final String confirmTitle = "Are you sure?";
    private final String currentFolderStr = "<html>Use the folder the <br>program is in</html>";
    private final String errorTitle = "Error";
    private final String folderChooseStr = "Select a folder";
    private final String frameTitleStr = "Folder Cleaner";
    private final String guideStr = "Guide";
    private final String helpStr = "Help";
    private final String openConfigStr = "Open configuration";
    private final String popUpTitle = "Folder Cleaner Result";
    private final String titleStr = "Welcome to Folder Cleaner";

    private String aboutText = ""; // Empty to avoid null.
    private String guideText = ""; // Empty to avoid null.

    /**
     * Does all the handler methods in order.
     * Handler methods are separated by category for ease of finding them.
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            int result = JOptionPane.showConfirmDialog(frame,
                    "There was a UIManager error: " + e.getMessage(),
                    errorTitle,
                    JOptionPane.OK_OPTION);
            if (result == JOptionPane.OK_OPTION)
                frame.dispose();
        }

        // Icon
        InputStream imgStream = getClass().getResourceAsStream(CleanerConstants.iconPath);
        try {
            Image iconImage = ImageIO.read(imgStream);
            frame.setIconImage(iconImage);
        } catch (IOException e) {
            // This will never happen if the Jar is not tampered with.
            e.printStackTrace();
        }
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

        openConfigMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);
        guideMenuItem.addActionListener(this);

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

        // Put the text from the txt files in Resources.
        InputStream isAbout = CleanerGUI.class.getResourceAsStream(CleanerConstants.aboutTextPath);
        BufferedReader brAbout = new BufferedReader(new InputStreamReader(isAbout));
        InputStream isGuide = CleanerGUI.class.getResourceAsStream(CleanerConstants.guideTextPath);
        BufferedReader brGuide = new BufferedReader(new InputStreamReader(isGuide));
        try {
            while (brAbout.ready())
                aboutText += brAbout.readLine();
            while (brGuide.ready())
                guideText += brGuide.readLine();
        } catch (IOException e) {
            // This will never happen if the Jar is never tampered with.
            e.printStackTrace();
        }
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
        selectContainer = new GradientPanel(gradientColor1, gradientColor2, new Point(0, 0),
                new Point(frame.getWidth(), frame.getHeight()));
        padding = new JPanel();
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
        chooseBtn.setPreferredSize(btnSize);
        currentBtn.setBackground(btnColor);
        currentBtn.setPreferredSize(btnSize);
        padding.setBackground(clearColor);

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
     * delegated to each action's separate method for readability.
     * 
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            // Handles open configuration menu button.
            case openConfigStr: {
                handleOpenConfig();
                break;
            }
            // Handles about menu button.
            case aboutStr: {
                handleAbout();
                break;
            }
            // Handles guide menu button.
            case guideStr: {
                handleGuide();
                break;
            }
            // Handles choose folder button.
            case folderChooseStr: {
                handleChooseFolder();
                break;
            }
            case currentFolderStr: {
                handleCurrFolder();
                break;
            }

        }
    }

    /**
     * Handles clicking the About button in the Help menu. Displays text to tell
     * what the program does.
     */
    private void handleAbout() {
        JLabel label = new JLabel(aboutText);
        JOptionPane.showMessageDialog(frame, label, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles clicking the Guide button in the Help menu. Displays text to tell how
     * to use the program.
     */
    private void handleGuide() {
        JLabel label = new JLabel(guideText);
        JOptionPane.showMessageDialog(frame, label, "Guide", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles opening configuration file.
     */
    private void handleOpenConfig() {
        ConfigGUI.makeGUI();
        // File configFile = new File(CleanerConstants.userConfigPath.toString());
        // try {
        //     Desktop.getDesktop().open(configFile);
        // } catch (IOException e) {
        //     JOptionPane.showConfirmDialog(null,
        //             "There was a error when trying to open the config file. You can also open the file through Documents/Folder-Sorter/Config.txt: " + e.getMessage(),
        //             errorTitle,
        //             JOptionPane.OK_OPTION);
        // }
    }

    /**
     * Handles clicking 'Select a folder'.
     */
    private void handleChooseFolder() {
        JFileChooser fileChooser = new JFileChooser();
        // Make the folder chooser only allow selecting folders.
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Set the file chooser to the current directory of the program.
        try {
            fileChooser.setCurrentDirectory(
                    new File(CleanerGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
        } catch (URISyntaxException e) {

        }
        // Show the file chooser and store the result.
        int fileChooserReturnValue = fileChooser.showOpenDialog(frame);

        // If user clicks yes, clean selected folder.
        if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
            int result = JOptionPane.showConfirmDialog(frame,
                    confirmText,
                    confirmTitle,
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                final String successStr = "Folder " + selectedFolder.getName() + " cleaned successfully.";
                final String failureStr = "Folder " + selectedFolder.getName() + " was not cleaned successfully.";

                if (CleanerLogic.cleanThere(selectedFolder.getAbsolutePath())) {
                    JOptionPane.showMessageDialog(frame,
                            successStr,
                            popUpTitle,
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            failureStr,
                            popUpTitle,
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * Handles clicking 'Use the folder the program is in'.
     */
    private void handleCurrFolder() {
        int result = JOptionPane.showConfirmDialog(frame,
                confirmText,
                confirmTitle,
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Current directory.
            String currDirectory;
            try {
                File jarFile = new File(CleanerGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                currDirectory = jarFile.getParent();
                CleanerLogic.cleanThere(currDirectory);
                final String popUpTitle = "Folder Cleaner Result";
                final String successStr = "Folder " + currDirectory + " cleaned successfully.";
                final String failureStr = "Folder " + currDirectory + " was not cleaned successfully.";

                if (CleanerLogic.cleanThere(currDirectory)) {
                    JOptionPane.showMessageDialog(frame,
                            successStr,
                            popUpTitle,
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            failureStr,
                            popUpTitle,
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (URISyntaxException e) {

            }
        }
    }

}
