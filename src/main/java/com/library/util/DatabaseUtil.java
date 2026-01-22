package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:library.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        }
        return connection;
    }

    private static void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // Create tables
            stmt.execute("CREATE TABLE IF NOT EXISTS categories (category_id INTEGER PRIMARY KEY AUTOINCREMENT, category_name TEXT UNIQUE, description TEXT)");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS publishers (publisher_id INTEGER PRIMARY KEY AUTOINCREMENT, publisher_name TEXT, address TEXT, phone TEXT, email TEXT, website TEXT)");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS authors (author_id INTEGER PRIMARY KEY AUTOINCREMENT, first_name TEXT, last_name TEXT, birth_date DATE, nationality TEXT, biography TEXT, email TEXT)");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS librarians (librarian_id INTEGER PRIMARY KEY AUTOINCREMENT, employee_id TEXT UNIQUE, first_name TEXT, last_name TEXT, email TEXT, phone TEXT, username TEXT UNIQUE, password TEXT, role TEXT, hire_date DATE, status TEXT)");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS members (member_id INTEGER PRIMARY KEY AUTOINCREMENT, membership_number TEXT UNIQUE, first_name TEXT, last_name TEXT, email TEXT, phone TEXT, address TEXT, join_date DATE, expiry_date DATE, membership_type TEXT, status TEXT, outstanding_fines REAL)");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS books (book_id INTEGER PRIMARY KEY AUTOINCREMENT, isbn TEXT UNIQUE, title TEXT, author_id INTEGER, category_id INTEGER, publisher_id INTEGER, publish_date DATE, total_copies INTEGER, available_copies INTEGER, location TEXT, price REAL, status TEXT, FOREIGN KEY(author_id) REFERENCES authors(author_id), FOREIGN KEY(category_id) REFERENCES categories(category_id), FOREIGN KEY(publisher_id) REFERENCES publishers(publisher_id))");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, book_id INTEGER, member_id INTEGER, librarian_id INTEGER, issue_date DATE, due_date DATE, return_date DATE, transaction_type TEXT, status TEXT, fine_amount REAL, FOREIGN KEY(book_id) REFERENCES books(book_id), FOREIGN KEY(member_id) REFERENCES members(member_id), FOREIGN KEY(librarian_id) REFERENCES librarians(librarian_id))");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS fines (fine_id INTEGER PRIMARY KEY AUTOINCREMENT, member_id INTEGER, transaction_id INTEGER, amount REAL, reason TEXT, fine_date DATE, paid_date DATE, status TEXT, FOREIGN KEY(member_id) REFERENCES members(member_id), FOREIGN KEY(transaction_id) REFERENCES transactions(transaction_id))");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS reservations (reservation_id INTEGER PRIMARY KEY AUTOINCREMENT, book_id INTEGER, member_id INTEGER, reservation_date DATE, expiry_date DATE, status TEXT, FOREIGN KEY(book_id) REFERENCES books(book_id), FOREIGN KEY(member_id) REFERENCES members(member_id))");
            
            // Insert default admin user
            stmt.execute("INSERT OR IGNORE INTO librarians (employee_id, first_name, last_name, email, phone, username, password, role, hire_date, status) VALUES ('EMP001', 'Admin', 'User', 'admin@library.com', '1234567890', 'admin', 'admin123', 'Admin', date('now'), 'Active')");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}