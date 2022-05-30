package com.example.administrator.kotlindemo.lesson4

/**
 * 枚举也是类，可以修改构造，添加成员及方法，可以提升代码可读性，也有一定的内存性能开销
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    println(LogLevel.VERBOSE.getTag())
    println(LogLevel2.ASSERT)
    //自 Kotlin 1.1 起，可以使用 enumValues<T>() 与 enumValueOf<T>() 函数以泛型的方式访问枚举类中的常量
    LogLevel.values().map(::println)
    println(LogLevel.valueOf("ERROR"))
    enumValues<LogLevel>().joinToString().forEach(::print)
}
enum class LogLevel(val id:Int){
    VERBOSE(0),DEBUG(1),INFO(2),WARN(3),ERROR(4),ASSERT(5);//唯一强制写分号的地方
    fun getTag():String{
        //name为名字，ordinal 为顺序第几位
        return "$id,$name,$ordinal"
    }

    override fun toString(): String {
        return "$name,$ordinal"
    }
}

class LogLevel2 protected constructor(){
    companion object {
        val VERBOSE = LogLevel2()
        val DEBUG = LogLevel2()
        val INFO = LogLevel2()
        val WARN = LogLevel2()
        val ERROR = LogLevel2()
        val ASSERT = LogLevel2()
    }
}
//ex
interface IState{
    fun getState():Int
}
//枚举可实现接口，也可重写方法
enum class ProtocolState : IState {
    WAITING {
        override fun signal(): ProtocolState = TAKING
    },
    TAKING {
        override fun signal(): ProtocolState = WAITING
    };

    abstract fun signal(): ProtocolState

    override fun getState(): Int {
        return 1
    }
}
