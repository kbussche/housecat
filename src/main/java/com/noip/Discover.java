package com.noip;

import java.net.InetAddress;
import java.net.NetworkInterface;

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
        if (check(check_ip)) {
            this.ds.set(check_ip, "good");
        }
    }

    public static boolean check(String ip)
    {
        int timeout = 1000;
        try {
            if (InetAddress.getByName(ip).isReachable(timeout)) {
                //System.out.println(ip + " is reachable");
                InetAddress localIP = InetAddress.getByName(ip);
                NetworkInterface ni = NetworkInterface.getByInetAddress(localIP);
                byte[] macAddress = ni.getHardwareAddress();
                String mac = new String(macAddress);
                //System.out.println(ip + " " + mac);
                return true;
            }

        } catch (Exception e) {
            System.out.println("whoops " + e.toString());
            return false;
        }

        return false;
    }
}