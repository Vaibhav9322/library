package com.library.ui;

import com.library.entity.Librarian;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardScreen extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color WARNING_COLOR = new Color(243, 156, 18);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font CARD_TITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font CARD_VALUE_FONT = new Font("Segoe UI", Font.BOLD, 36);
    private static final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    private Librarian currentLibrarian;
    private LibraryService libraryService;
    private JPanel contentPanel;
    
    public DashboardScreen(Librarian librarian, LibraryService service) {
        this.currentLibrarian = librarian;
        this.libraryService = service;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("Library Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Sidebar
        add(createSidebarPanel(), BorderLayout.WEST);
        
        // Content area
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        showDashboard();
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(15, 25, 15, 25));
        
        JLabel titleLabel = new JLabel("📚 Library Management System");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(PRIMARY_COLOR);
        
        JLabel userLabel = new JLabel("👤 " + currentLibrarian.getFullName() + " (" + currentLibrarian.getRole() + ")");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        userPanel.add(userLabel);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(SECONDARY_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        String[] menuItems = {
            "🏠 Dashboard", "📚 Books", "👥 Members", 
            "🔄 Issue/Return", "📈 Transactions", 
            "✍️ Authors", "📂 Categories", "🏢 Publishers",
            "📄 Reports", "⚙️ Settings", "🚪 Logout"
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            JButton button = createMenuButton(menuItems[i]);
            sidebarPanel.add(button);
            if (i < menuItems.length - 1) {
                sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        
        return sidebarPanel;
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(MENU_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(SECONDARY_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(250, 45));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(44, 62, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SECONDARY_COLOR);
            }
        });
        
        button.addActionListener(new MenuActionListener(text));
        return button;
    }
    
    private void showDashboard() {
        contentPanel.removeAll();
        
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(BACKGROUND_COLOR);
        
        // Welcome panel
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setBackground(BACKGROUND_COLOR);
        JLabel welcomeLabel = new JLabel("Welcome back, " + currentLibrarian.getFirstName() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(SECONDARY_COLOR);
        welcomePanel.add(welcomeLabel);
        
        dashboardPanel.add(welcomePanel, BorderLayout.NORTH);
        
        // Statistics cards
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBackground(BACKGROUND_COLOR);
        statsPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        int totalBooks = libraryService.getAllBooks().size();
        int totalMembers = libraryService.getAllMembers().size();
        int activeTransactions = (int) libraryService.getAllTransactions().stream()
            .filter(t -> "Active".equals(t.getStatus())).count();
        int overdueBooks = 0; // Calculate overdue books
        
        statsPanel.add(createStatCard("📚 Total Books", String.valueOf(totalBooks), PRIMARY_COLOR));
        statsPanel.add(createStatCard("👥 Total Members", String.valueOf(totalMembers), SUCCESS_COLOR));
        statsPanel.add(createStatCard("🔄 Active Issues", String.valueOf(activeTransactions), WARNING_COLOR));
        statsPanel.add(createStatCard("⚠️ Overdue Books", String.valueOf(overdueBooks), DANGER_COLOR));
        
        dashboardPanel.add(statsPanel, BorderLayout.CENTER);
        
        contentPanel.add(dashboardPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(25, 20, 25, 20)
        ));
        
        // Accent bar
        JPanel accentPanel = new JPanel();
        accentPanel.setBackground(accentColor);
        accentPanel.setPreferredSize(new Dimension(4, 0));
        card.add(accentPanel, BorderLayout.WEST);
        
        // Content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_COLOR);
        contentPanel.setBorder(new EmptyBorder(0, 15, 0, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(CARD_TITLE_FONT);
        titleLabel.setForeground(SECONDARY_COLOR);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(CARD_VALUE_FONT);
        valueLabel.setForeground(accentColor);
        
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(valueLabel, BorderLayout.CENTER);
        
        card.add(contentPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void showReportsPanel() {
        contentPanel.removeAll();
        ReportsPanel reportsPanel = new ReportsPanel(libraryService);
        contentPanel.add(reportsPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showSettingsPanel() {
        contentPanel.removeAll();
        SettingsPanel settingsPanel = new SettingsPanel();
        contentPanel.add(settingsPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private class MenuActionListener implements ActionListener {
        private String menuItem;
        
        public MenuActionListener(String menuItem) {
            this.menuItem = menuItem;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String item = menuItem.substring(2).trim(); // Remove emoji
            switch (item) {
                case "Dashboard":
                    showDashboard();
                    break;
                case "Books":
                    new BookManagementScreen(libraryService).setVisible(true);
                    break;
                case "Members":
                    new MemberManagementScreen(libraryService).setVisible(true);
                    break;
                case "Issue/Return":
                    new IssueReturnScreen(currentLibrarian, libraryService).setVisible(true);
                    break;
                case "Transactions":
                    new TransactionScreen(libraryService).setVisible(true);
                    break;
                case "Authors":
                    new AuthorManagementScreen(libraryService).setVisible(true);
                    break;
                case "Categories":
                    new CategoryManagementScreen(libraryService).setVisible(true);
                    break;
                case "Publishers":
                    new PublisherManagementScreen(libraryService).setVisible(true);
                    break;
                case "Reports":
                    showReportsPanel();
                    break;
                case "Settings":
                    showSettingsPanel();
                    break;
                case "Logout":
                    int result = JOptionPane.showConfirmDialog(DashboardScreen.this, 
                        "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        dispose();
                        new LoginScreen().setVisible(true);
                    }
                    break;
            }
        }
    }
}