package com.example.demo.Iostart.ios4;

import java.io.*;

/**
 * 缓冲字节流可以提高读写速度
 * 关闭高层流，无需关闭底层流
 * 缓冲流为什么可以提高查询速度
 *
 * 如何刷新输出缓存区（将缓存区的最新数据写入文件中）
 *          方法1：bos.close（）;先刷新在关闭流
 *          方法2 ：bos.flush();该方法会强制将缓冲区中的数据一次性写出，不管缓冲区是否已经装满
 *          方法3：缓冲区满，自动刷新
 */
public class TestFile1 {
    public static void main(String[] args) throws IOException{
        //创建输入流
//        File file1 = new File("F:\\学习\\IO学习\\log.txt");
//        File file2 = new File("F:\\学习\\IO学习\\IO.xlsx");
//        InputStream fis = new FileInputStream(file1);
//        OutputStream fos  = new FileOutputStream(file2,false);//append ：ture追加  false覆盖
//
//        BufferedInputStream bis = new BufferedInputStream(fis);
//        BufferedOutputStream bos = new BufferedOutputStream(fos);
        // 合并写法
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F:\\学习\\IO学习\\log.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("F:\\学习\\IO学习\\IO.xlsx"));

       int n;
       n= bis.read();
       while (n!=-1){
           bos.write(n);//写一个字节
           System.out.println(n);
           n= bis.read();//读一个字节

       }



        //完毕后输入流和输出流
        bis.close();
        bos.close();
    }
}
