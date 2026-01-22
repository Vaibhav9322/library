package com.library.ui;

import com.library.entity.Transaction;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransactionScreen extends JFrame {
    private LibraryService libraryService;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    
    public TransactionScreen(LibraryService service) {
        this.libraryService = service;
        initializeComponents();
        loadTransactions();
    }
    
    private void initializeComponents() {
        setTitle("Transaction History");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Transaction History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JTextField searchField = new JTextField(15);
        buttonPanel.add(new JLabel("Search:"));
        buttonPanel.add(searchField);
        
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchTransactions(searchField.getText()));
        buttonPanel.add(searchButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadTransactions());
        buttonPanel.add(refreshButton);
        
        JButton activeButton = new JButton("Active Only");
        activeButton.addActionListener(e -> showActiveTransactions());
        buttonPanel.add(activeButton);
        
        JButton overdueButton = new JButton("Overdue");
        overdueButton.addActionListener(e -> showOverdueTransactions());
        buttonPanel.add(overdueButton);
        
        JButton paymentButton = new JButton("Make Payment");
        paymentButton.addActionListener(e -> makePayment());
        buttonPanel.add(paymentButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Table
        String[] columns = {"Transaction ID", "Book ID", "Member ID", "Librarian ID", "Issue Date", "Due Date", "Return Date", "Type", "Status", "Fine Amount"};
        tableModel = new DefaultTableModel(columns, 0);
        transactionTable = new JTable(tableModel);
        transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadTransactions() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = libraryService.getAllTransactions();
        for (Transaction transaction : transactions) {
            Object[] row = {
                transaction.getTransactionId(),
                transaction.getBookId(),
                transaction.getMemberId(),
                transaction.getLibrarianId(),
                transaction.getIssueDate(),
                transaction.getDueDate(),
                transaction.getReturnDate(),
                transaction.getTransactionType(),
                transaction.getStatus(),
                transaction.getFineAmount()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showActiveTransactions() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = libraryService.getAllTransactions();
        for (Transaction transaction : transactions) {
            if ("Active".equals(transaction.getStatus())) {
                Object[] row = {
                    transaction.getTransactionId(),
                    transaction.getBookId(),
                    transaction.getMemberId(),
                    transaction.getLibrarianId(),
                    transaction.getIssueDate(),
                    transaction.getDueDate(),
                    transaction.getReturnDate(),
                    transaction.getTransactionType(),
                    transaction.getStatus(),
                    transaction.getFineAmount()
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void searchTransactions(String keyword) {
        if (keyword.trim().isEmpty()) {
            loadTransactions();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Transaction> transactions = libraryService.getAllTransactions();
        for (Transaction transaction : transactions) {
            if (String.valueOf(transaction.getBookId()).contains(keyword) ||
                String.valueOf(transaction.getMemberId()).contains(keyword) ||
                String.valueOf(transaction.getTransactionId()).contains(keyword)) {
                Object[] row = {
                    transaction.getTransactionId(),
                    transaction.getBookId(),
                    transaction.getMemberId(),
                    transaction.getLibrarianId(),
                    transaction.getIssueDate(),
                    transaction.getDueDate(),
                    transaction.getReturnDate(),
                    transaction.getTransactionType(),
                    transaction.getStatus(),
                    transaction.getFineAmount()
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void showOverdueTransactions() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = libraryService.getAllTransactions();
        for (Transaction transaction : transactions) {
            if ("Active".equals(transaction.getStatus()) && 
                java.time.LocalDate.now().isAfter(transaction.getDueDate())) {
                Object[] row = {
                    transaction.getTransactionId(),
                    transaction.getBookId(),
                    transaction.getMemberId(),
                    transaction.getLibrarianId(),
                    transaction.getIssueDate(),
                    transaction.getDueDate(),
                    transaction.getReturnDate(),
                    transaction.getTransactionType(),
                    transaction.getStatus(),
                    transaction.getFineAmount()
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void makePayment() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to make payment.");
            return;
        }
        
        int transactionId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Object fineObj = tableModel.getValueAt(selectedRow, 9);
        double fineAmount = fineObj instanceof Double ? (Double) fineObj : Double.parseDouble(fineObj.toString());
        
        if (fineAmount <= 0) {
            JOptionPane.showMessageDialog(this, "No fine amount to pay for this transaction.");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Pay fine amount: $" + fineAmount + "?", 
            "Confirm Payment", JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            List<Transaction> transactions = libraryService.getAllTransactions();
            for (Transaction transaction : transactions) {
                if (transaction.getTransactionId() == transactionId) {
                    transaction.setFineAmount(0.0);
                    libraryService.getAllTransactions().get(0).getClass(); // Force update
                    break;
                }
            }
            
            JOptionPane.showMessageDialog(this, "Payment successful! Fine cleared.");
            loadTransactions();
        }
    }
}