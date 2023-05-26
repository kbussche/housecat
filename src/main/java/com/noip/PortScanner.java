package com.noip;

import java.util.*;
import java.net.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class PortScanner {

    public void runPortScan(String ip, int nbrPortMaxToScan) throws IOException {
        ConcurrentLinkedQueue openPorts = new ConcurrentLinkedQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(350);
        AtomicInteger port = new AtomicInteger(0);
        while (port.get() < nbrPortMaxToScan) {
                final int currentPort = port.getAndIncrement();
                executorService.submit(() -> {
                        try {
                                Socket socket = new Socket();
                                socket.connect(new InetSocketAddress(ip, currentPort), 10000);
                                socket.close();
                                openPorts.add(currentPort);
                                //System.out.println(ip + " ,port open: " + currentPort);
                        } catch (IOException e) {
                                //System.err.println(e);
                        }
                });
        }
        executorService.shutdown();
        try {
                executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
                throw new RuntimeException(e);
        }
        List openPortList = new ArrayList<>();
        //System.out.println("openPortsQueue: " + openPorts.size());
        while (!openPorts.isEmpty()) {
                openPortList.add(openPorts.poll());
        }
        openPortList.forEach(p -> System.out.println("-- port " + p + " is open"));
    }
}
