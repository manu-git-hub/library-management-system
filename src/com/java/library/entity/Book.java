package com.java.library.entity;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private String currentBranchId;

    public Book(String isbn, String title, String author, int publicationYear, String currentBranchId) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.currentBranchId = currentBranchId;
    }

    
}