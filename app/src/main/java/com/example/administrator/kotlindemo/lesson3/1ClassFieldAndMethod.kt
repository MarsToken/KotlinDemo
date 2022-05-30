package com.example.administrator.kotlindemo.lesson3

/**
 * 属性初始化原则：
 * 1.属性初始化尽量在构造方法中
 * 2.如果无法在构造方法中初始化，尝试降级为局部变量
 * 3.var 用lateinit 初始化，val用lazy初始化
 * 4.可空类型谨慎用null直接初始化
 *
 * Created by Administrator on 2021/6/17.
 */
class ClassFieldAndMethod{
    var b = 0
    lateinit var c:String
    lateinit var d: X
    val e:X by lazy {//什么时候用什么时候初始化
        println("lazy init!")
        X()
    }
    //不推荐这样使用哦
    var cc:String?=null
}
class X

fun main(args: Array<String>) {
    var cfam = ClassFieldAndMethod()
    println("cfam.b is ${cfam.b}")
    cfam.c = "xxx"
    println("cfam.c is ${cfam.c}")
    cfam.d=X()
    println("cfam.d is ${cfam.d}")
    println("cfam.e is ${cfam.e}")
    //不推荐这样使用哦
    println(cfam.cc?.length)
    //抛异常
    println(cfam.cc!!.length)

}