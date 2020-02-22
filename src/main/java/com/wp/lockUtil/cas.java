package com.wp.lockUtil;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author: wp
 * @Title: cas
 * @Description: TODO
 * @date 2020/2/20 19:42
 */
public class cas {
    public static void main( String[] args ) {
        cas c = new cas();
        AtomicStampedReference<cas> ins = new AtomicStampedReference<>( c,0);
        AtomicReference<cas> ar = new AtomicReference<>( c );

    }
}
