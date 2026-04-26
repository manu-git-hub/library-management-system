package com.airtribe.java.Entity;

import com.airtribe.java.Enum.BookStatus;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private int year;

    private int totalQuantity;
    private int availableQuantity;
    private BookStatus status;

    public Book(String title, String author, String isbn, int year, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.totalQuantity = quantity;
        this.availableQuantity = quantity;
        updateStatus();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getYear() {
        return year;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // central place to maintain consistency
    private void updateStatus() {
        if (availableQuantity == 0) {
            status = BookStatus.OUT_OF_STOCK;
        } else {
            status = BookStatus.AVAILABLE;
        }
    }

    public void borrowBook() {
        if (availableQuantity > 0) {
            availableQuantity--;
            updateStatus();
        }
    }

    public void returnBook() {
        if (availableQuantity < totalQuantity) {
            availableQuantity++;
            updateStatus();
        }
    }

    public void increaseQuantity(int qty) {

        this.totalQuantity += qty;
        this.availableQuantity += qty;

        if (totalQuantity < 0) totalQuantity = 0;
        if (availableQuantity < 0) availableQuantity = 0;

        updateStatus();
    }

    @Override
    public String toString() {
        return title + " | " + author +
                " | ISBN: " + isbn +
                " | Available: " + availableQuantity +
                "/" + totalQuantity +
                " | Status: " + status;
    }
}
