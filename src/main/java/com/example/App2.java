package com.example;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Objects;

public class App2 {

    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        final LinkedHashSet<InetAddress> ipSet = new LinkedHashSet<>();
        for (NetworkInterface it : Collections.list(interfaces)) {
            for (InetAddress addr : Collections.list(it.getInetAddresses())) {
                if (addr != null && !addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                    ipSet.add(addr);
                }
            }
        }

        System.out.println(ipSet);

        for (InetAddress addr : ipSet) {
            System.out.println("--------------------------------------------------");
            System.out.println(addr.getHostAddress());
//            System.out.println("isSiteLocalAddress: " + addr.isSiteLocalAddress()); // useless

            NetworkInterface it = NetworkInterface.getByInetAddress(addr);
            String name = it.getName();
            System.out.println("Name: " + name);
            System.out.println("DisplayName: " + it.getDisplayName());
            System.out.println("Virtual: " + it.isVirtual());
            System.out.println("isUp: " + it.isUp());  // exclude docker0
            System.out.println("isLoopback: " + it.isLoopback());  // exclude loop
            if (it.isLoopback() || !it.isUp() || Objects.equals("docker0", name)) {
                System.out.println("Pass...");
            }


            byte[] mac = it.getHardwareAddress();
            System.out.println("Has Mac: " + (mac != null));
            if (mac != null) {
                StringBuilder ether = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    if (i > 0) {
                        ether.append(":");
                    }
                    ether.append(String.format("%02x", mac[i]));
                }
                System.out.println("HardwareAddress: " + ether);
            }
        }
    }
}
