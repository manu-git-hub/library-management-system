package com.airtribe.java.Strategy;

import com.airtribe.java.Entity.Book;
import com.airtribe.java.Enum.BookStatus;

import java.util.*;

public class AuthorBasedRecommendation implements RecommendationStrategy {
    public List<Book> recommend(List<Book> history, Collection<Book> allBooks) {

        Set<String> authors = new HashSet<>();
        Set<String> readBooks = new HashSet<>();

        for (Book b : history) {
            authors.add(b.getAuthor());
            readBooks.add(b.getIsbn());
        }

        List<Book> result = new ArrayList<>();

        for (Book b : allBooks) {

            if (authors.contains(b.getAuthor()) &&
                    b.getStatus() == BookStatus.AVAILABLE &&
                    !readBooks.contains(b.getIsbn())) {

                result.add(b);
            }
        }

        return result.size() > 5 ? result.subList(0, 5) : result;
    }
}
