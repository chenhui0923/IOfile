package com.example.demo.rxtx;

import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SerialPortDataHandle extends Thread implements SerialPortEventListener {

    private static final Logger logger = LoggerFactory.getLogger(SerialPortDataHandle.class);

    // 通讯端口管理，控制对通信端口的访问的中心类
    static CommPortIdentifier portManager;
    // 有效连接上的端口的枚举
    static Enumeration<?> portList;
    // 串口输入流引用
    static InputStream inputStream;
    // 串口输出流引用
    static OutputStream outputStream;
    // 串口对象引用
    static SerialPort serialPort;
    // 堵塞队列：用来存放串口发送到服务端的数据
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue();
    // 线程控制标识
    private boolean flag = true;

    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            /*
             *  SerialPortEvent.BI:/*Break interrupt,通讯中断
             *  SerialPortEvent.OE:/*Overrun error，溢位错误
             *  SerialPortEvent.FE:/*Framing error，传帧错误
             *  SerialPortEvent.PE:/*Parity error，校验错误
             *  SerialPortEvent.CD:/*Carrier detect，载波检测
             *  SerialPortEvent.CTS:/*Clear to send，清除发送
             *  SerialPortEvent.DSR:/*Data set ready，数据设备就绪
             *  SerialPortEvent.RI:/*Ring indicator，响铃指示
             *  SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*Output buffer is empty，输出缓冲区清空
             */
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: break;
            // 当有可用数据时读取数据
            case SerialPortEvent.DATA_AVAILABLE:
                // 数据接收缓冲容器
                byte[] readBuffer = new byte[200];
                try {
                    // 存储待接收读取字节数大小
                    int numBytes = 0;
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);
                        if (numBytes > 0) {
                            msgQueue.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .format(new Date()) + " 收到的串口发送数据为："+ new String(readBuffer));
                            // 数据接收缓冲容器清空初始化
                            readBuffer = new byte[200];
                        }
                    }
                } catch (IOException e) {
                    logger.error("IO异常", e);
                }
                break;
        }
    }

    public int init() {
        // 通过串口通信管理类获得当前连接上的端口列表
        //（获取一个枚举对象，该CommPortIdentifier对象包含系统中每个端口的对象集[串口、并口]）
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            // 获取相应串口对象
            portManager = (CommPortIdentifier) portList.nextElement();
            /*
             *  判断端口类型是否为串口
             *  PORT_SERIAL = 1; 【串口】
             *  PORT_PARALLEL = 2; 【并口】
             *  PORT_I2C = 3; 【I2C】
             *  PORT_RS485 = 4; 【RS485】
             *  PORT_RAW = 5; 【RAW】
             */
            if (portManager.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                logger.info("串口设备名称：" + portManager.getName());
                // 判断模拟COM4串口存在，就打开该串口
                if (portManager.getName().equals("COM5")) {
                    logger.info("测试串口设备名称：" + portManager.getName());
                    try {
                        if (serialPort==null) {
                            // 打开串口，设置名字为COM_4(自定义),延迟阻塞时等待3000毫秒（赋值给预设的串口引用）
                            serialPort = (SerialPort)portManager.open("COM5", 3000);
                            logger.info("串口设备COM5已打开");
                        }
                    } catch (PortInUseException e) {
                        logger.error("串口使用异常", e);
                        return 0;
                    }
                    // 在串口引用不为空时进行下述操作
                    if (serialPort!=null) {
                        // 1. 设置串口的输入输出流引用
                        try {
                            inputStream = serialPort.getInputStream();
                            outputStream = serialPort.getOutputStream();
                        } catch (IOException e) {
                            logger.error("串口输入输出IO异常", e);
                            return 0;
                        }
                        // 2. 设置串口监听器
                        try {
                            serialPort.addEventListener(this);
                        } catch (TooManyListenersException e) {
                            logger.error("串口监听器添加异常", e);
                            return 0;
                        }
                        // 设置监听器在有数据时通知生效
                        serialPort.notifyOnDataAvailable(true);

                        // 3. 设置串口相关读写参数
                        try {
                            // 比特率、数据位、停止位、校验位
                            serialPort.setSerialPortParams(9600,
                                    SerialPort.DATABITS_8,
                                    SerialPort.STOPBITS_1,
                                    SerialPort.PARITY_NONE);
                        } catch (UnsupportedCommOperationException e) {
                            logger.error("串口设置操作异常", e);
                            return 0;
                        }
                        return 1;
                    }
                    return 0;
                }
            }
        }
        return 0;
    }

    @Override
    public void run() {
        try {
            logger.info("串口线程已运行");
            while (flag) {
                // 如果堵塞队列中存在数据就将其输出
                if (msgQueue.size() > 0) {
                    // take() 取走BlockingQueue里排在首位的对象
                    // 若BlockingQueue为空，阻断进入等待状态直到Blocking有新的对象被加入为止
                    logger.info(msgQueue.take());
                }
            }
        } catch (InterruptedException e) {
            logger.error("线程执行异常", e);
        }
    }

    public void stopGetDataBySerialPort() {
        this.flag = false;
    }

    public static void main(String[] args) {
        SerialPortDataHandle handle = new SerialPortDataHandle();
        int i = handle.init();
        if (i == 1) {
            // 线程启动
            handle.start();
        }
    }
}
