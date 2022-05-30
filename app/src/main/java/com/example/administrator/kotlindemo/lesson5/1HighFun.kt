package com.example.administrator.kotlindemo.lesson5

/**
 * 高阶函数的基本概念：
 * 传入或者返回函数的函数
 * 函数引用：
 *  ::println
 * 带有Receiver的引用 pdfPrinter::println
 * 备注
 * 1.包级函数引用方式：
 *  ::函数名
 * 2.成员方法引用方式：
 *  对应的类名::函数名
 * 3.实际上 对应的类名::函数名（this，其他参数） 会持有一个自身的引用，所以无法被
 *  args.forEach(Printer::print)
 *  调用！
 *  所有涉及到对象里的函数（方法）引用的无法通过::调用具体函数
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    //(Any)->Unit
    val helloWorld = Hello::world

    args.forEach(::println)
    args.forEach(::hi)
    args.filter(String::isNotEmpty)

    //错误，无法被调用，因为上述 原因3
    //args.forEach(Printer::print)
    //应该这样
    val printer = object : Printer() {
        override fun test(type: Int) {
        }
    }
    invokeFun(printer::test)
    //printer::test
    args.forEach(printer::print)
    //或者lambda表达式形式
    args.forEach { printer.print(it) }
    args.forEach { Printer::print }

}
abstract class Printer {
    fun print(any: Any) {
        kotlin.io.print(any)
    }

    abstract fun test(type:Int)

    fun test(){}
}
class Hello{
    fun world(action: (Any)->Unit) {
        println("hello,world")
    }
}
fun hi(str:String){
    println("hi,world,$str")
}

private  fun invokeFun(invoke:(Int)->Unit){
    invoke
}