package controller;

import model.Database;
import model.User;
import view.DashboardFrame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    public static class LoginResult {
        private final boolean success;
        private final String message;
        private final User user;

        public LoginResult(boolean success, String message, User user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public User getUser() { return user; }
    }

    public static LoginResult authenticate(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return new LoginResult(false, "Please fill both fields", null);
        }

        try (Connection conn = Database.connect();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT role FROM users WHERE username=? AND password=?")
        ) {
            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(username.trim(), rs.getString("role"));
                return new LoginResult(true, "Login successful", user);
            } else {
                return new LoginResult(false, "Invalid credentials", null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginResult(false, "Database error", null);
        }
    }
}