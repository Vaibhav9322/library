package com.library.dao;

import com.library.entity.Transaction;
import com.library.util.FileUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    
    public boolean addTransaction(Transaction transaction) {
        try {
            transaction.setTransactionId(getNextId());
            String line = transactionToString(transaction);
            FileUtil.appendToFile(TRANSACTIONS_FILE, line);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        List<String> lines = FileUtil.readFile(TRANSACTIONS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                transactions.add(stringToTransaction(line));
            }
        }
        return transactions;
    }
    
    public boolean updateTransaction(Transaction transaction) {
        List<String> lines = FileUtil.readFile(TRANSACTIONS_FILE);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Transaction existingTransaction = stringToTransaction(line);
                if (existingTransaction.getTransactionId() == transaction.getTransactionId()) {
                    updatedLines.add(transactionToString(transaction));
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
        }
        
        if (found) {
            FileUtil.writeFile(TRANSACTIONS_FILE, updatedLines);
        }
        return found;
    }
    
    public List<Transaction> getActiveTransactionsByMember(int memberId) {
        List<Transaction> transactions = new ArrayList<>();
        List<String> lines = FileUtil.readFile(TRANSACTIONS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Transaction transaction = stringToTransaction(line);
                if (transaction.getMemberId() == memberId && "Active".equals(transaction.getStatus())) {
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }
    
    private String transactionToString(Transaction transaction) {
        return transaction.getTransactionId() + "|" + transaction.getBookId() + "|" + transaction.getMemberId() + "|" +
               transaction.getLibrarianId() + "|" + transaction.getIssueDate() + "|" + transaction.getDueDate() + "|" +
               (transaction.getReturnDate() != null ? transaction.getReturnDate() : "null") + "|" +
               transaction.getTransactionType() + "|" + transaction.getStatus() + "|" + transaction.getFineAmount();
    }
    
    private Transaction stringToTransaction(String line) {
        String[] parts = line.split("\\|");
        Transaction transaction = new Transaction();
        transaction.setTransactionId(Integer.parseInt(parts[0]));
        transaction.setBookId(Integer.parseInt(parts[1]));
        transaction.setMemberId(Integer.parseInt(parts[2]));
        transaction.setLibrarianId(Integer.parseInt(parts[3]));
        transaction.setIssueDate(LocalDate.parse(parts[4]));
        transaction.setDueDate(LocalDate.parse(parts[5]));
        if (!"null".equals(parts[6])) {
            transaction.setReturnDate(LocalDate.parse(parts[6]));
        }
        transaction.setTransactionType(parts[7]);
        transaction.setStatus(parts[8]);
        transaction.setFineAmount(Double.parseDouble(parts[9]));
        return transaction;
    }
    
    private int getNextId() {
        List<String> lines = FileUtil.readFile(TRANSACTIONS_FILE);
        int maxId = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                if (id > maxId) maxId = id;
            }
        }
        return maxId + 1;
    }
}