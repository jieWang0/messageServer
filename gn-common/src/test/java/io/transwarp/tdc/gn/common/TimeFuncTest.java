package io.transwarp.tdc.gn.common;

import org.junit.Test;

public class TimeFuncTest {

    @Test
    public void testNano() {
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000000; i++) {
            System.nanoTime();
        }

        long totalTime = System.nanoTime() - startTime;

        System.out.println("Total Time: " + totalTime);
        System.out.println("Avg Time: " + ((double)totalTime) / 1000000);
    }

    @Test
    public void testMillis() {
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000000; i++) {
            System.currentTimeMillis();
        }

        long totalTime = System.nanoTime() - startTime;

        System.out.println("Total Time: " + totalTime);
        System.out.println("Avg Time: " + ((double)totalTime) / 1000000);
    }
}
