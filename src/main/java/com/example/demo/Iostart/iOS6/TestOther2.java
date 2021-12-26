package com.example.demo.Iostart.iOS6;

import java.io.*;

public class TestOther2 {
    static File file1 = new File("F://学习//IO学习//ios5//rember.txt");
    static File file2 = new File("F://学习//IO学习//ios5//rember1.txt");

    public static void main(String[] args)throws IOException {
        InputStream is =System.in;//键盘输入
        Reader reader = new InputStreamReader(is);//字节流转成字符流
//        BufferedReader br = new BufferedReader(new FileReader(file1));
        BufferedReader br = new BufferedReader(reader);
        PrintWriter pw = new PrintWriter(file2);
        String str = br.readLine();
        while (!str.equals("bye")){
            pw.println(str);
            str = br.readLine();
        }
        br.close();
        pw.close();
    }
}
