package com.example.administrator.kotlindemo.lesson4

/**
 * 方法重载，带默认值，java如何调用不穿参数，用@JvmOverloads
 * Created by Administrator on 2021/6/20.
 */
class OverLoad{
    @JvmOverloads
    fun getX(int: Int = 0) = int
}

fun main(args: Array<String>) {
    val list = ArrayList<Int>()
    list.add(1)
    list.add(2)
    list.add(3)
    list.add(4)
    list.removeAt(1)//java无法避免
    list.remove(1)
}