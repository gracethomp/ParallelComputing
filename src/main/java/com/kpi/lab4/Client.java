package com.kpi.lab4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        sendMatrix();
    }

    private static void sendMatrix() {
        int[][] matrix = generateMatrix(2, 5);
        try(Socket socket = new Socket("localhost", 6666)) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            writeMatrix(dataOutputStream, matrix);
            System.out.println(dataInputStream.readInt());
            dataInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int[][] generateMatrix(int n, int m){
        Random random = new Random();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = random.nextInt(10001);
            }
        }
        return matrix;
    }

    private static void writeMatrix(DataOutputStream out, int[][] matrix) throws IOException {
        out.writeInt(matrix.length);
        out.writeInt(matrix[0].length);
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                out.writeInt(anInt);
            }
        }
    }
}
