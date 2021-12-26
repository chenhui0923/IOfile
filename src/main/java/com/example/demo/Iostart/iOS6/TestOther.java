package com.example.demo.Iostart.iOS6;

import java.io.*;


/**
 * 打印流  只有输出流   没有输入流  都是处理流
 */
public class TestOther {
    public static void main(String[] args)throws IOException {
        File file= new File("F://学习//IO学习//ios5//rember.txt");
        PrintStream ps  =new PrintStream(file);//字节流  输出流
        PrintWriter pw;//字符流  输出流

        ps.println("hello world");
        ps.println('A');
        ps.println(3.14);
    }
}
