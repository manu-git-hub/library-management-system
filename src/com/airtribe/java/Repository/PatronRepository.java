package com.airtribe.java.Repository;

import com.airtribe.java.Entity.Patron;

import java.util.HashMap;
import java.util.Map;

public class PatronRepository {

    private Map<String, Patron> patrons = new HashMap<>();

    public void add(Patron p) { patrons.put(p.getId(), p); }
    public Patron get(String id) { return patrons.get(id); }
}
