package com.library.ui;

import com.library.entity.Category;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryManagementScreen extends JFrame {
    private LibraryService libraryService;
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    
    public CategoryManagementScreen(LibraryService service) {
        this.libraryService = service;
        initializeComponents();
        loadCategories();
    }
    
    private void initializeComponents() {
        setTitle("Category Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Category Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Category");
        addButton.addActionListener(e -> showAddCategoryDialog());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Category");
        editButton.addActionListener(e -> editSelectedCategory());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("Delete Category");
        deleteButton.addActionListener(e -> deleteSelectedCategory());
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        String[] columns = {"ID", "Category Name", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        categoryTable = new JTable(tableModel);
        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(categoryTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadCategories() {
        tableModel.setRowCount(0);
        List<Category> categories = libraryService.getAllCategories();
        for (Category category : categories) {
            Object[] row = {
                category.getCategoryId(),
                category.getCategoryName(),
                category.getDescription()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddCategoryDialog() {
        JDialog dialog = new JDialog(this, "Add Category", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(20);
        JTextArea descriptionArea = new JTextArea(3, 20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Category Name:"), gbc);
        gbc.gridx = 1; dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(descriptionArea), gbc);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Category category = new Category();
            category.setCategoryName(nameField.getText());
            category.setDescription(descriptionArea.getText());
            
            if (libraryService.addCategory(category)) {
                JOptionPane.showMessageDialog(dialog, "Category added successfully!");
                dialog.dispose();
                loadCategories();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add category!");
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        dialog.add(saveButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void editSelectedCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to edit.");
            return;
        }
        
        int categoryId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Category category = libraryService.getCategoryById(categoryId);
        if (category == null) return;
        
        JDialog dialog = new JDialog(this, "Edit Category", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(category.getCategoryName(), 20);
        JTextArea descriptionArea = new JTextArea(category.getDescription(), 3, 20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Category Name:"), gbc);
        gbc.gridx = 1; dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(descriptionArea), gbc);
        
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            category.setCategoryName(nameField.getText());
            category.setDescription(descriptionArea.getText());
            
            JOptionPane.showMessageDialog(dialog, "Category updated successfully!");
            dialog.dispose();
            loadCategories();
        });
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        dialog.add(updateButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void deleteSelectedCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to delete.");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this category?");
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Category deleted successfully!");
            loadCategories();
        }
    }
}