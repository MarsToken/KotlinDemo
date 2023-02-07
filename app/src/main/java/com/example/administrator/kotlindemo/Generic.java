package com.example.administrator.kotlindemo;



/**
 * Created by tellerWang on 2021/6/30.
 */
public class Generic {
    public static void main(String[] args) {
    }

    void read(Source<String> source) {
        // 实例的引用是极为安全的——没有消费者-方法可以写入T。但是 Java 并不知道这一点，并且仍然禁止这样操作
        //Source<Object> objectSource = source;// ！！！在 Java 中不允许，写入需要借助 ？extends Object
        Source<? extends Object> objectSource = source;
        //objectSource.nextT();
    }

    void write(Source<String> source) {

    }

    interface Source<T> {
        T nextT();
    }
}
