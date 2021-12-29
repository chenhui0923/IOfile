package com.example.demo.Iostart.iOS6;

import java.io.*;

/**
 * 装饰模式
 */
public class TestOther3  {
    public static void main(String[] args) throws IOException{


        //字节流
        //节点流：FileInputStream、 FileOutputStream
        //处理流:BufferedInputStream 、 DataInputStream 、 PrintStream

        ByteArrayOutputStream bais; //数据源是一个字节数组
        ByteArrayInputStream baos;  //目的地是一个字节数组
        CharArrayReader bar;//节点流
        CharArrayWriter baw;//节点流


        //适配器模式InputStreamReader  OutputstreamWriter
        byte[] bytes = new byte[1023];
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));
        DataInputStream dis2 = new DataInputStream(new BufferedInputStream(new FileInputStream("F://学习//IO学习//ios5//rember1.txt")));

        //节点流的父亲：Inputstream
        //处理流的父亲：FilterInputStream exits InputStream
    }
}
