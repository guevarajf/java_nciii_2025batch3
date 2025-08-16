package view;

import model.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccountFrame extends JFrame {
    private JTextField usernameField;
    private JTextField fullNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> roleComboBox;
    private JLabel statusLabel;

    private final String[] BANKING_ROLES = { "Customer", "Teller", "Manager", "Admin" };

    public CreateAccountFrame() {
        setTitle("User Enrollment - Banking System");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        layoutComponents();
        setVisible(true);
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        fullNameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        roleComboBox = new JComboBox<>(BANKING_ROLES);
        statusLabel = new JLabel(" ");
    }

    private void layoutComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(46, 125, 50));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("User Enrollment - Banking System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form panel with GridLayout (simpler than GridBagLayout)
        JPanel formPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username
        formPanel.add(createLabeledField("Username:", usernameField));
        // Full Name
        formPanel.add(createLabeledField("Full Name:", fullNameField));
        // Password
        formPanel.add(createLabeledField("Password:", passwordField));
        // Confirm Password
        formPanel.add(createLabeledField("Confirm Password:", confirmPasswordField));
        // Role
        formPanel.add(createLabeledField("Role:", roleComboBox));

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton enrollBtn = new JButton("Enroll User");
        enrollBtn.addActionListener(this::enrollUser);
        buttonPanel.add(enrollBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> resetFields());
        buttonPanel.add(resetBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        buttonPanel.add(cancelBtn);

        // Status label
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        // Combine buttons and status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 2));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(label, BorderLayout.NORTH);

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void enrollUser(ActionEvent e) {
        // Get input values
        String username = usernameField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        // Validation
        if (username.isEmpty()) {
            showError("Username is required!");
            return;
        }

        if (fullName.isEmpty()) {
            showError("Full name is required!");
            return;
        }

        if (password.isEmpty()) {
            showError("Password is required!");
            return;
        }

        if (password.length() < 4) {
            showError("Password must be at least 4 characters long!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match!");
            return;
        }

        // Save user to database
        try {
            if (userExists(username)) {
                showError("Username already exists! Please choose a different username.");
                return;
            }

            if (insertUser(username, password, role)) {
                showSuccess("User '" + username + "' enrolled successfully!");
                resetFields();
            } else {
                showError("Failed to enroll user. Please try again.");
            }

        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            showError("An error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean userExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private boolean insertUser(String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private void resetFields() {
        usernameField.setText("");
        fullNameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        roleComboBox.setSelectedIndex(0);
        statusLabel.setText(" ");
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(new Color(46, 125, 50));
    }
}