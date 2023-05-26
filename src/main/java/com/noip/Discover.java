package com.noip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Scanner;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;


public class Discover implements Runnable
{
    String check_ip;
    DataShare ds;
    MacVendor data;

    public Discover(String ip, DataShare ds, MacVendor data)
    {
        check_ip = ip;
        this.ds = ds;
        this.data = data;
    }

    public void run()
    {
        String mac = check(check_ip);

        if (! mac.isEmpty() ) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("mac", mac);

            map.put("vendor", this.getVendorFromMac(mac));
            this.ds.set(check_ip, map);
        }
    }

    public String getVendorFromMac(String mac)
    {
        String[] parts = mac.split(":");
        String lookup = parts[0] + parts[1] + parts[2];

        return this.data.get(lookup);
    }

    public static String check(String ip)
    {
        int timeout = 1000;
        String mac = "";
        try {
            if (InetAddress.getByName(ip).isReachable(timeout)) {
                //System.out.println(ip + " is reachable");
                mac = getMacAddress(ip);
                return mac;
            }

        } catch (Exception e) {
            return mac;
        }

        return mac;
    }

    public static String getMacAddress(String ip) throws IOException
    {
        String mac = "";
        String cmd = "arp " + ip;
        Scanner s = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        String[] parts = result.split(" ");

        for (String part: parts) {
            if (validateMac(part)) {
                mac = part;
            }
        }

        return mac;
    }

    private static boolean validateMac(String mac) {
        Pattern p = Pattern.compile("^([a-fA-F0-9][a-fA-F0-9][:-]){5}[a-fA-F0-9][a-fA-F0-9]$");
        Matcher m = p.matcher(mac);
        return m.find();
    }
}