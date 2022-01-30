package example;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;

public class AppTest {

    public static void main(String[] args) throws UnknownHostException, SocketException {
        byte b = 0x4c;
        System.out.println(String.format("%02x", b));
        System.out.println(String.format("%02x", 76));
    }
}
