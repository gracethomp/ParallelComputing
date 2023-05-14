package com.kpi.lab4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(6666)) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            findSum(dataInputStream, dataOutputStream);
            dataOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void findSum(DataInputStream in, DataOutputStream out) throws IOException {
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
