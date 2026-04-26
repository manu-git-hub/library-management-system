package com.java.library.entity;

public class InventoryItems {
    private Book book;
    private int quantity;
    private int availableQuantity;

    public InventoryItems(Book book, int quantity, int availableQuantity) {
        this.book = book;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
