package com.noip;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        try {
            checkHosts("192.168.1", ds);
            
        } catch (Exception e) {
            //System.out.println("whoops: " + e.toString());
        }
    }

    public static void checkHosts(String subnet, DataShare ds) throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(50);

        int timeout=1000;

        for (int i=1;i<255;i++) {
            String host=subnet + "." + i;
            Discover d = new Discover(host, ds);
            executor.execute(d);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("Finished all threads");
    }
}
