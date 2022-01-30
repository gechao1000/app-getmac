package com.example;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Objects;

public class App1 {

    public static void main(String[] args) throws SocketException {
        // cat /sys/class/net/*/address
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();


        for (NetworkInterface it : Collections.list(interfaces)) {
//            System.out.println(it);
            String name = it.getName();
            System.out.println("Name: " + name);
            System.out.println("DisplayName: " + it.getDisplayName());
            System.out.println("Virtual: " + it.isVirtual());
            System.out.println("isUp: " + it.isUp());  // exclude docker0
            System.out.println("isLoopback: " + it.isLoopback());  // exclude loop

            boolean bindIP = false;
            ArrayList<InetAddress> addresses = Collections.list(it.getInetAddresses());
            System.out.println("InetAddress: " + addresses.size());
            for (InetAddress addr : addresses) {
                if (addr instanceof Inet4Address) {
                    System.out.println(addr.getHostAddress());
                    bindIP = true;
                }
            }


            if (it.isLoopback() || !it.isUp() || Objects.equals("docker0", name) || name.startsWith("vbox") || !bindIP) {
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

            System.out.println("--------------------------------------------------");
        }
    }
}
