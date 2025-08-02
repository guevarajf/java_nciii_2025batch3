import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

/**
 * Window Screenshot Tool
 * Author: J. Guevara
 * Complete and fixed version
 */
public class WindowScreenshotTool extends JFrame {
    private String saveFolder = "D:\\Training\\Java NCIII\\SIL\\4";
    private volatile boolean isSelecting = false;
    private BufferedImage lastScreenshot = null;
    
    // GUI Components
    private JTextField folderField;
    private JTextField filenameField;
    private JCheckBox autoIncrementCheck;
    private JButton screenshotBtn;
    private JButton cancelBtn;
    private JLabel statusLabel;
    private JLabel previewLabel;
    
    // Mouse listener for global clicks
    private GlobalMouseListener mouseListener;
    
    public WindowScreenshotTool() {
        initializeGUI();
        createSaveFolder();
        updateFilename();
    }
    
    private void initializeGUI() {
        setTitle("Window Screenshot Tool - by J. Guevara");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Window Screenshot Tool");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Save Location Panel
        mainPanel.add(createSaveLocationPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // File Settings Panel
        mainPanel.add(createFileSettingsPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Instructions Panel
        mainPanel.add(createInstructionsPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Actions Panel
        mainPanel.add(createActionsPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Status Panel
        mainPanel.add(createStatusPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Preview Panel
        mainPanel.add(createPreviewPanel());
        
        add(mainPanel);
        
        // Initialize mouse listener
        mouseListener = new GlobalMouseListener();
    }
    
    private JPanel createSaveLocationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBorder(new TitledBorder("Save Location"));
        
        JPanel innerPanel = new JPanel(new BorderLayout(10, 0));
        innerPanel.add(new JLabel("Folder:"), BorderLayout.WEST);
        
        folderField = new JTextField(saveFolder);
        folderField.setEditable(false);
        innerPanel.add(folderField, BorderLayout.CENTER);
        
        JButton browseBtn = new JButton("Browse");
        browseBtn.addActionListener(e -> browseFolder());
        innerPanel.add(browseBtn, BorderLayout.EAST);
        
        panel.add(innerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createFileSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBorder(new TitledBorder("File Settings"));
        
        JPanel innerPanel = new JPanel(new BorderLayout(10, 0));
        innerPanel.add(new JLabel("Filename:"), BorderLayout.WEST);
        
        filenameField = new JTextField();
        innerPanel.add(filenameField, BorderLayout.CENTER);
        
        autoIncrementCheck = new JCheckBox("Auto-increment", true);
        autoIncrementCheck.addActionListener(e -> toggleAutoIncrement());
        innerPanel.add(autoIncrementCheck, BorderLayout.EAST);
        
        panel.add(innerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createInstructionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Instructions"));
        
        String instructions = "<html>" +
            "1. Click 'Take Screenshot' button below<br>" +
            "2. Click on any window you want to capture<br>" +
            "3. The screenshot will be saved automatically<br>" +
            "4. Filename will auto-increment or use your custom name" +
            "</html>";
        
        JLabel instructionsLabel = new JLabel(instructions);
        panel.add(instructionsLabel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new TitledBorder("Actions"));
        
        screenshotBtn = new JButton("Take Screenshot");
        screenshotBtn.addActionListener(e -> startScreenshot());
        panel.add(screenshotBtn);
        
        cancelBtn = new JButton("Cancel");
        cancelBtn.setEnabled(false);
        cancelBtn.addActionListener(e -> cancelScreenshot());
        panel.add(cancelBtn);
        
        JButton openFolderBtn = new JButton("Open Folder");
        openFolderBtn.addActionListener(e -> openFolder());
        panel.add(openFolderBtn);
        
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> exitApp());
        panel.add(exitBtn);
        
        return panel;
    }
    
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Ready to take screenshot");
        statusLabel.setForeground(Color.BLUE);
        panel.add(statusLabel, BorderLayout.WEST);
        return panel;
    }
    
    private JPanel createPreviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Last Screenshot Preview"));
        panel.setPreferredSize(new Dimension(0, 200));
        
        previewLabel = new JLabel("No screenshot taken yet", SwingConstants.CENTER);
        previewLabel.setOpaque(true);
        previewLabel.setBackground(Color.WHITE);
        previewLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        previewLabel.setPreferredSize(new Dimension(0, 180));
        
        panel.add(previewLabel, BorderLayout.CENTER);
        return panel;
    }
    
    private void createSaveFolder() {
        File folder = new File(saveFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    private void browseFolder() {
        JFileChooser chooser = new JFileChooser(saveFolder);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            saveFolder = chooser.getSelectedFile().getAbsolutePath();
            folderField.setText(saveFolder);
            createSaveFolder();
            updateFilename();
        }
    }
    
    private void toggleAutoIncrement() {
        if (autoIncrementCheck.isSelected()) {
            filenameField.setEditable(false);
            updateFilename();
        } else {
            filenameField.setEditable(true);
            filenameField.setText("");
        }
    }
    
    private int getNextNumber() {
        File folder = new File(saveFolder);
        if (!folder.exists()) {
            return 1;
        }
        
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return 1;
        }
        
        List<Integer> numbers = new ArrayList<>(files.length);
        Pattern pattern = Pattern.compile("^(\\d+)\\.(png|jpg|jpeg)$", Pattern.CASE_INSENSITIVE);
        
        for (File file : files) {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                numbers.add(Integer.parseInt(matcher.group(1)));
            }
        }
        
        return numbers.isEmpty() ? 1 : Collections.max(numbers) + 1;
    }
    
    private void updateFilename() {
        if (autoIncrementCheck.isSelected()) {
            int nextNum = getNextNumber();
            filenameField.setText(String.valueOf(nextNum));
        }
    }
    
    private void startScreenshot() {
        isSelecting = true;
        waitForClick();
    }
    
    private void waitForClick() {
        statusLabel.setText("Click anywhere on the screen to capture that window...");
        screenshotBtn.setEnabled(false);
        screenshotBtn.setText("Waiting for click...");
        cancelBtn.setEnabled(true);
        
        setVisible(false);
        SwingUtilities.invokeLater(() -> setupClickDetection());
    }
    
    private void setupClickDetection() {
        try {
            mouseListener.startListening(this::handleClick);
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                setVisible(true);
                statusLabel.setText("Error setting up click detection: " + e.getMessage());
                resetButtons();
            });
        }
    }
    
    private void handleClick(Point clickPoint) {
        isSelecting = false;
        mouseListener.stopListening();
        
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            takeScreenshotAtPoint(clickPoint);
        });
    }
    
    private void takeScreenshotAtPoint(Point clickPoint) {
        new Thread(() -> {
            try {
                SwingUtilities.invokeAndWait(() -> 
                    statusLabel.setText("Taking screenshot..."));
                
                Robot robot = new Robot();
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage screenshot = robot.createScreenCapture(screenRect);
                
                // Make filename final for use in lambda
                final String filename;
                String tempFilename = filenameField.getText().trim();
                if (tempFilename.isEmpty()) {
                    filename = String.valueOf(getNextNumber());
                } else {
                    filename = tempFilename.replaceAll("\\.(png|jpg|jpeg)$", "");
                }
                
                File outputFile = new File(saveFolder, filename + ".png");
                ImageIO.write(screenshot, "PNG", outputFile);
                
                SwingUtilities.invokeLater(() -> {
                    updatePreview(screenshot);
                    statusLabel.setText("Screenshot saved: " + filename + ".png");
                    
                    if (autoIncrementCheck.isSelected()) {
                        updateFilename();
                    }
                    
                    resetButtons();
                });
                
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Error: " + e.getMessage());
                    resetButtons();
                });
            }
        }).start();
    }
    
    private void updatePreview(BufferedImage screenshot) {
        try {
            int previewWidth = 200;
            int previewHeight = 150;
            
            Image scaledImage = screenshot.getScaledInstance(previewWidth, previewHeight, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            
            previewLabel.setIcon(icon);
            previewLabel.setText("");
            
            lastScreenshot = screenshot;
            
        } catch (Exception e) {
            statusLabel.setText("Error updating preview: " + e.getMessage());
        }
    }
    
    private void cancelScreenshot() {
        isSelecting = false;
        mouseListener.stopListening();
        setVisible(true);
        statusLabel.setText("Screenshot cancelled");
        resetButtons();
    }
    
    private void resetButtons() {
        screenshotBtn.setEnabled(true);
        screenshotBtn.setText("Take Screenshot");
        cancelBtn.setEnabled(false);
    }
    
    private void openFolder() {
        try {
            Desktop.getDesktop().open(new File(saveFolder));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not open folder: " + e.getMessage(), 
                                      "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exitApp() {
        try {
            if (mouseListener != null) {
                mouseListener.stopListening();
            }
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Error during exit: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private class GlobalMouseListener {
        private volatile boolean listening = false;
        private MouseCallback callback;
        
        public void startListening(MouseCallback callback) {
            this.callback = callback;
            this.listening = true;
            
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Simulate click after delay for demo
                    if (listening && isSelecting) {
                        callback.onMouseClick(MouseInfo.getPointerInfo().getLocation());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        
        public void stopListening() {
            listening = false;
        }
    }
    
    @FunctionalInterface
    private interface MouseCallback {
        void onMouseClick(Point point);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            WindowScreenshotTool tool = new WindowScreenshotTool();
            tool.setVisible(true);
        });
    }
}