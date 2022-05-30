package com.example.administrator.kotlindemo.lesson3

import java.lang.Exception

/**
 * Created by tellerWang on 2021/6/21.
 */
fun main(args: Array<String>) {
    getResult(0)
}
fun getResult(x: Int): Int =
        try {
            10 / x
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        } finally {//一定会走，无返回值，调用处为代码最后
            println("finally")

        }