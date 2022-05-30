package com.example.administrator.kotlindemo.lesson4

/**
 * 代理模式-接口 seniorManager
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    val manager = Manager()
    manager.drive()
    manager.write()
    val driver = SeniorDriver()
    val writer = SeniorWriter()
    val seniorManager = SeniorManager(driver, writer)
    seniorManager.drive()
    seniorManager.write()

}
//对于方法，委托优先级低于 override，优先调用自身里的override方法
//对于override 变量，委托对象的成员只能访问其自身对接口成员实现
//kotlin special way
class SeniorManager(val driver: Driver,val writer: Writer):Driver by driver,Writer by writer
//传统模式
class ClassicSeniorManager(val driver: Driver,val writer: Writer): Manager() {
    override fun drive() {
        super.drive()
        driver.drive()
    }

    override fun write() {
        super.write()
        writer.write()
    }
}
class SeniorDriver:Driver{
    override fun drive() {
        println("senior driver drive!")
    }
}
class SeniorWriter:Writer{
    override fun write() {
        println("senior writer write!")
    }
}
open class Manager:Driver,Writer{
    override fun drive() {
        println("manager drive!")
    }

    override fun write() {
        println("manager write!")
    }
}

interface Driver{
    fun drive()
}
interface Writer{
    fun write()
}