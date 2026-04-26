package com.airtribe.java.Entity;

import java.util.*;

public class Patron {
    private String id;
    private String name;
    private List<Book> borrowedHistory = new ArrayList<>();

    public Patron(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Book> getBorrowedHistory() { return borrowedHistory; }

    public void setName(String name) {
        this.name = name;
    }
}

