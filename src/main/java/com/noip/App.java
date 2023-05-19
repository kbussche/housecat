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
        System.out.println( "Hello World!" );

        discover();
    }

    public static void discover()
    {
        long initialT = System.currentTimeMillis();

        try {
            checkHosts("192.168.1");
            long finalT = System.currentTimeMillis();
            System.out.println("Scan Completed taking " + (finalT - initialT) + " miliseconds approximately!");
        } catch (Exception e) {
            //System.out.println('whoops');
        }
    }

    public static void checkHosts(String subnet) throws Exception{
        int timeout=1000;
        for (int i=1;i<255;i++) {
            String host=subnet + "." + i;
            System.out.println("Checking " + host);
            if (InetAddress.getByName(host).isReachable(timeout)) {
                System.out.println(host + " is reachable");
            }
        }
    }
}
