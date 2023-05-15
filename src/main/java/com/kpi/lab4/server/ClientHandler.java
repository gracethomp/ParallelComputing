package com.kpi.lab4.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ClientHandler extends Thread{
    private final Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeUTF("Please send your matrix to calculate sum od elements");
            int[][] matrix = readMatrix(inputStream);

            outputStream.writeUTF("Data is received. Write 'start'");
            inputStream.readUTF();
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> findSum(matrix));

            outputStream.writeUTF("Calculation is started, please wait...");
            //int sum = findSum(matrix);
            while (true) {
                inputStream.readUTF();
                if(future.isDone()){
                    outputStream.writeUTF("Calculation was ended. The result is " + future.get());
                    break;
                } else {
                    outputStream.writeUTF("In progress");
                }
            }

            outputStream.close();
            inputStream.close();
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int[][] readMatrix(DataInputStream in) throws IOException {
        int n = in.readInt();
        int m = in.readInt();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = in.readInt();
            }
        }
        return matrix;
    }

    private int findSum(int[][] matrix) {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int sum = 0;
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                sum += ints[j];
            }
        }
        return sum;
    }
}
