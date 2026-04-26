package com.airtribe.java.Strategy;

import com.airtribe.java.Entity.Book;

import java.util.Collection;
import java.util.List;

public interface RecommendationStrategy {
    List<Book> recommend(List<Book> history, Collection<Book> allBooks);
}
