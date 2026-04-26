package com.airtribe.java.Repository;

import com.airtribe.java.Entity.Reservation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ReservationRepository {

    private Map<String, Queue<Reservation>> map = new HashMap<>();

    public void add(String isbn, Reservation r) {
        map.computeIfAbsent(isbn, k -> new LinkedList<>()).add(r);
    }

    public Reservation next(String isbn) {
        Queue<Reservation> q = map.get(isbn);
        if (q == null) return null;
        return q.poll();
    }
}
