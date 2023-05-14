package com.kpi.lab4.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            findSum(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void findSum(DataInputStream in, DataOutputStream out) throws IOException {
        int sum = 0;
        int n = in.readInt();
        int m = in.readInt();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = in.readInt();
                sum += matrix[i][j];
            }
        }
        out.writeInt(sum);
    }
}
