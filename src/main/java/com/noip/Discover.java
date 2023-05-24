package com.noip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Scanner;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Discover implements Runnable
{
    String check_ip;
    DataShare ds;

    public Discover(String ip, DataShare ds)
    {
        check_ip = ip;
        this.ds = ds;    
    }

    public void run()
    {
        String mac = check(check_ip);
        if (! mac.isEmpty() ) {
            this.ds.set(check_ip, mac);
        }
    }

    public static String check(String ip)
    {
        int timeout = 1000;
        String mac = "";
        try {
            if (InetAddress.getByName(ip).isReachable(timeout)) {
                //System.out.println(ip + " is reachable");
                mac = getMacAddress(ip);
                //System.out.println(ip + " " + mac);
                return mac;
            }

        } catch (Exception e) {
            System.out.println("whoops " + e.toString());
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