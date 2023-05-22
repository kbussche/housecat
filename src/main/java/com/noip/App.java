package com.noip;

import java.net.InetAddress;
import java.util.Scanner;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Starting" );

        try {
            getMacAddress("192.168.1.1");

        } catch (Exception e) {
            System.out.println("nope");
        }

        /*
        DataShare data_share = new DataShare();

        discover(data_share);

        System.out.println("Scan Completed");
        data_share.get().forEach((key, value) -> System.out.println(key + " " + value));
        */
    }

    /*
    public static void discover(DataShare ds)
    {
        long initialT = System.currentTimeMillis();
        long finalT = 0;

        try {
            checkHosts("192.168.1", ds);
            finalT = System.currentTimeMillis();

        } catch (Exception e) {
            //System.out.println("whoops: " + e.toString());
        }

        //System.out.println("Scan Completed taking " + (finalT - initialT) + " miliseconds approximately!");
    }

    public static void checkHosts(String subnet, DataShare ds) throws Exception{
        int timeout=1000;

        Thread t;

        for (int i=1;i<255;i++) {
            String host=subnet + "." + i;
            Discover d = new Discover(host, ds);

            t = new Thread(d);

            t.start();
        }

        //t.join();
    }
    */


    public static void getMacAddress(String ip) throws IOException
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

        System.out.println("result.. " + mac);
    }

    private static boolean validateMac(String mac) {
        System.out.println("|"+mac+"|");
        Pattern p = Pattern.compile("^([a-fA-F0-9][a-fA-F0-9][:-]){5}[a-fA-F0-9][a-fA-F0-9]$");
        Matcher m = p.matcher(mac);
        return m.find();
    }

}
