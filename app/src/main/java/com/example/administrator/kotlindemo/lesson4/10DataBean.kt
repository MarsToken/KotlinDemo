package com.example.administrator.kotlindemo.lesson4

/**
 * 数据类，关键字 data 修饰
 * 1.重写了成员变量，hashcode，equal，toString
 * 2.data 修饰的数据类为public final class形式！没有无参构造
 *  为了解决 除去final，添加无参构造，有两个插件：allopen，noArg
 *  步骤
 *  在根目录gradle下
 *  1.classpath 'org.jetbrains.kotlin-allopen:$kotlin_version
 *  classpath 'org.jetbrains.kotlin-noarg:$kotlin_version
 *  在app module gradle下
 *  2.apply plugin:'kotlin-allopen'
 *  apply plugin:'kotlin-noarg'
 *  3.noArg{
 *      annotation("xx.Poko")
 *  }
 *  但是无参构造只能通过反射调用！！！
 *
 * 3.数据类还可以有第二种写法：
 *  val (id, name) = china
 * 注意：
 *  Component 并不是 data class 专利，我们也可以重写操作符
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    val china = Country(0, "中国")
    println(china)
    println(china.name)

    val (id, name) = china
    println(name)
    //比如这个
    for ((index, value) in args.withIndex()) {
        println("index=$index,value=$value")
    }
    //重写操作符
    val (a, b, c, d) = ComponentX()
    println("$a$b$c$d")
}

class China:Country(1,"中国")
//除去final ，添加无参构造
@Poko
data class Country(val id:Int,val name:String)
//重写操作符
class ComponentX{
    operator fun component1(): String {
        return "hello,"
    }
    operator fun component2():Int{
        return 1
    }
    operator fun component3():Int{
        return 1
    }
    operator fun component4():Int{
        return 0
    }

}