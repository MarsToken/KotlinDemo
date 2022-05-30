package com.example.administrator.kotlindemo.lesson5

import java.io.OutputStream

/**
 * 柯里化
 * 多远函数变成一元函数调用链
 * 柯里化 是把接受多个参数的函数变换成接受一个单一参数（最初函数的第一个参数）的函数，并且返回接受余下的参数而且返回结果的新函数的技术
 * Created by tellerWang on 2021/6/21.
 */
fun main() {
    log("Currying",System.out,"Hello.")
    //log("Currying")(System.out)("Hello,again!")
    ::log.currying()("Currying")(System.out)("Hello,again and again!")
}
fun log(tag:String,target:OutputStream,info:Any){
    target.write("[$tag],$info\n".toByteArray())
}

//0.通用模板
fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.currying() = fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3)
//注意与偏函数区别！！偏函数对参数顺序没要求，可以任意定制，所以 partical2(x:X)，而柯里化要严格顺序要求，所以单独自定义一个方法 currying
//fun <P1, P2, R> Function2<P1, P2, R>.partial2(p2: P2) = fun(p1: P1) = this(p1, p2)

//1.可以不要返回值，但是无法柯里化
//fun log(tag: String)= fun(target: OutputStream) = fun(info: Any) { target.write("[$tag],$info\n".toByteArray()) }
//2.缩版
//fun log(tag: String) = fun(target: OutputStream) = fun(info: Any) = target.write("[$tag],$info\n".toByteArray())
//3.全版
//fun log(tag: String): (OutputStream) -> ((Any) -> Unit) {
//    return fun(target: OutputStream): (Any) -> Unit {
//        return fun(info: Any): Unit {
//            target.write("[$tag],$info\n".toByteArray())
//        }
//    }
//}
//4.缩版到简版的过程！
//fun log(tag: String): Unit {
//    fun(target: OutputStream):Unit{
//        fun(info: Any):Unit{
//            target.write("[$tag],$info\n".toByteArray())
//        }
//    }
//}
