package com.kpi.lab4;

import com.kpi.lab4.client.Client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("localhost", 6666);
        client.sendMatrix(5, 7);
    }
}
