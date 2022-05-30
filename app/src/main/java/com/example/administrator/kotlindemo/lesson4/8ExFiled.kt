package com.example.administrator.kotlindemo.lesson4

/**
 * 疑问点：拓展函数 拿不到 filed 怎么重写 set 方法？
 *
 * 拓展函数和拓展属性
 * 1.替代java里的工具类
 * 2.拓展函数和运算符重载
 * 3.Java调用拓展成员类似调用静态方法
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    println("abc".multiply(16))
    println("abc" * 16)
    println("abc".a)
    //"abc".a = "xx1"
    //println("abc".a)
}
//拓展函数
fun String.multiply(length:Int):String{
    val stringBuilder = StringBuffer()
    for (i in 0 until length) {
        stringBuilder.append(this)
    }
    return stringBuilder.toString()
}
//运算符重载
operator fun String.times(int:Int):String{
    val stringBuilder = StringBuffer()
    for (i in 0 until int) {
        stringBuilder.append(this)
    }
    return stringBuilder.toString()
}
var String.a:String
    get() = "ab"
    set(value) {
    //下边错误，由于扩展没有实际的将成员插入类中，因此对扩展属性来说幕后字段是无效的 这就是为什么扩展属性不能有初始化器
    //field = value
    }

var t:Int=1
    get() = 1
    set(value) {field=value
    }
//下边错误，扩展属性不能有初始化器
//val String.s="1"
