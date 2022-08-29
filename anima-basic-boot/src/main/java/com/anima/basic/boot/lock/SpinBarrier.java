package com.anima.basic.boot.lock;

import java.util.function.Supplier;

/**
 * 自旋栅栏函数
 *
 * @author hww
 */
public class SpinBarrier {

    // 自选周期
    private static final Long spinTime = 100L;

    public static boolean spin(Supplier<Boolean> supplier, long timeout) {
        long startTime = System.currentTimeMillis();
        try {
            while ((!Thread.currentThread().isInterrupted()) || (!timedOut(startTime, timeout))) {
                if (supplier.get()) {
                    return true;
                }
                Thread.sleep(spinTime);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    private static boolean timedOut(long startTime, long timeout) {
        return (startTime + timeout) < System.currentTimeMillis();
    }

}
