package com.example.administrator.kotlindemo.lesson4

/**
 * 父类或接口重写同样的方法时怎么解决冲突？（签名一致，函数名和参数列表，返回值都得一样！）
 * Created by Administrator on 2021/6/20.
 */
abstract class A{
    open fun x() = 5
}
interface B{
    fun x() = 1
}
interface C{
    fun x() = 2
}
//自研
class E:A(),B,C{
    override fun x() = 10
}
//具体解决方案
class D(val y:Int):A(),B,C{
    override fun x(): Int {
        if (y > 0) {
            return y
        }else if (y > -100) {
            return super<B>.x()
        }else if (y > -200) {
            return super<C>.x()
        }else{
            return super<A>.x()
        }
    }
}

fun main(args: Array<String>) {
    println(D(10).x())
    println(D(-10).x())
    println(D(-120).x())
    println(D(-1000).x())
}