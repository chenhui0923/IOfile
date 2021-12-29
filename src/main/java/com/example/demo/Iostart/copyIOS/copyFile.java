package com.example.demo.Iostart.copyIOS;

import java.io.*;

public class copyFile {
    public static void main(String[] args) {
        copyTest("F:\\学习\\IO学习\\ios5\\copy.txt","F:\\学习\\IO学习\\ios6\\copy.txt");
    }

    /**
     *
     * @param sourceFile 源文件
     * @param destFile 目的文件
     */
    public static void copyTest(String sourceFile,String destFile){
        File file1=new File(sourceFile);
        File file2 = new File(destFile);
        try {
            BufferedInputStream bis =new BufferedInputStream(new FileInputStream(file1));
            BufferedOutputStream bos =new BufferedOutputStream(new FileOutputStream(file2));

            byte [] buf  = new byte[1024] ;//定义一个中转站,就是一个字节数组
            int len = bis.read(buf); //读文件的内容放到字节数组中，返回读取的字节数
            while (len!=-1){//n=-1读到了文件的末尾
                //将字节数组的内容写入文件
                bos.write(buf,0,len);
                //再读内容到一个字节数组中
                len = bis.read(buf);

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
