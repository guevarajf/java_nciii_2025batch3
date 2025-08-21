import java.sql.*;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * GcashApp - Online Banking System with User Authentication
 * Implements secure user authentication for online banking features
 * Following Java SE Platform Security Architecture principles
 */
public class GcashApp {
    
  // Database connection details (configure as needed)
private static final String DB_URL = "jdbc:mysql://localhost:3306/gcash_db";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = "P@ssw0rd";



    // Security constants
    private static final int SALT_LENGTH = 32;
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    
    public static void main(String[] args) {
        GcashApp app = new GcashApp();
        app.initializeDatabase();
        app.runApplication();
    }
    
    /**
     * Initialize database and create users table if it doesn't exist
     */
    private void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                number VARCHAR(20) UNIQUE NOT NULL,
                pin VARCHAR(255) NOT NULL,
                salt VARCHAR(255) NOT NULL,
                login_attempts INT DEFAULT 0,
                account_locked BOOLEAN DEFAULT FALSE,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Main application runner with menu system
     */
    private void runApplication() {
        Scanner scanner = new Scanner(System.in);
        UserAuthentication auth = new UserAuthentication();
        
        while (true) {
            System.out.println("\n=== GCASH ONLINE BANKING SYSTEM ===");
            System.out.println("1. Register New User");
            System.out.println("2. User Login");
            System.out.println("3. Change PIN");
            System.out.println("4. Logout");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        auth.registerUser(scanner);
                        break;
                    case 2:
                        auth.loginUser(scanner);
                        break;
                    case 3:
                        auth.changePin(scanner);
                        break;
                    case 4:
                        auth.logout();
                        break;
                    case 5:
                        System.out.println("Thank you for using Gcash Banking System!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    /**
     * Get database connection
     */
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    /**
     * User Authentication Class
     * Handles all user authentication operations
     */
    static class UserAuthentication {
        private Integer currentUserId = null;
        private String currentUserName = null;
        
        /**
         * Register a new user with validation
         */
        public void registerUser(Scanner scanner) {
            System.out.println("\n=== USER REGISTRATION ===");
            
            try {
                // Collect user information with validation
                String name = getValidatedInput(scanner, "Enter full name: ", this::validateName);
                String email = getValidatedInput(scanner, "Enter email: ", this::validateEmail);
                String number = getValidatedInput(scanner, "Enter phone number: ", this::validatePhoneNumber);
                String pin = getValidatedInput(scanner, "Enter 6-digit PIN: ", this::validatePin);
                
                // Check if user already exists
                if (userExists(email, number)) {
                    System.out.println("Error: User with this email or phone number already exists!");
                    return;
                }
                
                // Generate salt and hash PIN
                String salt = generateSalt();
                String hashedPin = hashPin(pin, salt);
                
                // Insert user into database
                String insertSQL = "INSERT INTO users (name, email, number, pin, salt) VALUES (?, ?, ?, ?, ?)";
                
                try (Connection conn = getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                    
                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setString(3, number);
                    pstmt.setString(4, hashedPin);
                    pstmt.setString(5, salt);
                    
                    int rowsAffected = pstmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        ResultSet rs = pstmt.getGeneratedKeys();
                        if (rs.next()) {
                            int userId = rs.getInt(1);
                            System.out.println("Registration successful! User ID: " + userId);
                            System.out.println("Welcome, " + name + "!");
                        }
                    }
                }
                
            } catch (SQLException e) {
                System.err.println("Registration failed: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error during registration: " + e.getMessage());
            }
        }
        
        /**
         * User login with security measures
         */
        public void loginUser(Scanner scanner) {
            System.out.println("\n=== USER LOGIN ===");
            
            try {
                System.out.print("Enter email or phone number: ");
                String identifier = scanner.nextLine().trim();
                
                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine().trim();
                
                // Check user credentials and account status
                String selectSQL = """
                    SELECT id, name, email, number, pin, salt, login_attempts, account_locked 
                    FROM users WHERE (email = ? OR number = ?) AND account_locked = FALSE
                    """;
                
                try (Connection conn = getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
                    
                    pstmt.setString(1, identifier);
                    pstmt.setString(2, identifier);
                    
                    ResultSet rs = pstmt.executeQuery();
                    
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        String name = rs.getString("name");
                        String storedPin = rs.getString("pin");
                        String salt = rs.getString("salt");
                        int loginAttempts = rs.getInt("login_attempts");
                        
                        // Verify PIN
                        String hashedPin = hashPin(pin, salt);
                        
                        if (hashedPin.equals(storedPin)) {
                            // Successful login
                            resetLoginAttempts(userId);
                            currentUserId = userId;
                            currentUserName = name;
                            
                            System.out.println("Login successful! Welcome back, " + name);
                            showBankingFeatures(scanner);
                        } else {
                            // Failed login
                            handleFailedLogin(userId, loginAttempts);
                        }
                    } else {
                        System.out.println("Login failed: Invalid credentials or account is locked.");
                    }
                }
                
            } catch (SQLException e) {
                System.err.println("Login error: " + e.getMessage());
            }
        }
        
        /**
         * Display banking features after successful login
         */
        private void showBankingFeatures(Scanner scanner) {
            System.out.println("\n=== ONLINE BANKING FEATURES ===");
            System.out.println("Available services for user ID: " + currentUserId);
            System.out.println("1. Check Balance");
            System.out.println("2. Transfer Money");
            System.out.println("3. Transaction History");
            System.out.println("4. Pay Bills");
            System.out.println("5. Update Profile");
            System.out.println("Note: Full banking features would be implemented here.");
        }
        
        /**
         * Change user PIN with authentication
         */
        public void changePin(Scanner scanner) {
            if (currentUserId == null) {
                System.out.println("Please login first to change PIN.");
                return;
            }
            
            System.out.println("\n=== CHANGE PIN ===");
            
            try {
                System.out.print("Enter current PIN: ");
                String currentPin = scanner.nextLine().trim();
                
                // Verify current PIN
                if (!verifyCurrentPin(currentUserId, currentPin)) {
                    System.out.println("Current PIN is incorrect!");
                    return;
                }
                
                String newPin = getValidatedInput(scanner, "Enter new 6-digit PIN: ", this::validatePin);
                String confirmPin = getValidatedInput(scanner, "Confirm new PIN: ", this::validatePin);
                
                if (!newPin.equals(confirmPin)) {
                    System.out.println("PIN confirmation does not match!");
                    return;
                }
                
                // Update PIN in database
                String salt = generateSalt();
                String hashedPin = hashPin(newPin, salt);
                
                String updateSQL = "UPDATE users SET pin = ?, salt = ? WHERE id = ?";
                
                try (Connection conn = getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                    
                    pstmt.setString(1, hashedPin);
                    pstmt.setString(2, salt);
                    pstmt.setInt(3, currentUserId);
                    
                    int rowsUpdated = pstmt.executeUpdate();
                    
                    if (rowsUpdated > 0) {
                        System.out.println("PIN changed successfully!");
                    } else {
                        System.out.println("Failed to change PIN. Please try again.");
                    }
                }
                
            } catch (SQLException e) {
                System.err.println("Error changing PIN: " + e.getMessage());
            }
        }
        
        /**
         * Logout current user
         */
        public void logout() {
            if (currentUserId != null) {
                System.out.println("Goodbye, " + currentUserName + "! You have been logged out.");
                currentUserId = null;
                currentUserName = null;
            } else {
                System.out.println("No user is currently logged in.");
            }
        }
        
        // Validation methods
        private boolean validateName(String name) {
            return name != null && name.trim().length() >= 2 && name.matches("[a-zA-Z\\s]+");
        }
        
        private boolean validateEmail(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            return email != null && Pattern.matches(emailRegex, email);
        }
        
        private boolean validatePhoneNumber(String number) {
            return number != null && number.matches("\\d{10,15}");
        }
        
        private boolean validatePin(String pin) {
            return pin != null && pin.matches("\\d{6}");
        }
        
        // Security utility methods
        private String generateSalt() {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        }
        
        private String hashPin(String pin, String salt) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(salt.getBytes());
                byte[] hashedPin = md.digest(pin.getBytes());
                return Base64.getEncoder().encodeToString(hashedPin);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-256 algorithm not available", e);
            }
        }
        
        // Helper methods
        private String getValidatedInput(Scanner scanner, String prompt, ValidationFunction validator) {
            while (true) {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (validator.validate(input)) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
        
        private boolean userExists(String email, String number) throws SQLException {
            String checkSQL = "SELECT COUNT(*) FROM users WHERE email = ? OR number = ?";
            
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(checkSQL)) {
                
                pstmt.setString(1, email);
                pstmt.setString(2, number);
                
                ResultSet rs = pstmt.executeQuery();
                return rs.next() && rs.getInt(1) > 0;
            }
        }
        
        private boolean verifyCurrentPin(int userId, String pin) throws SQLException {
            String selectSQL = "SELECT pin, salt FROM users WHERE id = ?";
            
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
                
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    String storedPin = rs.getString("pin");
                    String salt = rs.getString("salt");
                    String hashedPin = hashPin(pin, salt);
                    return hashedPin.equals(storedPin);
                }
                
                return false;
            }
        }
        
        private void handleFailedLogin(int userId, int currentAttempts) throws SQLException {
            int newAttempts = currentAttempts + 1;
            
            if (newAttempts >= MAX_LOGIN_ATTEMPTS) {
                // Lock account
                String lockSQL = "UPDATE users SET login_attempts = ?, account_locked = TRUE WHERE id = ?";
                try (Connection conn = getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(lockSQL)) {
                    
                    pstmt.setInt(1, newAttempts);
                    pstmt.setInt(2, userId);
                    pstmt.executeUpdate();
                    
                    System.out.println("Account locked due to multiple failed login attempts. Contact support.");
                }
            } else {
                // Increment login attempts
                String updateSQL = "UPDATE users SET login_attempts = ? WHERE id = ?";
                try (Connection conn = getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                    
                    pstmt.setInt(1, newAttempts);
                    pstmt.setInt(2, userId);
                    pstmt.executeUpdate();
                    
                    int remainingAttempts = MAX_LOGIN_ATTEMPTS - newAttempts;
                    System.out.println("Login failed. " + remainingAttempts + " attempts remaining.");
                }
            }
        }
        
        private void resetLoginAttempts(int userId) throws SQLException {
            String resetSQL = "UPDATE users SET login_attempts = 0 WHERE id = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(resetSQL)) {
                
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
            }
        }
        
        @FunctionalInterface
        private interface ValidationFunction {
            boolean validate(String input);
        }
    }
}