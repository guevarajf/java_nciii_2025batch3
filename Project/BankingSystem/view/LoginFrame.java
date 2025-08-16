package view;

import controller.LoginController;
import controller.DashboardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    private final Color PRIMARY_COLOR = new Color(46, 125, 50);
    private final Color ERROR_COLOR = Color.RED;
    private final Color SUCCESS_COLOR = new Color(46, 125, 50);

    public LoginFrame() {
        setTitle("Banking System - Login");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        statusLabel = new JLabel(" ");
    }

    private void layoutComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Banking System - Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form panel with GridLayout
        JPanel formPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username
        formPanel.add(createLabeledField("Username:", usernameField));
        // Password
        formPanel.add(createLabeledField("Password:", passwordField));

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(this::performLogin);
        buttonPanel.add(loginBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> resetFields());
        buttonPanel.add(resetBtn);

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

    private void performLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        LoginController.LoginResult result = LoginController.authenticate(username, password);

        if (result.isSuccess()) {
            DashboardController.setCurrentUser(result.getUser());
            new DashboardFrame().setVisible(true);
            dispose();
        } else {
            showError(result.getMessage());
            passwordField.setText("");
        }
    }

    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
        statusLabel.setText(" ");
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(ERROR_COLOR);
    }
}