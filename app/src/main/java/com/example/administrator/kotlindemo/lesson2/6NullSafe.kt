package com.example.administrator.kotlindemo.lesson2

/**
 * Created by Administrator on 2021/6/15.
 */
fun main(args: Array<String>) {
    val name = getName() ?: return//?: 如果为空直接返回
    println(name?.length)

    val gender:String? = null
    println(gender?.length)//?. 如果为null则不继续执行
    println(gender!!.length)//!!.与?相反，他表示这个类型一定不为null，就算是null也继续走下去，后果是报空指针异常


}

fun getName(): String? {//返回值可为空
    return "123"
}