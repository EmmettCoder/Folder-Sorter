package GUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import Logic.SorterConstants;
import Logic.SorterLogic;

/**
 * The main GUI for Folder Sorter.
 * @author Emmett Grebe
 * @version 4-13-2026
 */
public class SorterGUI implements ActionListener {
    // Swing components:
    private JButton chooseBtn, currentBtn, undoBtn; 
    private JFrame frame;
    private JLabel titleLabel;
    private JMenuBar topMenuBar;
    private JMenu configMenu, nameSortMenu, helpMenu;
    private JMenuItem openConfigMenuItem, aboutNameSortItem, aboutMenuItem, guideMenuItem;
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
    private final String aboutNameSortStr = "About Name Sort";
    private final String configStr = "Configuration";
    private final String confirmText = "Do not turn off this program or computer while it is sorting. Are you sure you want to continue?";
    private final String confirmTitle = "Are you sure?";
    private final String currentFolderStr = "<html>Use the folder the <br>program is in</html>";
    private final String undoStr = "Undo"; 
    private final String errorTitle = "Error";
    private final String folderChooseStr = "Select a folder";
    private final String frameTitleStr = "Folder Sorter";
    private final String guideStr = "Guide";
    private final String helpStr = "Help";
    private final String nameSortStr = "Name Sort";
    private final String openConfigStr = "Open configuration";
    private final String popUpTitle = "Folder Sorter Result";
    private final String titleStr = "Welcome to Folder Sorter";

    private String aboutText = ""; // Empty to avoid null.
    private String guideText = ""; // Empty to avoid null.
    private String nameSortText = ""; // Empty to avoid null.

    /**
     * Makes the whole GUI. Calls functions to set up each piece.
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
     * Makes the frame for the whole program.
     */
    private void makeFrame() {
        frame = new JFrame(frameTitleStr);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameStartSize);
        frame.setMinimumSize(frameMinimumSize);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(frameColor);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            int result = JOptionPane.showConfirmDialog(frame, "UIManager error: " + e.getMessage(), errorTitle, JOptionPane.OK_OPTION);
            if (result == JOptionPane.OK_OPTION) frame.dispose();
        }

        InputStream imgStream = getClass().getResourceAsStream(SorterConstants.iconPath);
        try {
            if (imgStream != null) frame.setIconImage(ImageIO.read(imgStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes the top menu.
     */
    private void makeTopMenu() {
        topMenuBar = new JMenuBar();
        configMenu = new JMenu(configStr);
        openConfigMenuItem = new JMenuItem(openConfigStr);
        helpMenu = new JMenu(helpStr);
        aboutMenuItem = new JMenuItem(aboutStr);
        guideMenuItem = new JMenuItem(guideStr);
        nameSortMenu = new JMenu(nameSortStr);
        aboutNameSortItem = new JMenuItem(aboutNameSortStr);

        openConfigMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);
        guideMenuItem.addActionListener(this);

        configMenu.setBackground(topMenuColor);
        openConfigMenuItem.setBackground(topMenuOptionColor);
        topMenuBar.setBackground(topMenuColor);
        aboutMenuItem.setBackground(topMenuOptionColor);
        guideMenuItem.setBackground(topMenuOptionColor);

        configMenu.add(openConfigMenuItem);
        nameSortMenu.add(aboutNameSortItem);
        helpMenu.add(aboutMenuItem);
        helpMenu.add(guideMenuItem);
        topMenuBar.add(configMenu);
        topMenuBar.add(nameSortMenu);
        topMenuBar.add(helpMenu);
        frame.setJMenuBar(topMenuBar);

        try {
            InputStream isAbout = SorterGUI.class.getResourceAsStream(SorterConstants.aboutTextPath);
            BufferedReader brAbout = new BufferedReader(new InputStreamReader(isAbout));
            InputStream isGuide = SorterGUI.class.getResourceAsStream(SorterConstants.guideTextPath);
            BufferedReader brGuide = new BufferedReader(new InputStreamReader(isGuide));
            InputStream isNameSort = SorterGUI.class.getResourceAsStream(SorterConstants.nameSortTextPath);
            BufferedReader brNameSort = new BufferedReader(new InputStreamReader(isNameSort));
            while (brAbout.ready()) aboutText += brAbout.readLine();
            while (brGuide.ready()) guideText += brGuide.readLine();
            while (brGuide.ready()) nameSortText += brNameSort.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes the title and centers it by balancing the Undo button with a filler.
     */
    private void makeTitle() {
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(paddingColor);

        // Setup Undo Button
        undoBtn = new JButton();
        undoBtn.setActionCommand(undoStr);
        undoBtn.addActionListener(this);
        undoBtn.setFocusable(false);
        undoBtn.setBorderPainted(false);
        undoBtn.setContentAreaFilled(false);
        undoBtn.setPreferredSize(new Dimension(50, 40)); // Set fixed width for balancing

        try {
            InputStream iconStream = getClass().getResourceAsStream("/Assets/undoIcon.png");
            if (iconStream != null) {
                Image img = ImageIO.read(iconStream);
                undoBtn.setIcon(new ImageIcon(img.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
            }
        } catch (IOException e) {
            System.err.println("Could not load undo icon.");
        }

        // Setup Title Label
        titleLabel = new JLabel(titleStr, SwingConstants.CENTER);

        // Setup filler to center it
        // This is invisible but takes up the same 50px width as the undoBtn
        Box.Filler rightFiller = (Box.Filler) Box.createRigidArea(new Dimension(50, 40));

        // Adding components
        titlePanel.add(undoBtn, BorderLayout.LINE_START);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(rightFiller, BorderLayout.LINE_END);

        frame.add(titlePanel, BorderLayout.PAGE_START);
    }

    /**
     * Makes the padding around the edges of the GUI.
     */
    private void makePadding() {
        JPanel paddingLeft = new JPanel();
        JPanel paddingRight = new JPanel();
        JPanel paddingBottom = new JPanel();
        paddingLeft.setBackground(paddingColor);
        paddingRight.setBackground(paddingColor);
        paddingBottom.setBackground(paddingColor);

        frame.add(paddingLeft, BorderLayout.LINE_START);
        frame.add(paddingRight, BorderLayout.LINE_END);
        frame.add(paddingBottom, BorderLayout.PAGE_END);
    }

    /**
     * Makes the folder selector area. This is the area in the middle.
     */
    private void makeFolderSelector() {
        selectContainer = new GradientPanel(gradientColor1, gradientColor2, new Point(0, 0),
                new Point(frame.getWidth(), frame.getHeight()));
        padding = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        chooseBtn = new JButton(folderChooseStr);
        currentBtn = new JButton(currentFolderStr);

        chooseBtn.setActionCommand(folderChooseStr);
        currentBtn.setActionCommand(currentFolderStr);
        chooseBtn.addActionListener(this);
        currentBtn.addActionListener(this);

        selectContainer.setLayout(new GridBagLayout());
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        selectContainer.setBorder(blackLine);
        selectContainer.setBackground(frameColor);
        chooseBtn.setBackground(btnColor);
        chooseBtn.setPreferredSize(btnSize);
        currentBtn.setBackground(btnColor);
        currentBtn.setPreferredSize(btnSize);
        padding.setBackground(clearColor);

        frame.add(selectContainer, BorderLayout.CENTER);
        selectContainer.add(chooseBtn, c);
        c.gridy = 1;
        selectContainer.add(padding, c);
        c.gridy = 2;
        selectContainer.add(currentBtn, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case openConfigStr: handleOpenConfig(); break;
            case aboutStr: handleAbout(); break;
            case guideStr: handleGuide(); break;
            case folderChooseStr: handleChooseFolder(); break;
            case currentFolderStr: handleCurrFolder(); break;
            case undoStr: handleUndo(); break;
        }
    }

    /**
     * Handles about being clicked. Shows the about text.
     */
    private void handleAbout() {
        JOptionPane.showMessageDialog(frame, new JLabel(aboutText), "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles guide being clicked. Shows the guide text.
     */
    private void handleGuide() {
        JOptionPane.showMessageDialog(frame, new JLabel(guideText), "Guide", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles config. Opens the config file directly.
     */
    private void handleOpenConfig() {
        File configFile = new File(SorterConstants.userConfigPath.toString());
        try {
            Desktop.getDesktop().open(configFile);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Error: " + e.getMessage(), errorTitle, JOptionPane.OK_OPTION);
        }
    }

    /**
     * Handles choose folder being clicked. Opens a choose directory dialog.
     */
    private void handleChooseFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        try {
            fileChooser.setCurrentDirectory(new File(SorterGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
        } catch (Exception e) {}
        
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            if (JOptionPane.showConfirmDialog(frame, confirmText, confirmTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                if (SorterLogic.sortThere(selectedFolder.getAbsolutePath())) {
                    JOptionPane.showMessageDialog(frame, "Sorted successfully.", popUpTitle, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Sortup failed.", popUpTitle, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Handles current folder being clicked. Sorts the current folder of the program.
     */
    private void handleCurrFolder() {
        if (JOptionPane.showConfirmDialog(frame, confirmText, confirmTitle, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                File jarFile = new File(SorterGUI.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                String currDirectory = jarFile.getParent();
                if (SorterLogic.sortThere(currDirectory)) {
                    JOptionPane.showMessageDialog(frame, "Sorted successfully.", popUpTitle, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Sortup failed.", popUpTitle, JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {}
        }
    }

    /**
     * Handles undo being clicked.
     */
    private void handleUndo() {
        SorterLogic.undoSort();
        JOptionPane.showMessageDialog(frame, "Last action undone.", popUpTitle, JOptionPane.INFORMATION_MESSAGE);
    }
}