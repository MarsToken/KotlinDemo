package com.example.administrator.kotlindemo.lesson6;

/**
 * Created by Administrator on 2021/6/23.
 */
public class ThreadTest {
    private static final int BLOCK = -1;
    private static final int AWAIT = 0;
    private static int mStatus = BLOCK;
    public static void main(String[] args) {
        prepare();
        loop();
    }

    private static void prepare() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mStatus = AWAIT;
            }
        });
        thread.start();
    }

    private static void loop() {
        while (true) {
            if (mStatus != BLOCK) {
                System.out.println(Thread.currentThread().getName() + " mStatus=" + mStatus);
                mStatus = BLOCK;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
