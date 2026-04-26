package com.airtribe.java.Repository;

import com.airtribe.java.Entity.Book;

import java.util.*;

public class BookRepository {

    // branchId -> (isbn -> book)
    private Map<String, Map<String, Book>> data = new HashMap<>();

    private Map<String, Book> getBranchMap(String branchId) {
        return data.computeIfAbsent(branchId, k -> new HashMap<>());
    }

    // SAVE / UPDATE
    public void save(String branchId, Book book) {
        getBranchMap(branchId).put(book.getIsbn(), book);
    }

    // FIND
    public Book findByIsbn(String branchId, String isbn) {
        Map<String, Book> map = data.get(branchId);
        return map != null ? map.get(isbn) : null;
    }

    // DELETE
    public void delete(String branchId, String isbn) {
        Map<String, Book> map = data.get(branchId);
        if (map != null) map.remove(isbn);
    }

    // ALL BOOKS
    public Collection<Book> findAll(String branchId) {
        Map<String, Book> map = data.get(branchId);
        return map != null ? map.values() : Collections.emptyList();
    }

    // SEARCH
    public List<Book> search(String branchId, String keyword) {
        List<Book> result = new ArrayList<>();

        for (Book b : findAll(branchId)) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    b.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                    b.getIsbn().contains(keyword)) {
                result.add(b);
            }
        }
        return result;
    }
}
