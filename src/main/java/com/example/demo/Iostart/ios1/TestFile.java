package com.example.demo.Iostart.ios1;

import java.io.File;
import java.io.IOException;

/**
 * 实现对文件夹的创建和删除
 *
 * File不能文件进行操作，内容操作要使用IO流
 */
public class TestFile {
    public static void main(String[] args) {
        File file = new File("F:\\学习\\IO学习\\abc\\log3.txt");

        //如果文件存在，就删除，不存在就创建
        if(file.exists()){
            file.delete();
        }else {
            //如果上级文件夹不存在，就创建文件夹
            File dir = file.getParentFile();
            if (!dir.exists()){
                dir.mkdirs();//创建文件夹
            }

                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
            }


        }

    }
}
