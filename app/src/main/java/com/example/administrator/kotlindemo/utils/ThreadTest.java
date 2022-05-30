package com.example.administrator.kotlindemo.utils;

/**
 * Created by tellerWang on 2021/6/23.
 */
public class ThreadTest {
    private static String mQueue;
    public static void main(String[] args) {
        enter();
        while (true) {
            if (null != mQueue && mQueue.length() > 0) {
                System.out.println(Thread.currentThread().getName() + ":获取到了数据：" + mQueue);
                mQueue = null;
            }
            //must
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void enter() {

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"：开始下载图片");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "：图片下载完毕");
            mQueue = "大耳朵图图";
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"：开始下载电影");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mQueue = "西游记";
        });
        thread.start();
    }

}
