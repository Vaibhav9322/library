package com.library.ui;

import com.library.entity.Fine;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class FineManagementScreen extends JFrame {
    private LibraryService libraryService;
    private JTable fineTable;
    private DefaultTableModel tableModel;
    
    public FineManagementScreen(LibraryService service) {
        this.libraryService = service;
        initializeComponents();
        loadFines();
    }
    
    private void initializeComponents() {
        setTitle("Fine Management");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Fine Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton payButton = new JButton("Pay Fine");
        payButton.addActionListener(e -> paySelectedFine());
        buttonPanel.add(payButton);
        
        JButton waiveButton = new JButton("Waive Fine");
        waiveButton.addActionListener(e -> waiveSelectedFine());
        buttonPanel.add(waiveButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadFines());
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        String[] columns = {"Fine ID", "Member ID", "Transaction ID", "Amount", "Reason", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        fineTable = new JTable(tableModel);
        fineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(fineTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadFines() {
        tableModel.setRowCount(0);
        // Sample fine data
        Object[][] fines = {
            {1, 1, 3, 5.0, "Overdue Book", "2024-01-20", "Unpaid"},
            {2, 3, 4, 10.0, "Late Return", "2024-01-25", "Unpaid"},
            {3, 2, 2, 2.0, "Damaged Book", "2024-01-15", "Paid"}
        };
        
        for (Object[] fine : fines) {
            tableModel.addRow(fine);
        }
    }
    
    private void paySelectedFine() {
        int selectedRow = fineTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a fine to pay.");
            return;
        }
        
        double amount = (Double) tableModel.getValueAt(selectedRow, 3);
        int result = JOptionPane.showConfirmDialog(this, 
            "Pay fine amount: $" + amount + "?", 
            "Confirm Payment", JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Paid", selectedRow, 6);
            JOptionPane.showMessageDialog(this, "Fine paid successfully!");
        }
    }
    
    private void waiveSelectedFine() {
        int selectedRow = fineTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a fine to waive.");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Waive this fine?", 
            "Confirm Waive", JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Waived", selectedRow, 6);
            JOptionPane.showMessageDialog(this, "Fine waived successfully!");
        }
    }
}