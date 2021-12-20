package com.example.demo.Iostart.ios1;

import java.io.*;

/**
 * 文件复制
 */
public class TestFile3 {
    public static void main(String[] args) throws IOException{
        //创建输入流
        File file1 = new File("F:\\学习\\IO学习\\log.txt");
        File file2 = new File("F:\\学习\\IO学习\\IO.xlsx");
        InputStream fis = new FileInputStream(file1);
        OutputStream fos  = new FileOutputStream(file2,false);//append ：ture追加  false覆盖
        // 合并写法
        //InputStream fis = new FileInputStream("F:\\学习\\IO学习\\log.txt");
        //OutputStream fos  = new FileOutputStream("F:\\学习\\IO学习\\IO.xlsx",false);

        //使用输入流和输出流完成文件复制

        byte [] buf  = new byte[1024] ;//定义一个中转站,就是一个字节数组
        int len = fis.read(buf); //读文件的内容放到字节数组中，返回读取的字节数
            while (len!=-1){//n=-1读到了文件的末尾
                //将字节数组的内容写入文件
                fos.write(buf,0,len);
                //再读内容到一个字节数组中
                len = fis.read(buf);

            }



        //完毕后输入流和输出流
        fis.close();
        fos.close();
    }
}
