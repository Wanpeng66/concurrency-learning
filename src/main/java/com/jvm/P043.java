package com.jvm;

/**
 * @author: wp
 * @Title: P043
 * @Description: 手动模拟young GC
 * @date 2020/5/3 15:07
 */
public class P043 {
    public static void main( String[] args ) {
        byte[] arr1 = new byte[1024*1024];
        arr1 = new byte[1024*1024];
        arr1 = new byte[1024*1024];
        arr1 = null;
        byte[] arr2 = new byte[2 * 1024 * 1024];

    }
}
