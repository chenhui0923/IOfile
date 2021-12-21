package com.example.demo.Iostart.ios4;


import java.io.*;

/**
 * 1、提高速度   BufferedInputStream
 * 2、简化操作 BufferedReader
 */
public class TestCopy1 {
    public static void main(String[] args)throws IOException {
        //创建输入流和输出流
        File file;
        BufferedReader br = new BufferedReader(new FileReader("F:\\学习\\IO学习\\log.txt"));
        BufferedWriter bw =new BufferedWriter(new FileWriter("F:\\学习\\IO学习\\log2.txt",true));
        //使用输入流和输出流复制文件（按行读写）

        String str = br.readLine();//读一行
        while (str!=null){

            bw.write(str);//写一行
            bw.newLine();//换行
            str = br.readLine();//读一行
        }

        //关闭输入流和输出流
        br.close();
        bw.close();
    }
}
