package com.example.administrator.kotlindemo.lesson4

/**
 * 伴生对象，相当于类方法或静态方法
 * 每个类可以对于一个伴生对象
 * 伴生对象的成员全局独一份
 * 类似Java的静态变量
 *
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    println("${Latitude.ofDouble(1.0).value},tag is ${Latitude.TAG}")
}

class Latitude private constructor(val value: Double){
    constructor(int:Int):this(1.0){

    }

    companion object {


        fun ofDouble(double: Double): Latitude {
            return Latitude(double)
        }
        //copy
        @JvmStatic//java 可以直接调用
        fun ofLatitude(latitude: Latitude): Latitude {
            return Latitude(latitude.value)
        }

        @JvmField//java 可以直接调用
        val TAG: String = "Latitude"

        fun test(): Int = 1

    }
}
class Test(val str:String){
    var t:String
    get()="defualt"
    set(value) {}
    constructor(int:Int):this("1"){

    }
    constructor():this(1)

}