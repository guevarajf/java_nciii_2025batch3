package view;

import model.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JLabel statusLabel;

    public ChangePasswordFrame() {
        setTitle("Change Password - Banking System");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        layoutComponents();
        setVisible(true);
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
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
        JLabel titleLabel = new JLabel("Change User Password");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form panel with GridLayout
        JPanel formPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username
        formPanel.add(createLabeledField("Username:", usernameField));
        // Current Password
        formPanel.add(createLabeledField("Current Password:", currentPasswordField));
        // New Password
        formPanel.add(createLabeledField("New Password:", newPasswordField));
        // Confirm New Password
        formPanel.add(createLabeledField("Confirm New Password:", confirmPasswordField));

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton changeBtn = new JButton("Change Password");
        changeBtn.addActionListener(this::changePassword);
        buttonPanel.add(changeBtn);

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

    private void changePassword(ActionEvent e) {
        // Get input values
        String username = usernameField.getText().trim();
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validation
        if (username.isEmpty()) {
            showError("Username is required!");
            return;
        }

        if (currentPassword.isEmpty()) {
            showError("Current password is required!");
            return;
        }

        if (newPassword.isEmpty()) {
            showError("New password is required!");
            return;
        }

        if (newPassword.length() < 4) {
            showError("New password must be at least 4 characters long!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showError("New passwords do not match!");
            return;
        }

        if (currentPassword.equals(newPassword)) {
            showError("New password must be different from current password!");
            return;
        }

        // Change password in database
        try {
            if (!verifyCurrentPassword(username, currentPassword)) {
                showError("Invalid username or current password!");
                return;
            }

            if (updatePassword(username, newPassword)) {
                showSuccess("Password changed successfully for user '" + username + "'!");
                resetFields();
            } else {
                showError("Failed to change password. Please try again.");
            }

        } catch (SQLException ex) {
            showError("Database error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            showError("An error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean verifyCurrentPassword(String username, String currentPassword) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, currentPassword);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private boolean updatePassword(String username, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private void resetFields() {
        usernameField.setText("");
        currentPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
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