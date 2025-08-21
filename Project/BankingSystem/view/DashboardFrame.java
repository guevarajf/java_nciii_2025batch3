package view;

import controller.DashboardController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DashboardFrame extends JFrame {
    private User currentUser;

    // Modern color palette
    private final Color PRIMARY_BLUE = new Color(25, 118, 210);
    private final Color SECONDARY_BLUE = new Color(33, 150, 243);
    private final Color SUCCESS_GREEN = new Color(76, 175, 80);
    private final Color WARNING_ORANGE = new Color(255, 193, 7);
    private final Color ERROR_RED = new Color(244, 67, 54);
    private final Color PURPLE = new Color(156, 39, 176);
    private final Color TEAL = new Color(0, 150, 136);
    private final Color INDIGO = new Color(103, 58, 183);
    private final Color BACKGROUND = new Color(248, 249, 250);
    private final Color CARD_BACKGROUND = new Color(255, 255, 255);
    private final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private final Color TEXT_SECONDARY = new Color(108, 117, 125);

    public DashboardFrame() {
        this.currentUser = DashboardController.getCurrentUser();

        setTitle("Jon Guevara Banking System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));

        // Set background color
        getContentPane().setBackground(BACKGROUND);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Header Panel with gradient background
        JPanel headerPanel = createHeaderPanel();

        // Main content panel
        JPanel mainPanel = createMainPanel();

        // Footer panel
        JPanel footerPanel = createFooterPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Create gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY_BLUE,
                        getWidth(), getHeight(), SECONDARY_BLUE);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        headerPanel.setPreferredSize(new Dimension(0, 120));

        // Welcome section
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);

        // User avatar and info
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setOpaque(false);

        // Create user avatar
        JLabel avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(60, 60));
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(new Color(255, 255, 255, 50));
        avatarLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 3),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setText(String.valueOf(currentUser.getUsername().charAt(0)).toUpperCase());
        avatarLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        avatarLabel.setForeground(Color.WHITE);

        // User details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JLabel welcomeLabel = new JLabel("Welcome back,");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setForeground(new Color(255, 255, 255, 180));

        JLabel nameLabel = new JLabel(currentUser.getUsername());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE);

        JLabel roleLabel = new JLabel(currentUser.getRole() + " Dashboard");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setForeground(new Color(255, 255, 255, 160));

        detailsPanel.add(welcomeLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(2));
        detailsPanel.add(roleLabel);

        userInfoPanel.add(avatarLabel);
        userInfoPanel.add(detailsPanel);

        // Current time/date
        JLabel dateTimeLabel = new JLabel(java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")));
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateTimeLabel.setForeground(new Color(255, 255, 255, 160));
        dateTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        welcomePanel.add(userInfoPanel, BorderLayout.WEST);
        welcomePanel.add(dateTimeLabel, BorderLayout.EAST);

        headerPanel.add(welcomePanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Get role-based cards
        List<JPanel> roleBasedCards = getRoleBasedCards();

        // Create dynamic layout based on number of cards
        int cardCount = roleBasedCards.size();
        int columns = Math.min(3, cardCount); // Max 3 columns
        int rows = (int) Math.ceil((double) cardCount / columns);

        mainPanel.setLayout(new GridLayout(rows, columns, 15, 15));

        // Add cards to panel
        for (JPanel card : roleBasedCards) {
            mainPanel.add(card);
        }

        return mainPanel;
    }

    private List<JPanel> getRoleBasedCards() {
        List<JPanel> cards = new ArrayList<>();
        String userRole = currentUser.getRole().toLowerCase();

        // Based on the user roles table, add appropriate cards
        switch (userRole) {
            case "customer":
                // Customer permissions: Check Balance, Account Operation, Logout
                cards.add(createActionCard(
                        "Check Balance",
                        "View account balance",
                        "📊",
                        PURPLE,
                        () -> openAccountFrame("balance")));

                cards.add(createActionCard(
                        "Logout",
                        "Sign out of the system",
                        "🚪",
                        ERROR_RED,
                        () -> logout()));
                break;

            case "teller":
                // Teller permissions: Create Account, Deposit Money, Withdraw Money, Check
                // Balance, Account Operation, Logout
                cards.add(createActionCard(
                        "Create New Account",
                        "Add new user to the system",
                        "👤",
                        SUCCESS_GREEN,
                        () -> openCreateAccountFrame()));

                cards.add(createActionCard(
                        "Deposit Money",
                        "Add funds to account",
                        "💰",
                        SECONDARY_BLUE,
                        () -> openAccountFrame("deposit")));

                cards.add(createActionCard(
                        "Withdraw Money",
                        "Remove funds from account",
                        "💸",
                        WARNING_ORANGE,
                        () -> openAccountFrame("withdraw")));

                cards.add(createActionCard(
                        "Check Balance",
                        "View account balance",
                        "📊",
                        PURPLE,
                        () -> openAccountFrame("balance")));

                cards.add(createActionCard(
                        "Account Operations",
                        "Manage account activities",
                        "⚙️",
                        TEAL,
                        () -> openAccountFrame("operations")));

                cards.add(createActionCard(
                        "Logout",
                        "Sign out of the system",
                        "🚪",
                        ERROR_RED,
                        () -> logout()));
                break;

            case "manager":
                // Manager permissions: Create Account, Change Password, Check Balance, Account
                // Operation, Logout
                cards.add(createActionCard(
                        "Create New Account",
                        "Add new user to the system",
                        "👤",
                        SUCCESS_GREEN,
                        () -> openCreateAccountFrame()));

                cards.add(createActionCard(
                        "Change Password",
                        "Update user passwords",
                        "🔒",
                        INDIGO,
                        () -> openChangePasswordFrame()));

                cards.add(createActionCard(
                        "Check Balance",
                        "View account balance",
                        "📊",
                        PURPLE,
                        () -> openAccountFrame("balance")));

                cards.add(createActionCard(
                        "Account Operations",
                        "Manage account activities",
                        "⚙️",
                        TEAL,
                        () -> openAccountFrame("operations")));

                cards.add(createActionCard(
                        "Logout",
                        "Sign out of the system",
                        "🚪",
                        ERROR_RED,
                        () -> logout()));
                break;

            case "admin":
                // Admin permissions: Create Account, Change Password, Deposit Money, Withdraw
                // Money, Check Balance, Account Operation, Logout
                cards.add(createActionCard(
                        "Create New Account",
                        "Add new user to the system",
                        "👤",
                        SUCCESS_GREEN,
                        () -> openCreateAccountFrame()));

                cards.add(createActionCard(
                        "Change Password",
                        "Update user passwords",
                        "🔒",
                        INDIGO,
                        () -> openChangePasswordFrame()));

                cards.add(createActionCard(
                        "Deposit Money",
                        "Add funds to account",
                        "💰",
                        SECONDARY_BLUE,
                        () -> openAccountFrame("deposit")));

                cards.add(createActionCard(
                        "Withdraw Money",
                        "Remove funds from account",
                        "💸",
                        WARNING_ORANGE,
                        () -> openAccountFrame("withdraw")));

                cards.add(createActionCard(
                        "Check Balance",
                        "View account balance",
                        "📊",
                        PURPLE,
                        () -> openAccountFrame("balance")));

                cards.add(createActionCard(
                        "Account Operations",
                        "Manage account activities",
                        "⚙️",
                        TEAL,
                        () -> openAccountFrame("operations")));

                cards.add(createActionCard(
                        "Logout",
                        "Sign out of the system",
                        "🚪",
                        ERROR_RED,
                        () -> logout()));
                break;

            default:
                // Default case - only logout
                cards.add(createActionCard(
                        "Logout",
                        "Sign out of the system",
                        "🚪",
                        ERROR_RED,
                        () -> logout()));
                break;
        }

        return cards;
    }

    private JPanel createActionCard(String title, String description, String icon, Color accentColor, Runnable action) {
        JPanel card = new JPanel() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw card background with shadow effect
                if (isHovered) {
                    g2d.setColor(new Color(0, 0, 0, 20));
                    g2d.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, 15, 15);
                }

                g2d.setColor(CARD_BACKGROUND);
                g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 15, 15);

                // Draw accent border
                g2d.setColor(accentColor);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 15, 15);
            }
        };

        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(250, 180));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setOpaque(false);

        // Icon panel
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        iconPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconPanel.add(iconLabel);

        // Text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(descLabel);

        // Action button with the title as button text
        JButton actionButton = new JButton(title);
        actionButton.setBackground(accentColor);
        actionButton.setForeground(Color.BLACK);
        actionButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        actionButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        actionButton.setFocusPainted(false);
        actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionButton.addActionListener(e -> action.run());

        // Hover effects
        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JPanel) e.getSource()).repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JPanel) e.getSource()).repaint();
            }
        };

        card.addMouseListener(hoverEffect);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JPanel) e.getSource()).repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JPanel) e.getSource()).repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                action.run();
            }
        });

        card.add(iconPanel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(actionButton, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(52, 58, 64));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        JLabel systemLabel = new JLabel("Jon Guevara Banking System v0.5");
        systemLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        systemLabel.setForeground(new Color(248, 249, 250));

        JLabel userLabel = new JLabel(
                "Logged in as: " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        userLabel.setForeground(new Color(173, 181, 189));

        JLabel statusLabel = new JLabel("● System Online");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setForeground(SUCCESS_GREEN);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(systemLabel);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(userLabel);
        rightPanel.add(statusLabel);

        footerPanel.add(leftPanel, BorderLayout.WEST);
        footerPanel.add(rightPanel, BorderLayout.EAST);

        return footerPanel;
    }

    private void openCreateAccountFrame() {
        new CreateAccountFrame().setVisible(true);
    }

    private void openAccountFrame(String mode) {
        new AccountFrame(mode).setVisible(true);
    }

    private void openChangePasswordFrame() {
        new ChangePasswordFrame().setVisible(true);
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            // Show logout message
            JOptionPane.showMessageDialog(
                    this,
                    "Logged out successfully. Salamat for using Philippine Banking System!",
                    "Logout",
                    JOptionPane.INFORMATION_MESSAGE);

            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}