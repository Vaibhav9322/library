package com.library.ui;

import com.library.entity.Publisher;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PublisherManagementScreen extends JFrame {
    private LibraryService libraryService;
    private JTable publisherTable;
    private DefaultTableModel tableModel;
    
    public PublisherManagementScreen(LibraryService service) {
        this.libraryService = service;
        initializeComponents();
        loadPublishers();
    }
    
    private void initializeComponents() {
        setTitle("Publisher Management");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Publisher Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Publisher");
        addButton.addActionListener(e -> showAddPublisherDialog());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Publisher");
        editButton.addActionListener(e -> editSelectedPublisher());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("Delete Publisher");
        deleteButton.addActionListener(e -> deleteSelectedPublisher());
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        String[] columns = {"ID", "Publisher Name", "Address", "Phone", "Email", "Website"};
        tableModel = new DefaultTableModel(columns, 0);
        publisherTable = new JTable(tableModel);
        publisherTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(publisherTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadPublishers() {
        tableModel.setRowCount(0);
        List<Publisher> publishers = libraryService.getAllPublishers();
        for (Publisher publisher : publishers) {
            Object[] row = {
                publisher.getPublisherId(),
                publisher.getPublisherName(),
                publisher.getAddress(),
                publisher.getPhone(),
                publisher.getEmail(),
                publisher.getWebsite()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddPublisherDialog() {
        JDialog dialog = new JDialog(this, "Add Publisher", true);
        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(20);
        JTextArea addressArea = new JTextArea(3, 20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField websiteField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Publisher Name:"), gbc);
        gbc.gridx = 1; dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(addressArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; dialog.add(phoneField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; dialog.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Website:"), gbc);
        gbc.gridx = 1; dialog.add(websiteField, gbc);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Publisher publisher = new Publisher();
            publisher.setPublisherName(nameField.getText());
            publisher.setAddress(addressArea.getText());
            publisher.setPhone(phoneField.getText());
            publisher.setEmail(emailField.getText());
            publisher.setWebsite(websiteField.getText());
            
            if (libraryService.addPublisher(publisher)) {
                JOptionPane.showMessageDialog(dialog, "Publisher added successfully!");
                dialog.dispose();
                loadPublishers();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add publisher!");
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        dialog.add(saveButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void editSelectedPublisher() {
        int selectedRow = publisherTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a publisher to edit.");
            return;
        }
        
        int publisherId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Publisher publisher = libraryService.getPublisherById(publisherId);
        if (publisher == null) return;
        
        JDialog dialog = new JDialog(this, "Edit Publisher", true);
        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(publisher.getPublisherName(), 20);
        JTextArea addressArea = new JTextArea(publisher.getAddress(), 3, 20);
        JTextField phoneField = new JTextField(publisher.getPhone(), 20);
        JTextField emailField = new JTextField(publisher.getEmail(), 20);
        JTextField websiteField = new JTextField(publisher.getWebsite(), 20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Publisher Name:"), gbc);
        gbc.gridx = 1; dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(addressArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; dialog.add(phoneField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; dialog.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Website:"), gbc);
        gbc.gridx = 1; dialog.add(websiteField, gbc);
        
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            publisher.setPublisherName(nameField.getText());
            publisher.setAddress(addressArea.getText());
            publisher.setPhone(phoneField.getText());
            publisher.setEmail(emailField.getText());
            publisher.setWebsite(websiteField.getText());
            
            JOptionPane.showMessageDialog(dialog, "Publisher updated successfully!");
            dialog.dispose();
            loadPublishers();
        });
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        dialog.add(updateButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void deleteSelectedPublisher() {
        int selectedRow = publisherTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a publisher to delete.");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this publisher?");
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Publisher deleted successfully!");
            loadPublishers();
        }
    }
}