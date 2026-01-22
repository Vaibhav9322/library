package com.library.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

public class SettingsPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    
    private JTextField libraryNameField;
    private JTextField finePerDayField;
    private JTextField maxBooksField;
    private JTextField loanPeriodField;
    private JPasswordField adminPasswordField;
    private JPasswordField confirmPasswordField;
    private Properties settings;
    private final String SETTINGS_FILE = "library_settings.properties";
    
    public SettingsPanel() {
        loadSettings();
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("⚙️ System Settings");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(SECONDARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Library Settings Section
        addSectionTitle(contentPanel, gbc, "📚 Library Configuration", 0);
        
        addFormField(contentPanel, gbc, "Library Name:", 1);
        libraryNameField = new JTextField(settings.getProperty("library.name", "Central Library"), 20);
        gbc.gridx = 1; gbc.gridy = 1;
        contentPanel.add(libraryNameField, gbc);
        
        addFormField(contentPanel, gbc, "Fine per Day ($):", 2);
        finePerDayField = new JTextField(settings.getProperty("fine.per.day", "1.0"), 20);
        gbc.gridx = 1; gbc.gridy = 2;
        contentPanel.add(finePerDayField, gbc);
        
        addFormField(contentPanel, gbc, "Max Books per Member:", 3);
        maxBooksField = new JTextField(settings.getProperty("max.books.per.member", "5"), 20);
        gbc.gridx = 1; gbc.gridy = 3;
        contentPanel.add(maxBooksField, gbc);
        
        addFormField(contentPanel, gbc, "Loan Period (days):", 4);
        loanPeriodField = new JTextField(settings.getProperty("loan.period.days", "14"), 20);
        gbc.gridx = 1; gbc.gridy = 4;
        contentPanel.add(loanPeriodField, gbc);
        
        // Security Section
        addSectionTitle(contentPanel, gbc, "🔒 Security Settings", 5);
        
        addFormField(contentPanel, gbc, "New Admin Password:", 6);
        adminPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 6;
        contentPanel.add(adminPasswordField, gbc);
        
        addFormField(contentPanel, gbc, "Confirm Password:", 7);
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 7;
        contentPanel.add(confirmPasswordField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton saveBtn = createStyledButton("💾 Save Settings", SUCCESS_COLOR);
        JButton resetBtn = createStyledButton("🔄 Reset to Default", new Color(243, 156, 18));
        JButton exportBtn = createStyledButton("📤 Export Settings", PRIMARY_COLOR);
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(exportBtn);
        
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 0, 0, 0);
        contentPanel.add(buttonPanel, gbc);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Button actions
        saveBtn.addActionListener(new SaveSettingsListener());
        resetBtn.addActionListener(new ResetSettingsListener());
        exportBtn.addActionListener(new ExportSettingsListener());
    }
    
    private void addSectionTitle(JPanel panel, GridBagConstraints gbc, String title, int row) {
        JLabel sectionLabel = new JLabel(title);
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sectionLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(row == 0 ? 0 : 20, 0, 10, 0);
        panel.add(sectionLabel, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(SECONDARY_COLOR);
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(label, gbc);
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 35));
        return button;
    }
    
    private void loadSettings() {
        settings = new Properties();
        try (FileInputStream fis = new FileInputStream(SETTINGS_FILE)) {
            settings.load(fis);
        } catch (IOException e) {
            // Use default settings if file doesn't exist
            setDefaultSettings();
        }
    }
    
    private void setDefaultSettings() {
        settings.setProperty("library.name", "Central Library");
        settings.setProperty("fine.per.day", "1.0");
        settings.setProperty("max.books.per.member", "5");
        settings.setProperty("loan.period.days", "14");
    }
    
    private void saveSettings() {
        settings.setProperty("library.name", libraryNameField.getText());
        settings.setProperty("fine.per.day", finePerDayField.getText());
        settings.setProperty("max.books.per.member", maxBooksField.getText());
        settings.setProperty("loan.period.days", loanPeriodField.getText());
        
        try (FileOutputStream fos = new FileOutputStream(SETTINGS_FILE)) {
            settings.store(fos, "Library Management System Settings");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error saving settings: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Settings saved successfully!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void resetToDefaults() {
        setDefaultSettings();
        libraryNameField.setText(settings.getProperty("library.name"));
        finePerDayField.setText(settings.getProperty("fine.per.day"));
        maxBooksField.setText(settings.getProperty("max.books.per.member"));
        loanPeriodField.setText(settings.getProperty("loan.period.days"));
        adminPasswordField.setText("");
        confirmPasswordField.setText("");
    }
    
    private void exportSettings() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Settings");
        fileChooser.setSelectedFile(new File("library_settings_backup.properties"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile())) {
                settings.store(fos, "Library Management System Settings Backup");
                JOptionPane.showMessageDialog(this, 
                    "Settings exported successfully!", 
                    "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error exporting settings: " + e.getMessage(), 
                    "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class SaveSettingsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Validate password if entered
            char[] password = adminPasswordField.getPassword();
            char[] confirmPassword = confirmPasswordField.getPassword();
            
            if (password.length > 0) {
                if (!java.util.Arrays.equals(password, confirmPassword)) {
                    JOptionPane.showMessageDialog(SettingsPanel.this, 
                        "Passwords do not match!", 
                        "Password Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (password.length < 6) {
                    JOptionPane.showMessageDialog(SettingsPanel.this, 
                        "Password must be at least 6 characters long!", 
                        "Password Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Validate numeric fields
            try {
                Double.parseDouble(finePerDayField.getText());
                Integer.parseInt(maxBooksField.getText());
                Integer.parseInt(loanPeriodField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SettingsPanel.this, 
                    "Please enter valid numeric values!", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            saveSettings();
            
            // Clear password fields
            adminPasswordField.setText("");
            confirmPasswordField.setText("");
        }
    }
    
    private class ResetSettingsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(SettingsPanel.this, 
                "Are you sure you want to reset all settings to default values?", 
                "Confirm Reset", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                resetToDefaults();
            }
        }
    }
    
    private class ExportSettingsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            exportSettings();
        }
    }
}