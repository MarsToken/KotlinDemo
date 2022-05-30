package com.example.administrator.kotlindemo.lesson5

import java.nio.charset.Charset

/**
 * 偏函数
 * 传入部分参数得到新的函数，可以忽略参数的顺序
 * 注意与柯里化的不同
    1.定义一个变量
    2.先柯里化一下它,对于固定每次都要传的参数，可以先固定一下它
    3.对于多参数函数，通过先指定它的一些固定的参数，得到的依然是一个函数，这个函数就是原来函数的偏函数
 * Created by tellerWang on 2021/6/21.
 */
fun main() {
    val makeStringToGBK1 = makeStringToGBK("Hello! 小明".toByteArray(charset("GBK")), charset("GBK"))
    println("$makeStringToGBK1")
    val partial1 = "Hello,小米".toByteArray(charset("GBK"))
    val partial2 = makeStringToGBK.partial2(charset("GBK"))
    val result = partial2(partial1)
    println(result)
    //变量名构造
    println(makeStringToGBK.partial2(charset("GBK"))(partial1))
    //函数名构造
    println(::makeString.partial2(charset("GBK"))(partial1))
}

//变量名构造
fun makeString(byteArray: ByteArray, charset: Charset) = String(byteArray, charset)
//函数名构造
val makeStringToGBK = fun(byteArray: ByteArray, charset: Charset) = String(byteArray, charset)
//自定义偏函数
fun <P1, P2, R> Function2<P1, P2, R>.partial2(p2: P2) = fun(p1: P1) = this(p1, p2)
