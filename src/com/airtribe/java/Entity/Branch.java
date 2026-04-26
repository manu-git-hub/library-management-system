package com.airtribe.java.Entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Branch {

    private String id;
    private String name;

    public Branch(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

}
