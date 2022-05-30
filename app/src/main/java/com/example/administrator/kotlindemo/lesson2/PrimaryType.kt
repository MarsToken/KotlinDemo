package com.example.administrator.kotlindemo.lesson2

/**
 * Created by Administrator on 2021/6/15.
 */
val anInt:Int=5
val aLong: Long = anInt.toLong()
val string: String = "HelloWorld"
val fromChars: String = String(charArrayOf('H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd'))
fun main(args: Array<String>) {
    // == 比较是否相等， === 表示引用是否一样
    println(string == fromChars)
    println(string === fromChars)
    val arg1: Int = 0
    val arg2: Int = 1
    println("$arg1+$arg2=${arg1 + arg2}")//0+1=1
    //Hello "Trump"
    val sayHello: String = "Hello \"Trump\""
    println(sayHello)
    //$1000
    val salary: Int = 1000
    println("$${salary}")
    //$salary
    println("\$salary")
    //""" 三引号:原始字符串！无法使用转义 非得转义需要空格 "$ salary"
    val rawString:String="""\t \n $salary"""
    println(rawString)//\t \n 1000
    println(rawString.length)//10

    val girl = Girl("linyichen", 1)
    println(girl is Person)


}
class Girl(name: String, age: Int):Person(name,age){
    fun test(): Unit {
        println("$name")
    }
}
open class Person(var name:String,var age:Int){
    init {
        println("new a ${this.javaClass.simpleName} ,name is $name,age is $age")
    }
}

