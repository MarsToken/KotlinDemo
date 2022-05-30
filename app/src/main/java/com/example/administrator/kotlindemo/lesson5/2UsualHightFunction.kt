package com.example.administrator.kotlindemo.lesson5

import java.io.BufferedReader
import java.io.FileReader
import java.lang.Exception

/**
 * 域函数
 * let run with apply also
 * Created by Administrator on 2021/6/23.
 */
fun main() {
    //1.forEach 遍历
    println("======forEach=========")
    val list = arrayOf(1, 2, 3, 4, 5, 6)
    val newList = ArrayList<Int>()
    list.forEach {
        newList.add(it * 2 + 1)
    }
    newList.forEach(::println)
    //2.map 生成新数组，影响性能
    println("======map=========")
    val newList1 = list.map {
        it * 2 + 3
    }
    newList1.forEach(::println)
    //3.flatMap 双重循环，将多元分解为1元
    println("======flatMap=========")
    val list1 = listOf(1..2, 2..5, 10..20)
    //看提示：下一行右下方
    val newList2 = list1.flatMap {
        it.map {
            "No$it"
        }
    }
    newList2.forEach(::println)
    //4.reduce 求和，求阶乘
    println("=============reduce=================")
    //acc 前边的累计，i 第i个元素
    val result = list.reduce { acc, i -> acc + i }
    println("求和=$result")
    val result1 = list.reduce { acc, i -> acc * i }
    println("求阶乘=$result1")
    //=====================fold foldRight=========================
    //5.fold 与reduce类似，reduce没有初始值是第一个元素；fold可以指定初始值 acc，它是 fold 传递的第一个参数
    println("=============fold=================")
    val result2 = (0..6).fold(1) { acc, i -> acc + i }
    println("acc初始值为1：$result2")
    val result3 = (0..6).map(::factorial).fold(5) { acc, i ->
        acc + i
    }
    //874=6!+5!+4!+3!+2!+1!+0!=720+120+24+6+2+1+1
    //879=873+5（初始值）
    println("求: $result3")
    //相当于初始值为空，拼接字符串1
    println((0..6).fold(StringBuilder()) { acc, i ->
        acc.append(i).append(",")
    })
    //拼接字符串2
    println((0..6).joinToString(","))
    //====================filter======================
    //6.filter
    println("=============filter=================")
    //保留奇数
    println((0..6).map(::factorial).filter {
        it % 2 == 1
    })
    //保留位数为奇数的值
    println((0..6).map(::factorial).filterIndexed { index, i -> index % 2 == 1 })
    //====================takeWhile======================
    //7.takeWhile 取数据直到不满足条件
    println("=============takeWhile=================")
    println((0..6).map(::factorial).takeWhile {
        it % 2 == 1
    })
    //====================let======================
    //8.let 返回的不为空则打印
    println("=============let=================")
    findPerson()?.let {
        println("${it.name}-${it.age}")
    }
    //data class 特性
    findPerson()?.let { (name, age) ->
        println("$name-$age")
    }
    //====================apply======================
    //9.apply 它没有返回值，let有返回值
    //返回类型为 Unit 的方法的 Builder 风格用法
    println("=============apply=================")
    findPerson()?.apply {
        work()
        println(age)
    }
    //ex 配置对象的属性,这对于配置未出现在对象构造函数中的属性非常有用。
    val myPerson = Person("jerry", 11).apply {
        gender = "1"
    }
    //====================with======================
    //10.with 与apply区别，receiver到底是被调用还是被传递，返回值取决于大括号最后一行代码
    //对一个对象实例调用多个方法 （with）
    println("=============with=================")
    val p = with(findPerson()) {
        this?.work()
        println(this?.age)
    }//p为Unit
    //具体使用场景
    try {
        val br = BufferedReader(FileReader("hello.txt"))
        with(br) {
            var line: String?
            while (true) {
                line = readLine() ?: break
                println(line)
            }
            close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    //封装
    try {
        val br_ex = BufferedReader(FileReader("hello.txt")).readLines()//readText()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    //====================use======================
    //11.use 与 with 区别，
    println("=============with=================")
    //必须实现 closeable 接口 封装了关闭的逻辑
    BufferedReader(FileReader("hello.txt")).use {
        var line: String?
        while (true) {
            line = it.readLine() ?: break
            println(line)
        }
    }
    //12.also
    println("=============also=================")
    //4.交换两个变量
    var a = 1
    var b = 2
    a = b.also {
        println("it=$it")
        b = a
    }
    //13.run
    val run_result = findPerson()?.run {
        println("name is $name,age is $age")
    }
    println("run_result is $run_result")
}

fun factorial(n: Int): Int {
    if (n == 0) return 1
    return (1..n).reduce { acc, i -> acc * i }
}

fun findPerson(): Person? {
    return Person("tom", 12)
}

data class Person(val name: String, val age: Int) {
    lateinit var gender: String

    //lateinit var int:Int//int有默认值，无法与 lateinit 连用
    fun work() {
        println("$name is working!")
    }
}