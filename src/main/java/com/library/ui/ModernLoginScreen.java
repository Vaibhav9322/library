package com.library.ui;

import com.library.entity.Librarian;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class ModernLoginScreen extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(74, 144, 226);
    private static final Color SECONDARY_COLOR = new Color(45, 52, 54);
    private static final Color SUCCESS_COLOR = new Color(0, 184, 148);
    private static final Color ERROR_COLOR = new Color(255, 118, 117);
    private static final Color BACKGROUND_COLOR = new Color(240, 242, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private LibraryService libraryService;
    
    public ModernLoginScreen() {
        this.libraryService = new LibraryService();
        initializeComponents();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        setTitle("Library Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with gradient background
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());
        
        // Login card
        JPanel loginCard = createLoginCard();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 50);
        mainPanel.add(loginCard, gbc);
        
        add(mainPanel);
    }
    
    private JPanel createLoginCard() {
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(40, 40, 40, 40));
        card.setPreferredSize(new Dimension(350, 450));
        
        // Logo and title
        JLabel logoLabel = new JLabel("📚", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Library Management", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(SECONDARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Sign in to your account", SwingConstants.CENTER);
        subtitleLabel.setFont(SUBTITLE_FONT);
        subtitleLabel.setForeground(new Color(116, 125, 136));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(FIELD_FONT);
        usernameLabel.setForeground(SECONDARY_COLOR);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        usernameField = createStyledTextField();
        usernameField.setText("admin"); // Default for demo
        
        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(FIELD_FONT);
        passwordLabel.setForeground(SECONDARY_COLOR);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        passwordField = createStyledPasswordField();
        passwordField.setText("admin123"); // Default for demo
        
        // Login button
        loginButton = createStyledButton("Sign In", PRIMARY_COLOR);
        
        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Footer
        JLabel footerLabel = new JLabel("© 2024 Library Management System", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(149, 165, 166));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add components with spacing
        card.add(logoLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(subtitleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        
        card.add(usernameLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(usernameField);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        
        card.add(passwordLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(passwordField);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        
        card.add(loginButton);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(statusLabel);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(footerLabel);
        
        return card;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(FIELD_FONT);
        field.setPreferredSize(new Dimension(270, 40));
        field.setMaximumSize(new Dimension(270, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(FIELD_FONT);
        field.setPreferredSize(new Dimension(270, 40));
        field.setMaximumSize(new Dimension(270, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setPreferredSize(new Dimension(270, 45));
        button.setMaximumSize(new Dimension(270, 45));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
    }
    
    private void setupEventHandlers() {
        loginButton.addActionListener(e -> performLogin());
        
        // Enter key support
        KeyListener enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };
        
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Please enter both username and password", ERROR_COLOR);
            return;
        }
        
        // Show loading state
        loginButton.setText("Signing in...");
        loginButton.setEnabled(false);
        statusLabel.setText("Authenticating...");
        statusLabel.setForeground(PRIMARY_COLOR);
        
        // Simulate authentication delay
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(500); // Simulate network delay
                
                Librarian librarian = libraryService.authenticateLibrarian(username, password);
                
                if (librarian != null) {
                    showStatus("Login successful! Welcome " + librarian.getFirstName(), SUCCESS_COLOR);
                    
                    // Delay before opening dashboard
                    Timer timer = new Timer(1000, e -> {
                        dispose();
                        new DashboardScreen(librarian, libraryService).setVisible(true);
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    showStatus("Invalid username or password", ERROR_COLOR);
                    resetLoginButton();
                }
            } catch (InterruptedException ex) {
                showStatus("Login failed. Please try again.", ERROR_COLOR);
                resetLoginButton();
            }
        });
    }
    
    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
    
    private void resetLoginButton() {
        loginButton.setText("Sign In");
        loginButton.setEnabled(true);
    }
    
    // Gradient background panel
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(116, 185, 255),
                0, getHeight(), new Color(74, 144, 226)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModernLoginScreen().setVisible(true);
        });
    }
}