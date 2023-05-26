package com.noip;

import java.util.HashMap;
import java.util.Scanner;
import java.io.*;



public class MacVendor
{
    HashMap<String, String> map = new HashMap<String, String> ();

    public void load()
    {
        this.loadMacVendors();
    }

    private void loadMacVendors()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("macs.dat"));

            String[] parts;
            String line = "";

            while ((line = br.readLine()) != null) {
                parts = line.split(",");
                this.map.put(parts[0].toLowerCase(), parts[1]);
            }

         } catch (Exception e) {
            return;
        }
    }

    public String get(String key)
    {
        return this.map.get(key);
    }
}