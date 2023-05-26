package com.noip;

import java.net.InetAddress;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.net.InetAddress;


import java.io.FileReader;
/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Starting" );
        DataShare data_share = new DataShare();

        MacVendor mv = new MacVendor();
        mv.load();

        discover(data_share, mv);

        System.out.println("Scan Completed");
        data_share.get().forEach((key, value)  -> {
            System.out.println(key + " " + value);
            try {
                PortScanner p = new PortScanner();
                p.runPortScan(key + "", 5000);
            } catch (Exception e) {}
        });
    }

    public static void discover(DataShare ds, MacVendor mv)
    {
        try {
            String[] parts = getSystemIp().split("[.]");
            String ip_prefix = parts[0] + "." + parts[1] + "." + parts[2];
            System.out.println("Scanning: " + ip_prefix + ".*");
            checkHosts(ip_prefix, ds, mv);

        } catch (Exception e) {
            System.out.println("whoops: " + e.toString());
        }
    }

    public static void checkHosts(String subnet, DataShare ds, MacVendor mv) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(150);

        int timeout=1000;

        for (int i=1;i<255;i++) {
            String host=subnet + "." + i;
            Discover d = new Discover(host, ds, mv);
            executor.execute(d);
        }
        executor.shutdown();

        while (!executor.isTerminated()) {
        }

        System.out.println("Finished all threads");
    }

    private static String getSystemIp()
    {
        String localIp = "192.168.0.1";
        NetworkInterface nif;

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()) {

                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                if (networkInterface.isUp()) {
                        while(inetAddresses.hasMoreElements()) {

                        InetAddress ia = inetAddresses.nextElement();
                        if (ia.isSiteLocalAddress()) {
                            localIp = ia + "";
                            nif = networkInterface;
                        }
                    }
                }
            }
        } catch (Exception e) {}

        if (localIp.contains("/")) {
            localIp = localIp.replace("/", "");
        }
        // return nif and localip somehow
        return localIp;
    }
}
