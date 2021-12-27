package com.example.demo.rxtx;


import gnu.io.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
 
 
 
@Component
@Order(value = 1)
public class ContinueRead extends Thread implements SerialPortEventListener ,ApplicationRunner,Serializable { // SerialPortEventListener
    // 监听器,我的理解是独立开辟一个线程监听串口数据
// 串口通信管理类
    static CommPortIdentifier portId;
 
    /* 有效连接上的端口的枚举 */
 
    static Enumeration<?> portList;
    InputStream inputStream; // 从串口来的输入流
    static OutputStream outputStream;// 向串口输出的流
    static SerialPort serialPort; // 串口的引用
    // 堵塞队列用来存放读到的数据
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();
    //用来存放原始数据
    static LinkedList<Double> linkedList = new LinkedList();
 
    @Override
    /**
     * SerialPort EventListene 的方法,持续监听端口上是否有数据流
     */
    public void serialEvent(SerialPortEvent event) {//
 
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据
                byte[] readBuffer = null;
                int availableBytes = 0;
                try {
                    availableBytes = inputStream.available();
                    while (availableBytes > 0) {
                        readBuffer = ContinueRead.readFromPort(serialPort);
                        String needData = printHexString(readBuffer);
                        System.out.println(new Date() + "真实收到的数据为：-----" + needData);
                        availableBytes = inputStream.available();
                        msgQueue.add(needData);
                    }
                } catch (IOException e) {
                }
            default:
                break;
        }
    }
 
    /**
     * 从串口读取数据
     *
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public static byte[] readFromPort(SerialPort serialPort) {
        InputStream in = null;
        byte[] bytes = {};
        try {
            in = serialPort.getInputStream();
            // 缓冲区大小为一个字节
            byte[] readBuffer = new byte[1];
            int bytesNum = in.read(readBuffer);
            while (bytesNum > 0) {
                bytes = MyUtils.concat(bytes, readBuffer);
                bytesNum = in.read(readBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
 
 
    /**
     * 通过程序打开COM4串口，设置监听器以及相关的参数
     *
     * @return 返回1 表示端口打开成功，返回 0表示端口打开失败
     */
    public int startComPort() {
        // 通过串口通信管理类获得当前连接上的串口列表
        portList = CommPortIdentifier.getPortIdentifiers();
 
        while (portList.hasMoreElements()) {
            // 获取相应串口对象
            portId = (CommPortIdentifier) portList.nextElement();
 
            System.out.println("设备类型：--->" + portId.getPortType());
            System.out.println("设备名称：---->" + portId.getName());
            // 判断端口类型是否为串口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // 判断如果COM4串口存在，就打开该串口
                if ("COM3".equals(portId.getName())) {
                    try {
                        // 打开串口名字为COM_4(名字任意),延迟为1000毫秒
                        serialPort = (SerialPort) portId.open(portId.getName(), 1000);
 
                    } catch (PortInUseException e) {
                        System.out.println("打开端口失败!");
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置当前串口的输入输出流
                    try {
                        inputStream = serialPort.getInputStream();
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 给当前串口添加一个监听器
                    try {
                        serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置监听器生效，即：当有数据时通知
                    serialPort.notifyOnDataAvailable(true);
 
                    // 设置串口的一些读写参数
                    try {
                        // 比特率、数据位、停止位、奇偶校验位
                        serialPort.setSerialPortParams(9600,
                                SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    return 1;
                }
            }
        }
        return 0;
    }
 
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println("--------------任务处理线程运行了--------------");
            while (true) {
                // 如果堵塞队列中存在数据就将其输出
                if (msgQueue.size() > 0) {
                    String vo = msgQueue.peek();
                    String vos[] = vo.split("  ", -1);
                    getData(vos);
                    sendOrder();
                    msgQueue.take();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    /**
     * @Description: 发送获取数据指令
     * @Param:
     * @return:
     * @Author: LiangZF
     * @Date: 2019/1/3
     */
    public void sendOrder() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 1;
        if (i == 1) {
            // 启动线程来处理收到的数据
            try {
                byte[] b = new byte[]{0x01, 0x03, 0x00, 0x00, 0x00, 0x0E, (byte) 0xC4, 0x0E};
                System.out.println("发送的数据:" + b);
                System.out.println("发出字节数：" + b.length);
                outputStream.write(b);
                outputStream.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                serialPort.close();
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
    /**
     * @Description:通过数组解析检测数据
     * @Param: [vo]
     * @return: void
     * @Author: LiangZF
     * @Date: 2019/1/4
     */
    public void getData(String[] vos) {
        // 数组不为空
        if (vos != null || vos.length != 0) {
            // 流速数据
            double wind_direction = getNum(vos[4], vos[5]);
            linkedList.add(wind_direction);
            System.out.println("linkedList = " + linkedList);
            System.out.println(wind_direction);
        }
    }
 
    // 16转10计算
    public double getNum(String num1, String num2) {
        BigInteger bigint=new BigInteger(num1+num2, 16);
        int numb=bigint.intValue();
        return numb/100.00;
    }
 
 
    // 字节数组转字符串
    private String printHexString(byte[] b) {
 
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sbf.append(hex.toUpperCase() + "  ");
        }
        return sbf.toString().trim();
    }
 
 
 
 
 
 
 
 
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ContinueRead cRead = new ContinueRead();
        System.out.println("asdasd");
        int i = cRead.startComPort();
        if (i == 1) {
            // 启动线程来处理收到的数据
            cRead.start();
            try {
                //根据提供的文档给出的发送命令，发送16进制数据给仪器
                byte[] b = new byte[]{0x01, 0x03, 0x00, 0x00, 0x00, 0x0E, (byte) 0xC4, 0x0E};
                System.out.println("发送的数据:" + b);
                System.out.println("发出字节数：" + b.length);
                outputStream.write(b);
                outputStream.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return;
        }
    }
}