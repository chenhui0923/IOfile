package com.example.demo.Iostart.ios5;


import java.io.*;
import java.util.Date;

/**
 *
 * 希望将各种基本数据类型方便写入到文件中并方便的读取出来
 *
 */
public class TestObjectStream {
    static File file= new File("F://学习//IO学习//ios5//rember.txt");
    public static void main(String[] args) {
        try {
            write();
            read();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void read() throws IOException, ClassNotFoundException {
       BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        ObjectInputStream dis = new ObjectInputStream(bis);
        //打印读入输出的数据,要跟write一一对应
        System.out.println(dis.readInt());
        System.out.println(dis.readDouble());
        System.out.println(dis.readChar());
        System.out.println(dis.readUTF());
        System.out.println(dis.readBoolean());
        Date date = (Date) dis.readObject();
        System.out.println(date);
        Student stu =(Student)dis.readObject();
        System.out.println(stu);
        //关闭
        dis.close();
    }

    public static void write()throws IOException {

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        ObjectOutputStream dos = new ObjectOutputStream(bos);
        //使用输出六哦写基本数据类型和字符串
        dos.writeInt(10);
        dos.writeDouble(3.14);
        dos.writeChar('A');
        dos.writeUTF("IOS学习");
        dos.writeBoolean(true);
        dos.writeObject(new Date());
        Student stu =new Student(1,"zhagnsan",23,98.0);
        stu.setClazz(new Clazz(1,"java01"));
        dos.writeObject(stu);
        //关闭流
        dos.close();

    }
}
