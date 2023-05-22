package com.noip;

import java.net.InetAddress;

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

        discover(data_share);

        System.out.println("Scan Completed");
        data_share.get().forEach((key, value) -> System.out.println(key + " " + value));
    }

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


}
