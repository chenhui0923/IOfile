package com.example.demo.Iostart.ios1;


import java.io.File;
import java.util.Date;

/**
 *获取文件或者文件夹的属性
 */
public class TestFile2 {
    public static void main(String[] args) {
        File file = new File("F:\\学习\\IO学习");
        System.out.println(file.getName());//文件名
        System.out.println(file.length());//文件长度
        System.out.println(file.exists());//文件是否存在

        System.out.println(file.canRead());//是否可读
        System.out.println(file.canWrite());//是否可写
        System.out.println(file.canExecute());//是否可执行

        System.out.println(file.isFile());//是文件吗

        System.out.println(file.isDirectory());//是文件夹吗

        File[] files = file.listFiles();
        System.out.println(file.length());

        for (File f :files){
            System.out.println(f.getName()+"\t"+ new Date(f.lastModified()).toLocaleString() );//lastModified()最近修改时间
            if (f.isDirectory()){
                System.out.println("<DIR>");
            }else {
                System.out.println(""+f.length());
            }
        }
    }
}
