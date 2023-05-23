package com.noip;

import java.util.HashMap;

public class DataShare {
    private HashMap<String, String> data = new HashMap<String, String>();

    public void Datashare()
    {
         //this.data = new HashMap<String, String>();
    }

    public synchronized void set(String key, String value)
    {
        this.data.put(key, value);
    }

    public synchronized HashMap get()
    {
        return this.data;
    }
    
}
