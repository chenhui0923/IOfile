package com.example.demo.Iostart.ios1;

import java.io.*;

/**
 * 异常处理写法
 */

public class TestFile4 {
    public static void main(String[] args) {
        File file1 = new File("F:\\学习\\IO学习\\log.txt");
        File file2 = new File("F:\\学习\\IO学习\\IO.xlsx");


        try (InputStream fis = new FileInputStream(file1);
             OutputStream fos = new FileOutputStream(file2,false);){


            byte [] buf  = new byte[1024] ;//定义一个中转站,就是一个字节数组
            int len = fis.read(buf); //读文件的内容放到字节数组中，返回读取的字节数
            while (len!=-1){//n=-1读到了文件的末尾
                //将字节数组的内容写入文件
                fos.write(buf,0,len);
                //再读内容到一个字节数组中
                len = fis.read(buf);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
