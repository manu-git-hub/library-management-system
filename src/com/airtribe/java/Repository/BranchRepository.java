package com.airtribe.java.Repository;

import com.airtribe.java.Entity.Branch;

import java.util.HashMap;
import java.util.Map;

public class BranchRepository {
    private Map<String, Branch> branches = new HashMap<>();

    public void add(Branch b) { branches.put(b.getId(), b); }
    public Branch get(String id) { return branches.get(id); }
}

