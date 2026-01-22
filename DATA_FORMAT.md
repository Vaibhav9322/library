# Data Storage Structure

The application stores all data in text files within the `data/` directory:

## File Format
All files use pipe-delimited (|) format for data separation.

## Files Created:
- `librarians.txt` - Librarian accounts and authentication
- `books.txt` - Book inventory and details
- `members.txt` - Library member information
- `transactions.txt` - Book issue/return transactions
- `authors.txt` - Author information (optional)
- `categories.txt` - Book categories (optional)
- `publishers.txt` - Publisher information (optional)
- `fines.txt` - Fine records (optional)
- `reservations.txt` - Book reservations (optional)

## Default Data:
- Default admin account: admin/admin123 (automatically created)

## Data Format Examples:

### librarians.txt
```
1|EMP001|Admin|User|admin@library.com|1234567890|admin|admin123|Admin|2024-01-01|Active
```

### books.txt
```
1|978-0123456789|Sample Book|1|1|1|2024-01-01|5|5|A1-Shelf1|29.99|Available
```

### members.txt
```
1|MEM001|John|Doe|john@email.com|1234567890|123 Main St|2024-01-01|2025-01-01|Regular|Active|0.0
```

### transactions.txt
```
1|1|1|1|2024-01-01|2024-01-15|null|Issue|Active|0.0
```