package com.airtribe.java.Service;

import com.airtribe.java.Entity.Book;
import com.airtribe.java.Entity.Branch;
import com.airtribe.java.Entity.Patron;
import com.airtribe.java.Entity.Reservation;
import com.airtribe.java.Repository.BookRepository;
import com.airtribe.java.Repository.BranchRepository;
import com.airtribe.java.Repository.PatronRepository;
import com.airtribe.java.Repository.ReservationRepository;
import com.airtribe.java.Strategy.AuthorBasedRecommendation;
import com.airtribe.java.Strategy.RecommendationStrategy;

import java.util.List;
import java.util.logging.Logger;

public class LibraryService {

    private BookRepository bookRepo;
    private PatronRepository patronRepo;
    private BranchRepository branchRepo;
    private ReservationRepository reservationRepo;
    private RecommendationStrategy strategy;
    private NotificationService notificationService;

    private static final Logger logger = Logger.getLogger(LibraryService.class.getName());

    public LibraryService(BookRepository b, PatronRepository p,
                          BranchRepository br, ReservationRepository r) {
        this.bookRepo = b;
        this.patronRepo = p;
        this.branchRepo = br;
        this.reservationRepo = r;
        this.strategy = new AuthorBasedRecommendation();
        this.notificationService = new NotificationService();
    }

    // ================= BRANCH =================
    public void addBranch(String id, String name) {

        if (branchRepo.get(id) != null) {
            logger.warning("Branch already exists with ID: " + id);
            return;
        }

        branchRepo.add(new Branch(id, name));

        logger.info("Branch added → " + id);
    }

    // ================= BOOK =================
    public void addBook(String branchId, String title, String author,
                        String isbn, int year, int quantity) {

        if (branchRepo.get(branchId) == null) {
            logger.warning("Branch not found");
            return;
        }

        Book existing = bookRepo.findByIsbn(branchId, isbn);

        if (existing != null) {
            existing.increaseQuantity(quantity);
            bookRepo.save(branchId, existing);
            logger.info("Stock updated → Total: " + existing.getTotalQuantity());
        } else {
            Book book = com.airtribe.java.Factory.EntityFactory.createBook(title, author, isbn, year, quantity);
            bookRepo.save(branchId, book);
            logger.info("New book added");
        }
    }

    public void search(String branchId, String keyword) {

        // If keyword is same as branchId → return all books
        if (keyword.equalsIgnoreCase(branchId)) {

            var books = bookRepo.findAll(branchId);

            if (books.isEmpty()) {
                logger.info("No books found in branch: " + branchId);
            } else {
                logger.info("All books in branch: " + branchId);
                books.forEach(book -> logger.info(book.toString()));
            }
            return;
        }

        // Normal keyword search
        var books = bookRepo.search(branchId, keyword);

        if (books.isEmpty()) {
            logger.info("No books found for keyword: " + keyword);
        } else {
            books.forEach(book -> logger.info(book.toString()));
        }
    }

    // ================= PATRON =================
    public void addPatron(String id, String name) {

        if (patronRepo.get(id) != null) {
            logger.warning("Patron already exists with ID: " + id);
            return;
        }

        patronRepo.add(com.airtribe.java.Factory.EntityFactory.createPatron(id, name));

        logger.info("Patron added → " + id);
    }

    // ================= CHECKOUT =================
    public void checkout(String branchId, String patronId, String isbn) {

        Book book = bookRepo.findByIsbn(branchId, isbn);
        Patron patron = patronRepo.get(patronId);

        if (book == null || patron == null) {
            logger.warning("Invalid data for checkout");
            return;
        }

        if (book.getAvailableQuantity() == 0) {
            reservationRepo.add(isbn, new Reservation(patronId, isbn));
            logger.info("No copies available → added to reservation for patron: " + patronId);
            return;
        }

        book.borrowBook();
        bookRepo.save(branchId, book);

        patron.getBorrowedHistory().add(book);

        logger.info("Book issued to " + patronId +
                " → Remaining: " + book.getAvailableQuantity());
    }

    // ================= RETURN =================
    public void returnBook(String branchId, String patronId, String isbn) {

        Book book = bookRepo.findByIsbn(branchId, isbn);
        Patron patron = patronRepo.get(patronId);

        if (book == null || patron == null) {
            logger.warning("Return failed → Invalid data");
            return;
        }

        book.returnBook();
        bookRepo.save(branchId, book);

        logger.info("Book returned → ISBN: " + isbn +
                " | Available: " + book.getAvailableQuantity());

        if (book.getAvailableQuantity() > 0) {

            Reservation next = reservationRepo.next(isbn);

            if (next != null) {
                next.complete();

                notificationService.notifyUser(
                        next.getPatronId(),
                        "Book " + isbn + " is now available"
                );

                logger.info("Reservation fulfilled → Notified patron: " + next.getPatronId());
            } else {
                logger.info("No pending reservations for ISBN: " + isbn);
            }
        }
    }

    // ================= REMOVE BOOK =================
    public void removeBook(String branchId, String isbn) {

        Book book = bookRepo.findByIsbn(branchId, isbn);

        if (book == null) {
            logger.warning("Book not found");
            return;
        }

        if (book.getAvailableQuantity() != book.getTotalQuantity()) {
            logger.warning("Cannot remove → copies are currently borrowed");
            return;
        }

        bookRepo.delete(branchId, isbn);
        logger.info("Book removed → " + isbn);
    }

    // ================= TRANSFER =================
    public void transfer(String fromBranch, String toBranch, String isbn, int qty) {

        if (branchRepo.get(fromBranch) == null || branchRepo.get(toBranch) == null) {
            logger.warning("Transfer failed → Invalid branch");
            return;
        }

        Book fromBook = bookRepo.findByIsbn(fromBranch, isbn);

        if (fromBook == null) {
            logger.warning("Transfer failed → Book not found in source branch");
            return;
        }

        if (qty <= 0 || fromBook.getAvailableQuantity() < qty) {
            logger.warning("Transfer failed → Invalid quantity");
            return;
        }

        // Deduct from source branch
        fromBook.increaseQuantity(-qty);
        bookRepo.save(fromBranch, fromBook);

        //  Add to destination branch
        Book toBook = bookRepo.findByIsbn(toBranch, isbn);

        if (toBook != null) {
            toBook.increaseQuantity(qty);
        } else {
            toBook = com.airtribe.java.Factory.EntityFactory.createBook(
                    fromBook.getTitle(),
                    fromBook.getAuthor(),
                    fromBook.getIsbn(),
                    fromBook.getYear(),
                    qty
            );
        }

        bookRepo.save(toBranch, toBook);

        logger.info("Transfer success → ISBN: " + isbn +
                " | Qty: " + qty +
                " | From: " + fromBranch +
                " → To: " + toBranch);
    }

    // ================= RECOMMEND =================
    public void recommend(String patronId, String branchId) {

        Patron patron = patronRepo.get(patronId);

        if (patron == null) {
            logger.warning("Invalid patron");
            return;
        }

        List<Book> recs = strategy.recommend(
                patron.getBorrowedHistory(),
                bookRepo.findAll(branchId)
        );

        if (recs.isEmpty()) {
            logger.info("No recommendations available");
        } else {
            logger.info("Recommended Books:");
            recs.forEach(book -> logger.info(book.toString()));
        }
    }

    // ================= UPDATE BOOK =================
    public void updateBook(String branchId, String isbn, String newTitle, String newAuthor) {

        Book book = bookRepo.findByIsbn(branchId, isbn);

        if (book == null) {
            logger.warning("Book not found");
            return;
        }

        book.setTitle(newTitle);
        book.setAuthor(newAuthor);

        bookRepo.save(branchId, book);

        logger.info("Book updated → " + isbn);
    }

    // ================= UPDATE PATRON =================
    public void updatePatron(String patronId, String newName) {

        Patron patron = patronRepo.get(patronId);

        if (patron == null) {
            logger.warning("Patron not found");
            return;
        }

        patron.setName(newName);

        logger.info("Patron updated → " + patronId);
    }
}