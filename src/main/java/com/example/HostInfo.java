package com.example;

import java.util.Arrays;

public class HostInfo {

    private String ip;

    private byte[] mac;

    private String displayName;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public byte[] getMac() {
        return mac;
    }

    public void setMac(byte[] mac) {
        this.mac = mac;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMacAccess() {
        StringBuilder ether = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i > 0) {
                ether.append(":");
            }
            ether.append(String.format("%02x", mac[i]));
        }
        return ether.toString();
    }

    @Override
    public String toString() {
        return "HostInfo{" +
                "ip='" + ip + '\'' +
                ",\tmac='" + getMacAccess() + '\'' +
                ",\tdisplayName='" + displayName + '\'' +
                '}';
    }
}
