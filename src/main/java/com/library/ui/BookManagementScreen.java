package com.library.ui;

import com.library.entity.Book;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class BookManagementScreen extends JFrame {
    private LibraryService libraryService;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    
    public BookManagementScreen(LibraryService service) {
        this.libraryService = service;
        initializeComponents();
        loadBooks();
    }
    
    private void initializeComponents() {
        setTitle("Book Management");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Book Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchBooks());
        searchPanel.add(searchButton);
        
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> showAddBookDialog());
        searchPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Book");
        editButton.addActionListener(e -> editSelectedBook());
        searchPanel.add(editButton);
        
        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(e -> deleteSelectedBook());
        searchPanel.add(deleteButton);
        
        add(searchPanel, BorderLayout.SOUTH);
        
        // Table
        String[] columns = {"ID", "ISBN", "Title", "Author ID", "Category ID", "Publisher ID", "Publish Date", "Total Copies", "Available", "Location", "Price", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadBooks() {
        tableModel.setRowCount(0);
        List<Book> books = libraryService.getAllBooks();
        for (Book book : books) {
            Object[] row = {
                book.getBookId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId(),
                book.getCategoryId(),
                book.getPublisherId(),
                book.getPublishDate(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getLocation(),
                book.getPrice(),
                book.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchBooks() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadBooks();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Book> books = libraryService.searchBooks(keyword);
        for (Book book : books) {
            Object[] row = {
                book.getBookId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId(),
                book.getCategoryId(),
                book.getPublisherId(),
                book.getPublishDate(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getLocation(),
                book.getPrice(),
                book.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddBookDialog() {
        JDialog dialog = new JDialog(this, "Add Book", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField isbnField = new JTextField(20);
        JTextField titleField = new JTextField(20);
        JTextField authorIdField = new JTextField(20);
        JTextField categoryIdField = new JTextField(20);
        JTextField publisherIdField = new JTextField(20);
        JTextField publishDateField = new JTextField(20);
        JTextField totalCopiesField = new JTextField(20);
        JTextField locationField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1; dialog.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; dialog.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Author ID:"), gbc);
        gbc.gridx = 1; dialog.add(authorIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Category ID:"), gbc);
        gbc.gridx = 1; dialog.add(categoryIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Publisher ID:"), gbc);
        gbc.gridx = 1; dialog.add(publisherIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; dialog.add(new JLabel("Publish Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; dialog.add(publishDateField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; dialog.add(new JLabel("Total Copies:"), gbc);
        gbc.gridx = 1; dialog.add(totalCopiesField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; dialog.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1; dialog.add(locationField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 8; dialog.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; dialog.add(priceField, gbc);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                Book book = new Book(
                    isbnField.getText(),
                    titleField.getText(),
                    Integer.parseInt(authorIdField.getText()),
                    Integer.parseInt(categoryIdField.getText()),
                    Integer.parseInt(publisherIdField.getText()),
                    LocalDate.parse(publishDateField.getText()),
                    Integer.parseInt(totalCopiesField.getText()),
                    locationField.getText(),
                    Double.parseDouble(priceField.getText())
                );
                
                if (libraryService.addBook(book)) {
                    JOptionPane.showMessageDialog(dialog, "Book added successfully!");
                    dialog.dispose();
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add book!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage());
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        dialog.add(saveButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void editSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to edit.");
            return;
        }
        
        int bookId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Book book = libraryService.getBookById(bookId);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found!");
            return;
        }
        
        JDialog dialog = new JDialog(this, "Edit Book", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField isbnField = new JTextField(book.getIsbn(), 20);
        JTextField titleField = new JTextField(book.getTitle(), 20);
        JTextField authorIdField = new JTextField(String.valueOf(book.getAuthorId()), 20);
        JTextField categoryIdField = new JTextField(String.valueOf(book.getCategoryId()), 20);
        JTextField publisherIdField = new JTextField(String.valueOf(book.getPublisherId()), 20);
        JTextField publishDateField = new JTextField(book.getPublishDate().toString(), 20);
        JTextField totalCopiesField = new JTextField(String.valueOf(book.getTotalCopies()), 20);
        JTextField locationField = new JTextField(book.getLocation(), 20);
        JTextField priceField = new JTextField(String.valueOf(book.getPrice()), 20);
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1; dialog.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; dialog.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Author ID:"), gbc);
        gbc.gridx = 1; dialog.add(authorIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Category ID:"), gbc);
        gbc.gridx = 1; dialog.add(categoryIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Publisher ID:"), gbc);
        gbc.gridx = 1; dialog.add(publisherIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; dialog.add(new JLabel("Publish Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; dialog.add(publishDateField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; dialog.add(new JLabel("Total Copies:"), gbc);
        gbc.gridx = 1; dialog.add(totalCopiesField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; dialog.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1; dialog.add(locationField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 8; dialog.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; dialog.add(priceField, gbc);
        
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            try {
                book.setIsbn(isbnField.getText());
                book.setTitle(titleField.getText());
                book.setAuthorId(Integer.parseInt(authorIdField.getText()));
                book.setCategoryId(Integer.parseInt(categoryIdField.getText()));
                book.setPublisherId(Integer.parseInt(publisherIdField.getText()));
                book.setPublishDate(LocalDate.parse(publishDateField.getText()));
                book.setTotalCopies(Integer.parseInt(totalCopiesField.getText()));
                book.setLocation(locationField.getText());
                book.setPrice(Double.parseDouble(priceField.getText()));
                
                if (libraryService.updateBook(book)) {
                    JOptionPane.showMessageDialog(dialog, "Book updated successfully!");
                    dialog.dispose();
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update book!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage());
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        dialog.add(updateButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void deleteSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?");
        if (result == JOptionPane.YES_OPTION) {
            int bookId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (libraryService.deleteBook(bookId)) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete book!");
            }
        }
    }
}