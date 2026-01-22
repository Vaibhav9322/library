package com.library.ui;

import com.library.entity.*;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReportsPanel extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    private LibraryService libraryService;
    private JTable table;
    private DefaultTableModel model;
    private JLabel reportTitle;
    
    public ReportsPanel(LibraryService service) {
        this.libraryService = service;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("📊 Reports");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(new Color(52, 73, 94));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton booksBtn = createReportButton("📚 Books Report");
        JButton membersBtn = createReportButton("👥 Members Report");
        JButton transactionsBtn = createReportButton("🔄 Active Issues");
        JButton overdueBtn = createReportButton("⚠️ Overdue Books");
        JButton finesBtn = createReportButton("💰 Fines Report");
        
        buttonPanel.add(booksBtn);
        buttonPanel.add(membersBtn);
        buttonPanel.add(transactionsBtn);
        buttonPanel.add(overdueBtn);
        buttonPanel.add(finesBtn);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder("Report Data"));
        
        reportTitle = new JLabel("Select a report to view data");
        reportTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        reportTitle.setBorder(new EmptyBorder(10, 10, 10, 10));
        tablePanel.add(reportTitle, BorderLayout.NORTH);
        
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        add(tablePanel, BorderLayout.SOUTH);
        
        // Button actions
        booksBtn.addActionListener(e -> generateBooksReport());
        membersBtn.addActionListener(e -> generateMembersReport());
        transactionsBtn.addActionListener(e -> generateActiveTransactionsReport());
        overdueBtn.addActionListener(e -> generateOverdueReport());
        finesBtn.addActionListener(e -> generateFinesReport());
    }
    
    private JButton createReportButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }
    
    private void generateBooksReport() {
        reportTitle.setText("📚 Books Inventory Report");
        model.setColumnIdentifiers(new String[]{
            "Book ID", "Title", "ISBN", "Author ID", "Category ID", "Total Copies", "Available", "Price"
        });
        model.setRowCount(0);
        
        List<Book> books = libraryService.getAllBooks();
        for (Book book : books) {
            model.addRow(new Object[]{
                book.getBookId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthorId(),
                book.getCategoryId(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                "$" + book.getPrice()
            });
        }
    }
    
    private void generateMembersReport() {
        reportTitle.setText("👥 Members Report");
        model.setColumnIdentifiers(new String[]{
            "Member ID", "Name", "Email", "Phone", "Membership Type", "Join Date"
        });
        model.setRowCount(0);
        
        List<Member> members = libraryService.getAllMembers();
        for (Member member : members) {
            model.addRow(new Object[]{
                member.getMemberId(),
                member.getFirstName() + " " + member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getMembershipType(),
                member.getJoinDate()
            });
        }
    }
    
    private void generateActiveTransactionsReport() {
        reportTitle.setText("🔄 Active Transactions Report");
        model.setColumnIdentifiers(new String[]{
            "Transaction ID", "Book ID", "Member ID", "Issue Date", "Due Date", "Status"
        });
        model.setRowCount(0);
        
        List<Transaction> activeTransactions = libraryService.getAllTransactions()
            .stream()
            .filter(t -> "Active".equals(t.getStatus()))
            .collect(Collectors.toList());
            
        for (Transaction transaction : activeTransactions) {
            model.addRow(new Object[]{
                transaction.getTransactionId(),
                transaction.getBookId(),
                transaction.getMemberId(),
                transaction.getIssueDate(),
                transaction.getDueDate(),
                transaction.getStatus()
            });
        }
    }
    
    private void generateOverdueReport() {
        reportTitle.setText("⚠️ Overdue Books Report");
        model.setColumnIdentifiers(new String[]{
            "Transaction ID", "Book ID", "Member ID", "Due Date", "Days Overdue", "Fine Amount"
        });
        model.setRowCount(0);
        
        List<Transaction> overdueTransactions = libraryService.getAllTransactions()
            .stream()
            .filter(t -> "Active".equals(t.getStatus()) && 
                        LocalDate.now().isAfter(t.getDueDate()))
            .collect(Collectors.toList());
            
        for (Transaction transaction : overdueTransactions) {
            long daysOverdue = LocalDate.now().toEpochDay() - transaction.getDueDate().toEpochDay();
            double fine = daysOverdue * 1.0; // $1 per day
            
            model.addRow(new Object[]{
                transaction.getTransactionId(),
                transaction.getBookId(),
                transaction.getMemberId(),
                transaction.getDueDate(),
                daysOverdue + " days",
                "$" + fine
            });
        }
    }
    
    private void generateFinesReport() {
        reportTitle.setText("💰 Fines Report");
        model.setColumnIdentifiers(new String[]{
            "Transaction ID", "Book ID", "Member ID", "Fine Amount", "Status"
        });
        model.setRowCount(0);
        
        List<Transaction> transactionsWithFines = libraryService.getAllTransactions()
            .stream()
            .filter(t -> t.getFineAmount() > 0)
            .collect(Collectors.toList());
            
        for (Transaction transaction : transactionsWithFines) {
            model.addRow(new Object[]{
                transaction.getTransactionId(),
                transaction.getBookId(),
                transaction.getMemberId(),
                "$" + transaction.getFineAmount(),
                transaction.getStatus()
            });
        }
    }
}