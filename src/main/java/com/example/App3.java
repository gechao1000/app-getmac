package com.example;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;

public class App3 {

    public static void main(String[] args) throws SocketException {
        final List<HostInfo> list = new LinkedList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        // check ip
        for (NetworkInterface it : Collections.list(interfaces)) {
            HostInfo item = new HostInfo();
            boolean bindIP = false;
            for (InetAddress addr : Collections.list(it.getInetAddresses())) {
                if (addr != null && !addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                    bindIP = true;
                    item.setIp(addr.getHostAddress());
                    break;
                }
            }
            if (!bindIP) {
                continue;
            }
            System.out.println("--------------------------------------------------");
            System.out.println(item.getIp());

            // check property
            String name = it.getName();
            System.out.println("Name: " + name);
            System.out.println("DisplayName: " + it.getDisplayName());
            System.out.println("Virtual: " + it.isVirtual());
            System.out.println("isUp: " + it.isUp());  // exclude docker0
            System.out.println("isLoopback: " + it.isLoopback());  // exclude loop
            item.setDisplayName(it.getDisplayName());
            if (it.isLoopback() || !it.isUp() || Objects.equals("docker0", name)) {
                continue;
            }

            // check mac address
            byte[] mac = it.getHardwareAddress();
            if (mac == null) {
                continue;
            }
            item.setMac(mac);
            list.add(item);
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        list.forEach(System.out::println);


        HostInfo rt = null;
        if (list.size() == 1) {
            rt = list.get(0);
        } else {
            System.out.println("+++++++++++++++++++++ Not Virtual ++++++++++++++++");
            List<HostInfo> listNotVirtual = list.stream()
                    .filter(it -> !isVirtualMac(it.getMac()))
                    .collect(Collectors.toList());
            listNotVirtual.forEach(System.out::println);

            if (listNotVirtual.size() == 1) {
                rt = listNotVirtual.get(0);
            } else {
                System.out.println("+++++++++++++++++++++ Not Adapter ++++++++++++++++");
                List<HostInfo> listNotAdapter = listNotVirtual.stream()
                        .filter(it -> !isAdapter(it.getDisplayName()))
                        .collect(Collectors.toList());
                listNotAdapter.forEach(System.out::println);
                if (listNotAdapter.size() > 0) {
                    rt = listNotAdapter.get(0);
                }
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.println(rt);
    }

    public static boolean isAdapter(String displayName) {
        if (displayName == null || displayName.length() == 0) {
            return false;
        }
        return displayName.toLowerCase().contains("adapter");
    }

    public static final byte[][] VIRTUAL_MAC_PREFIX = {
        // hyper-v
        {0x00, 0x15, 0x5d},
        // VirtualBox
        {0x0a, 0x00, 0x27},
        {0x08, 0x00, 0x27},
        // VMware
        {0x00, 0x05, 0x69},
        {0x00, 0x0c, 0x29},
        {0x00, 0x50, 0x56},
        {0x00, 0x1c, 0x14},
    };

    public static boolean isVirtualMac(byte[] mac) {
        if (mac == null || mac.length < 3) {
            return true;
        }
        for (byte[] pre : VIRTUAL_MAC_PREFIX) {
            if (pre[0] == mac[0] && pre[1] == mac[1] && pre[2] == mac[2]) {
                return true;
            }
        }

//         docker
//        {0x02, 0x42, 0x07},
//        {0x02, 0x42, 0x39},
//        {0x02, 0x42, 0x40},
        if (mac[0] == 0x02 && mac[1] == 0x42) {
            return true;
        }

        return false;
    }
}
