package com.airtribe.java.Factory;

import com.airtribe.java.Entity.Book;
import com.airtribe.java.Entity.Patron;

public class EntityFactory {

    // Centralised creation for Book entity
    public static Book createBook(String title, String author, String isbn, int year, int quantity) {
        return new Book(title, author, isbn, year, quantity);
    }

    // Centralised creation for Patron entity
    public static Patron createPatron(String id, String name) {
        return new Patron(id, name);
    }
}
