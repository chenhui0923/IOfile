package com.example.demo.Iostart.ios2;

import java.io.*;

/**
 * 使用字节流可以读写任意类型的文件
 * 使用字符流只可以读写文本文件
 */

public class TestCopy {
    public static void main(String[] args) throws IOException {
        Reader re =new FileReader("F:\\学习\\IO学习\\log.txt");

        Writer fw =new FileWriter("F:\\学习\\IO学习\\log2.txt");

        //定义一个字符
//        int n =re.read();
//        while (n!=-1){
//            fw.write(n);//写一个字符到文件
//            System.out.println((char) n);
//            n= re.read();
//        }
        char [] buf  = new char[1024] ;//定义一个中转站,就是一个字节数组
        int len = re.read(buf); //读文件的内容放到字节数组中，返回读取的字节数
        while (len!=-1){//n=-1读到了文件的末尾
            //将字节数组的内容写入文件
            fw.write(buf,0,len);
            //再读内容到一个字节数组中
//            System.out.println(buf);
            System.out.println(new String(buf,0,len));
            len = re.read(buf);

        }
//cs
        re.close();
        fw.close();
    }
}
