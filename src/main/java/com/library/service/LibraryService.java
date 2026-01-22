package com.library.service;

import com.library.dao.*;
import com.library.entity.*;
import java.time.LocalDate;
import java.util.List;

public class LibraryService {
    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private TransactionDAO transactionDAO;
    private LibrarianDAO librarianDAO;
    private AuthorDAO authorDAO;
    private CategoryDAO categoryDAO;
    private PublisherDAO publisherDAO;
    
    public LibraryService() {
        this.bookDAO = new BookDAO();
        this.memberDAO = new MemberDAO();
        this.transactionDAO = new TransactionDAO();
        this.librarianDAO = new LibrarianDAO();
        this.authorDAO = new AuthorDAO();
        this.categoryDAO = new CategoryDAO();
        this.publisherDAO = new PublisherDAO();
    }
    
    // Authentication
    public Librarian authenticateLibrarian(String username, String password) {
        return librarianDAO.authenticate(username, password);
    }
    
    // Book operations
    public boolean addBook(Book book) {
        return bookDAO.addBook(book);
    }
    
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
    
    public Book getBookById(int bookId) {
        return bookDAO.getBookById(bookId);
    }
    
    public boolean updateBook(Book book) {
        return bookDAO.updateBook(book);
    }
    
    public boolean deleteBook(int bookId) {
        return bookDAO.deleteBook(bookId);
    }
    
    public List<Book> searchBooks(String keyword) {
        return bookDAO.searchBooks(keyword);
    }
    
    // Member operations
    public boolean addMember(Member member) {
        return memberDAO.addMember(member);
    }
    
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }
    
    public Member getMemberById(int memberId) {
        return memberDAO.getMemberById(memberId);
    }
    
    public boolean updateMember(Member member) {
        return memberDAO.updateMember(member);
    }
    
    public boolean deleteMember(int memberId) {
        return memberDAO.deleteMember(memberId);
    }
    
    // Transaction operations
    public boolean issueBook(int bookId, int memberId, int librarianId) {
        Book book = bookDAO.getBookById(bookId);
        if (book != null && book.getAvailableCopies() > 0) {
            Transaction transaction = new Transaction(bookId, memberId, librarianId, "Issue");
            if (transactionDAO.addTransaction(transaction)) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
                return bookDAO.updateBook(book);
            }
        }
        return false;
    }
    
    public boolean returnBook(int transactionId, int librarianId) {
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() == transactionId && transaction.getStatus().equals("Active")) {
                transaction.setReturnDate(LocalDate.now());
                transaction.setStatus("Returned");
                
                // Calculate fine if overdue
                if (LocalDate.now().isAfter(transaction.getDueDate())) {
                    long daysOverdue = LocalDate.now().toEpochDay() - transaction.getDueDate().toEpochDay();
                    double fine = daysOverdue * 1.0; // $1 per day
                    transaction.setFineAmount(fine);
                }
                
                if (transactionDAO.updateTransaction(transaction)) {
                    Book book = bookDAO.getBookById(transaction.getBookId());
                    book.setAvailableCopies(book.getAvailableCopies() + 1);
                    return bookDAO.updateBook(book);
                }
            }
        }
        return false;
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }
    
    public List<Transaction> getActiveTransactionsByMember(int memberId) {
        return transactionDAO.getActiveTransactionsByMember(memberId);
    }
    
    // Author operations
    public boolean addAuthor(Author author) {
        return authorDAO.addAuthor(author);
    }
    
    public List<Author> getAllAuthors() {
        return authorDAO.getAllAuthors();
    }
    
    public Author getAuthorById(int authorId) {
        return authorDAO.getAuthorById(authorId);
    }
    
    // Category operations
    public boolean addCategory(Category category) {
        return categoryDAO.addCategory(category);
    }
    
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
    
    public Category getCategoryById(int categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }
    
    // Publisher operations
    public boolean addPublisher(Publisher publisher) {
        return publisherDAO.addPublisher(publisher);
    }
    
    public List<Publisher> getAllPublishers() {
        return publisherDAO.getAllPublishers();
    }
    
    public Publisher getPublisherById(int publisherId) {
        return publisherDAO.getPublisherById(publisherId);
    }
}