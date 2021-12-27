package com.example.demo.rxtx;

import java.io.*;
import java.math.BigInteger;
 
public class MyUtils {
 
    /**
     * 合并数组
     *
     * @param firstArray  第一个数组
     * @param secondArray 第二个数组
     * @return 合并后的数组
     */
    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            if (firstArray != null)
                return firstArray;
            if (secondArray != null)
                return secondArray;
            return null;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length, secondArray.length);
        return bytes;
    }
 
    public static void main(String[] args)throws IOException {
        InputStream is =System.in;
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        String str = br.readLine();
        BigInteger bigint=new BigInteger(String.valueOf(str), 16);
        int numb=bigint.intValue();
        System.out.println(numb/100.00);

        br.close();


    }
    }
