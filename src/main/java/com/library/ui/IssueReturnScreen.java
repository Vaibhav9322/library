package com.library.ui;

import com.library.entity.Librarian;
import com.library.service.LibraryService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IssueReturnScreen extends JFrame {
    private Librarian currentLibrarian;
    private LibraryService libraryService;
    private JTextField bookIdField;
    private JTextField memberIdField;
    private JTextField transactionIdField;
    
    public IssueReturnScreen(Librarian librarian, LibraryService service) {
        this.currentLibrarian = librarian;
        this.libraryService = service;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("Issue/Return Books");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Issue/Return Books");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Issue Book Tab
        JPanel issuePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        issuePanel.add(new JLabel("Book ID:"), gbc);
        gbc.gridx = 1;
        bookIdField = new JTextField(15);
        issuePanel.add(bookIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        issuePanel.add(new JLabel("Member ID:"), gbc);
        gbc.gridx = 1;
        memberIdField = new JTextField(15);
        issuePanel.add(memberIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton issueButton = new JButton("Issue Book");
        issueButton.addActionListener(new IssueBookActionListener());
        issuePanel.add(issueButton, gbc);
        
        tabbedPane.addTab("Issue Book", issuePanel);
        
        // Return Book Tab
        JPanel returnPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        returnPanel.add(new JLabel("Transaction ID:"), gbc);
        gbc.gridx = 1;
        transactionIdField = new JTextField(15);
        returnPanel.add(transactionIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(new ReturnBookActionListener());
        returnPanel.add(returnButton, gbc);
        
        tabbedPane.addTab("Return Book", returnPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private class IssueBookActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int bookId = Integer.parseInt(bookIdField.getText());
                int memberId = Integer.parseInt(memberIdField.getText());
                
                if (libraryService.issueBook(bookId, memberId, currentLibrarian.getLibrarianId())) {
                    JOptionPane.showMessageDialog(IssueReturnScreen.this, "Book issued successfully!");
                    bookIdField.setText("");
                    memberIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(IssueReturnScreen.this, "Failed to issue book. Check if book is available and member exists.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(IssueReturnScreen.this, "Please enter valid numeric IDs.");
            }
        }
    }
    
    private class ReturnBookActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int transactionId = Integer.parseInt(transactionIdField.getText());
                
                if (libraryService.returnBook(transactionId, currentLibrarian.getLibrarianId())) {
                    JOptionPane.showMessageDialog(IssueReturnScreen.this, "Book returned successfully!");
                    transactionIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(IssueReturnScreen.this, "Failed to return book. Check transaction ID.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(IssueReturnScreen.this, "Please enter valid transaction ID.");
            }
        }
    }
}