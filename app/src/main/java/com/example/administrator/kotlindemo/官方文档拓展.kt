package com.example.administrator.kotlindemo

/**
 * Created by tellerWang on 2021/6/25.
 */
/*

* 1.如果一个不可变的局部变量或属性已经判断出为某类型，那么检测后的分支中可以直接当作该类型使用，无需显式转换
    猜测：当然 函数的形参 也是不可变的

* */
fun main() {
    //2.关于区间
    val range_step1 = (1..10) step 2//1,3,5,7,9
    val range_step1_1 = (100..10) step 2//空，不支持逆序
    val range_step2 = 9 downTo 0 step 3//9630 逆序这样表达
    //3.如果为空 ?:
    val tag: String? = null
    println(tag?.length ?: "empty")
    //4.交换两个变量
    var a = 1
    var b = 2
    a = b.also {
        b = a
    }
    //5.数字可以加 “_” 目的完全是为了易读
    val int: Long = 1000_000L + 3
    //6.数组也可以这么定义
    val arrays = Array(2) {
        (it * 2).toString()
    }
    val array = arrayOf("1", "2")
    //7.无符号整形 kotlin 1.3+ 不稳定
    val uInt: UInt = 1u//无符号 32 比特整数，范围是 0 到 2^32 - 1
    val uArray = UIntArray(2) {
        it.toUInt()
    }
    //8.字符串  函数去除前导空格,默认 | 用作边界前缀
    val text = """
    |Tell me and I forget.
    >Teach me and I remember.
    >Involve me and I learn.
    \(Benjamin Franklin)
    """.trimMargin(">")//去除前导空格，默认为 | ，现在换为 >
    println(text)
    val price = """
        ${'$'}9.99
                """
    println(price)
    //9.表达式 kotlin 1.3+ 特性
    when (val result = getResult()) {
        1 -> println("ok")
        else -> println("no")
    }
    //常规用法
    var number = 10
    when (number) {
        in 1..10 -> println("is in 1..10")
        else -> println("not in 1..10")
    }
    val t = when {
        number in 1..10 -> println("is in 1..10")
        else -> println("not in 1..10")
    }
    //10.return,break,continue
    foo()
    foo2()
}

private fun getResult(): Int = 1

fun foo() {//1.同名字，可以直接返回，不用成对出现 @forEach，2.不用名字需要成对出现
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return@forEach// 局部返回到该 lambda 表达式的调用者，即 forEach 循环
        print(it)//1245
    }
    println(" done with explicit label")//执行
}
fun foo2() {//借助run函数，相当于break
    val result = run loop@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@loop 1// 从传入 run 的 lambda 表达式非局部返回,意为“返回 1 到 @loop”，即 result 上
            print(it)//12
        }
    }
    println(" done with nested loop , result is $result")//执行
}
