package com.example.demo.serTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

            try {
                ServerSocket serverSocket = new ServerSocket(4001);
                Socket socket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = bufferedReader.readLine();
                System.out.println(line);
                System.out.println("received frome client:" + line);
                //创建PrintWriter，用于发送数据
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println("this data is from server");
                pw.flush();
                //关闭资源
                pw.close();
                bufferedReader.close();
                socket.close();
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
