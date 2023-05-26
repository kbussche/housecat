package com.noip;

import java.util.HashMap;

public class DataShare {
    private HashMap<String, HashMap <String, String>> data = new HashMap<String, HashMap <String, String>>();

    public synchronized void set(String key, HashMap value)
    {
        this.data.put(key, value);
    }

    public synchronized HashMap get()
    {
        return this.data;
    }
}
