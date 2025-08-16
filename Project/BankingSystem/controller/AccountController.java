package controller;

import model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountController {

    public static class TransactionResult {
        private final boolean success;
        private final String message;
        private final double newBalance;

        public TransactionResult(boolean success, String message, double newBalance) {
            this.success = success;
            this.message = message;
            this.newBalance = newBalance;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public double getNewBalance() {
            return newBalance;
        }
    }

    public static TransactionResult deposit(String owner, double amount) {
        return performTransaction(owner, amount, true);
    }

    public static TransactionResult withdraw(String owner, double amount) {
        return performTransaction(owner, amount, false);
    }

    private static TransactionResult performTransaction(String owner, double amount, boolean isDeposit) {
        if (owner.trim().isEmpty()) {
            return new TransactionResult(false, "Owner is required", 0);
        }

        if (amount <= 0) {
            return new TransactionResult(false, "Amount must be positive", 0);
        }

        try (Connection c = Database.connect()) {
            // Check if account exists
            try (PreparedStatement fetch = c.prepareStatement(
                    "SELECT balance FROM accounts WHERE owner=?")) {
                fetch.setString(1, owner.trim());
                ResultSet rs = fetch.executeQuery();
                double currentBalance;

                if (!rs.next()) {
                    // Account doesn't exist
                    if (!isDeposit) {
                        return new TransactionResult(false, "Account not found", 0);
                    }
                    // Create new account for deposit
                    currentBalance = 0.0;
                    try (PreparedStatement ins = c.prepareStatement(
                            "INSERT INTO accounts (owner, balance) VALUES (?, ?)")) {
                        ins.setString(1, owner.trim());
                        ins.setDouble(2, currentBalance);
                        ins.executeUpdate();
                    }
                } else {
                    currentBalance = rs.getDouble("balance");
                }

                double newBalance = isDeposit ? currentBalance + amount : currentBalance - amount;

                if (newBalance < 0) {
                    return new TransactionResult(false, "Insufficient funds", currentBalance);
                }

                // Update balance
                try (PreparedStatement upd = c.prepareStatement(
                        "UPDATE accounts SET balance=? WHERE owner=?")) {
                    upd.setDouble(1, newBalance);
                    upd.setString(2, owner.trim());
                    upd.executeUpdate();
                }

                String operation = isDeposit ? "Deposit" : "Withdrawal";
                return new TransactionResult(true, operation + " successful", newBalance);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new TransactionResult(false, "Database error", 0);
        }
    }

    public static double getBalance(String owner) {
        try (Connection c = Database.connect();
                PreparedStatement ps = c.prepareStatement("SELECT balance FROM accounts WHERE owner=?")) {
            ps.setString(1, owner.trim());
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble("balance") : 0.0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // Helper method to format currency with Peso symbol
    public static String formatCurrency(double amount) {
        return String.format("₱%.2f", amount);
    }
}