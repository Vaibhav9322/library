package com.library.ui;

import com.library.entity.Member;
import com.library.service.LibraryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MemberManagementScreen extends JFrame {
    private LibraryService libraryService;
    private JTable memberTable;
    private DefaultTableModel tableModel;
    
    public MemberManagementScreen(LibraryService service) {
        this.libraryService = service;
        initializeComponents();
        loadMembers();
    }
    
    private void initializeComponents() {
        setTitle("Member Management");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Member Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Member");
        addButton.addActionListener(e -> showAddMemberDialog());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("Edit Member");
        editButton.addActionListener(e -> editSelectedMember());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("Delete Member");
        deleteButton.addActionListener(e -> deleteSelectedMember());
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Table
        String[] columns = {"ID", "Membership #", "First Name", "Last Name", "Email", "Phone", "Address", "Join Date", "Expiry Date", "Type", "Status", "Outstanding Fines"};
        tableModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(tableModel);
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(memberTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadMembers() {
        tableModel.setRowCount(0);
        List<Member> members = libraryService.getAllMembers();
        for (Member member : members) {
            Object[] row = {
                member.getMemberId(),
                member.getMembershipNumber(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.getJoinDate(),
                member.getExpiryDate(),
                member.getMembershipType(),
                member.getStatus(),
                member.getOutstandingFines()
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAddMemberDialog() {
        JDialog dialog = new JDialog(this, "Add Member", true);
        dialog.setSize(400, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField membershipNumberField = new JTextField(20);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextArea addressArea = new JTextArea(3, 20);
        JComboBox<String> membershipTypeCombo = new JComboBox<>(new String[]{"Regular", "Premium", "Student"});
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Membership Number:"), gbc);
        gbc.gridx = 1; dialog.add(membershipNumberField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; dialog.add(firstNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; dialog.add(lastNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; dialog.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; dialog.add(phoneField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; dialog.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(addressArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; dialog.add(new JLabel("Membership Type:"), gbc);
        gbc.gridx = 1; dialog.add(membershipTypeCombo, gbc);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                Member member = new Member(
                    membershipNumberField.getText(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    addressArea.getText(),
                    (String) membershipTypeCombo.getSelectedItem()
                );
                
                if (libraryService.addMember(member)) {
                    JOptionPane.showMessageDialog(dialog, "Member added successfully!");
                    dialog.dispose();
                    loadMembers();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add member!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage());
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        dialog.add(saveButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void editSelectedMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to edit.");
            return;
        }
        
        int memberId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Member member = libraryService.getMemberById(memberId);
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Member not found!");
            return;
        }
        
        JDialog dialog = new JDialog(this, "Edit Member", true);
        dialog.setSize(400, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField membershipNumberField = new JTextField(member.getMembershipNumber(), 20);
        JTextField firstNameField = new JTextField(member.getFirstName(), 20);
        JTextField lastNameField = new JTextField(member.getLastName(), 20);
        JTextField emailField = new JTextField(member.getEmail(), 20);
        JTextField phoneField = new JTextField(member.getPhone(), 20);
        JTextArea addressArea = new JTextArea(member.getAddress(), 3, 20);
        JComboBox<String> membershipTypeCombo = new JComboBox<>(new String[]{"Regular", "Premium", "Student"});
        membershipTypeCombo.setSelectedItem(member.getMembershipType());
        
        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Membership Number:"), gbc);
        gbc.gridx = 1; dialog.add(membershipNumberField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; dialog.add(firstNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; dialog.add(lastNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; dialog.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; dialog.add(phoneField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; dialog.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; dialog.add(new JScrollPane(addressArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; dialog.add(new JLabel("Membership Type:"), gbc);
        gbc.gridx = 1; dialog.add(membershipTypeCombo, gbc);
        
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            try {
                member.setMembershipNumber(membershipNumberField.getText());
                member.setFirstName(firstNameField.getText());
                member.setLastName(lastNameField.getText());
                member.setEmail(emailField.getText());
                member.setPhone(phoneField.getText());
                member.setAddress(addressArea.getText());
                member.setMembershipType((String) membershipTypeCombo.getSelectedItem());
                
                if (libraryService.updateMember(member)) {
                    JOptionPane.showMessageDialog(dialog, "Member updated successfully!");
                    dialog.dispose();
                    loadMembers();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update member!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage());
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        dialog.add(updateButton, gbc);
        
        dialog.setVisible(true);
    }
    
    private void deleteSelectedMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.");
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member?");
        if (result == JOptionPane.YES_OPTION) {
            int memberId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (libraryService.deleteMember(memberId)) {
                JOptionPane.showMessageDialog(this, "Member deleted successfully!");
                loadMembers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete member!");
            }
        }
    }
}