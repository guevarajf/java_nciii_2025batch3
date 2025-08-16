package view;

import controller.AccountController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountFrame extends JFrame {
    private String mode;
    private JTextField ownerField;
    private JTextField amountField;
    private JLabel statusLabel;

    public AccountFrame(String mode) {
        this.mode = mode;

        String title = "Account ";
        switch (mode) {
            case "deposit":
                title += "Deposit";
                break;
            case "withdraw":
                title += "Withdrawal";
                break;
            case "balance":
                title += "Balance Check";
                break;
            default:
                title += "Operations";
        }

        setTitle(title);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        ownerField = new JTextField(20);
        amountField = new JTextField(20);
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.BLUE);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        String titleText = "";
        switch (mode) {
            case "deposit":
                titleText = "Deposit Money";
                break;
            case "withdraw":
                titleText = "Withdraw Money";
                break;
            case "balance":
                titleText = "Check Balance";
                break;
            default:
                titleText = "Account Operations";
        }

        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Account Owner:"), gbc);
        gbc.gridx = 1;
        formPanel.add(ownerField, gbc);

        if (!mode.equals("balance")) {
            gbc.gridx = 0;
            gbc.gridy = 1;
            formPanel.add(new JLabel("Amount (₱):"), gbc);
            gbc.gridx = 1;
            formPanel.add(amountField, gbc);
        }

        // Button panel
        JPanel buttonPanel = new JPanel();

        if (mode.equals("operations")) {
            JButton depositBtn = new JButton("Deposit");
            JButton withdrawBtn = new JButton("Withdraw");
            JButton balanceBtn = new JButton("Check Balance");

            depositBtn.addActionListener(e -> performDeposit());
            withdrawBtn.addActionListener(e -> performWithdraw());
            balanceBtn.addActionListener(e -> checkBalance());

            buttonPanel.add(depositBtn);
            buttonPanel.add(withdrawBtn);
            buttonPanel.add(balanceBtn);
        } else {
            JButton actionBtn = new JButton();
            switch (mode) {
                case "deposit":
                    actionBtn.setText("Deposit");
                    actionBtn.addActionListener(e -> performDeposit());
                    break;
                case "withdraw":
                    actionBtn.setText("Withdraw");
                    actionBtn.addActionListener(e -> performWithdraw());
                    break;
                case "balance":
                    actionBtn.setText("Check Balance");
                    actionBtn.addActionListener(e -> checkBalance());
                    break;
            }
            buttonPanel.add(actionBtn);
        }

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        buttonPanel.add(closeBtn);

        // Status panel
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add status panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void performDeposit() {
        String owner = ownerField.getText().trim();
        if (owner.isEmpty()) {
            statusLabel.setText("Owner is required");
            statusLabel.setForeground(Color.RED);
            return;
        }

        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) {
                statusLabel.setText("Amount must be positive");
                statusLabel.setForeground(Color.RED);
                return;
            }

            AccountController.TransactionResult result = AccountController.deposit(owner, amount);

            if (result.isSuccess()) {
                statusLabel.setText(result.getMessage() + ". New balance: " +
                        AccountController.formatCurrency(result.getNewBalance()));
                statusLabel.setForeground(Color.GREEN);
                amountField.setText("");
            } else {
                statusLabel.setText(result.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid amount format");
            statusLabel.setForeground(Color.RED);
        }
    }

    private void performWithdraw() {
        String owner = ownerField.getText().trim();
        if (owner.isEmpty()) {
            statusLabel.setText("Owner is required");
            statusLabel.setForeground(Color.RED);
            return;
        }

        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            if (amount <= 0) {
                statusLabel.setText("Amount must be positive");
                statusLabel.setForeground(Color.RED);
                return;
            }

            AccountController.TransactionResult result = AccountController.withdraw(owner, amount);

            if (result.isSuccess()) {
                statusLabel.setText(result.getMessage() + ". New balance: " +
                        AccountController.formatCurrency(result.getNewBalance()));
                statusLabel.setForeground(Color.GREEN);
                amountField.setText("");
            } else {
                statusLabel.setText(result.getMessage());
                statusLabel.setForeground(Color.RED);
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid amount format");
            statusLabel.setForeground(Color.RED);
        }
    }

    private void checkBalance() {
        String owner = ownerField.getText().trim();
        if (owner.isEmpty()) {
            statusLabel.setText("Owner is required");
            statusLabel.setForeground(Color.RED);
            return;
        }

        double balance = AccountController.getBalance(owner);
        statusLabel.setText("Current balance: " + AccountController.formatCurrency(balance));
        statusLabel.setForeground(Color.BLUE);
    }
}