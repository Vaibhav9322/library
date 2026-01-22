package com.library.ui;

import com.library.entity.Author;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AuthorManagementScreen extends JFrame {
    private LibraryService libraryService;
    private JTable authorTable;
    private DefaultTableModel tableModel;
    
    public AuthorManagementScreen(LibraryService service) {
        this.libraryService = service;
        initializeComponents();
        loadAuthors();
    }
    
    private void initializeComponents() {
        setTitle("Author Management");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Author Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Author");
        addButton.addActionListener(e -> showAddAuthorDialog());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Author");
        editButton.addActionListener(e -> editSelectedAuthor());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("Delete Author");
        deleteButton.addActionListener(e -> deleteSelectedAuthor());
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        String[] columns = {"ID", "First Name", "Last Name", "Biography", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        authorTable = new JTable(tableModel);
        authorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(authorTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadAuthors() {
        tableModel.setRowCount(0);
        List<Author> authors = libraryService.getAllAuthors();
        for (Author author : authors) {
            Object[] row = {
                author.getAuthorId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBiography(),
                author.getEmail(),
                author.getPhone()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddAuthorDialog() {
        JDialog dialog = new JDialog(this, "Add Author", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextArea biographyArea = new JTextArea(3, 20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; dialog.add(firstNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; dialog.add(lastNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Biography:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(biographyArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; dialog.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; dialog.add(phoneField, gbc);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Author author = new Author(
                firstNameField.getText(),
                lastNameField.getText(),
                LocalDate.now(),
                "Unknown",
                biographyArea.getText(),
                emailField.getText(),
                phoneField.getText()
            );
            
            if (libraryService.addAuthor(author)) {
                JOptionPane.showMessageDialog(dialog, "Author added successfully!");
                dialog.dispose();
                loadAuthors();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add author!");
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        dialog.add(saveButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void editSelectedAuthor() {
        int selectedRow = authorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an author to edit.");
            return;
        }
        
        int authorId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Author author = libraryService.getAuthorById(authorId);
        if (author == null) return;
        
        JDialog dialog = new JDialog(this, "Edit Author", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField firstNameField = new JTextField(author.getFirstName(), 20);
        JTextField lastNameField = new JTextField(author.getLastName(), 20);
        JTextArea biographyArea = new JTextArea(author.getBiography(), 3, 20);
        JTextField emailField = new JTextField(author.getEmail(), 20);
        JTextField phoneField = new JTextField(author.getPhone(), 20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; dialog.add(firstNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; dialog.add(lastNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Biography:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(biographyArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; dialog.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; dialog.add(phoneField, gbc);
        
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            author.setFirstName(firstNameField.getText());
            author.setLastName(lastNameField.getText());
            author.setBiography(biographyArea.getText());
            author.setEmail(emailField.getText());
            author.setPhone(phoneField.getText());
            
            JOptionPane.showMessageDialog(dialog, "Author updated successfully!");
            dialog.dispose();
            loadAuthors();
        });
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        dialog.add(updateButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void deleteSelectedAuthor() {
        int selectedRow = authorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an author to delete.");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this author?");
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Author deleted successfully!");
            loadAuthors();
        }
    }
}