package com.library.ui;

import com.library.entity.Librarian;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginScreen extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private LibraryService libraryService;
    private JButton loginButton;
    
    public LoginScreen() {
        libraryService = new LibraryService();
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(40, 60, 40, 60));
        
        // Logo/Icon panel
        JPanel logoPanel = createLogoPanel();
        mainPanel.add(logoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Title
        JLabel titleLabel = new JLabel("Library Management", JLabel.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(SECONDARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("System", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subtitleLabel.setForeground(PRIMARY_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Login form
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel);
        
        add(mainPanel);
        
        // Set focus and enter key handling
        usernameField.requestFocus();
        setupKeyListeners();
    }
    
    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(BACKGROUND_COLOR);
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create book icon
        JLabel iconLabel = new JLabel("📚");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        logoPanel.add(iconLabel);
        
        return logoPanel;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));
        
        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(LABEL_FONT);
        usernameLabel.setForeground(SECONDARY_COLOR);
        formPanel.add(usernameLabel);
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        
        usernameField = new JTextField();
        usernameField.setFont(LABEL_FONT);
        usernameField.setPreferredSize(new Dimension(300, 40));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(usernameField);
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(LABEL_FONT);
        passwordLabel.setForeground(SECONDARY_COLOR);
        formPanel.add(passwordLabel);
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        
        passwordField = new JPasswordField();
        passwordField.setFont(LABEL_FONT);
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        formPanel.add(passwordField);
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Login button
        loginButton = new JButton("Sign In");
        loginButton.setFont(BUTTON_FONT);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(PRIMARY_COLOR);
        loginButton.setPreferredSize(new Dimension(300, 45));
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new LoginActionListener());
        
        // Hover effect
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(31, 97, 141));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(PRIMARY_COLOR);
            }
        });
        
        formPanel.add(loginButton);
        
        return formPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(BACKGROUND_COLOR);
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        
        JLabel defaultLabel = new JLabel("Default Credentials:", JLabel.CENTER);
        defaultLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        defaultLabel.setForeground(SECONDARY_COLOR);
        defaultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.add(defaultLabel);
        
        footerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JLabel credentialsLabel = new JLabel("Username: admin | Password: admin123", JLabel.CENTER);
        credentialsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        credentialsLabel.setForeground(new Color(127, 140, 141));
        credentialsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.add(credentialsLabel);
        
        return footerPanel;
    }
    
    private void setupKeyListeners() {
        KeyListener enterKeyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        };
        
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }
    
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                showErrorMessage("Please enter both username and password.");
                return;
            }
            
            // Disable button during authentication
            loginButton.setEnabled(false);
            loginButton.setText("Signing In...");
            
            // Simulate loading (in real app, this would be async)
            SwingUtilities.invokeLater(() -> {
                Librarian librarian = libraryService.authenticateLibrarian(username, password);
                
                loginButton.setEnabled(true);
                loginButton.setText("Sign In");
                
                if (librarian != null) {
                    showSuccessMessage("Welcome, " + librarian.getFullName() + "!");
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        new DashboardScreen(librarian, libraryService).setVisible(true);
                    });
                } else {
                    showErrorMessage("Invalid username or password.");
                    passwordField.setText("");
                    usernameField.requestFocus();
                }
            });
        }
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Authentication Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Use default look and feel
            new LoginScreen().setVisible(true);
        });
    }
}