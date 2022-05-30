package com.example.administrator.kotlindemo.lesson2

/**
 * Created by Administrator on 2021/6/16.
 */
fun main(args: Array<String>) {
    val range:IntRange=0..10//闭区间
    val ranges:IntRange=0 until 10//左闭右开
    val range_step1 = (1..10) step 2//1,3,5,7,9
    val range_step1_1 = (100..10) step 2//空，不支持逆序
    val range_step2= 9 downTo 0 step 3//9630 逆序这样表达
    val emptyRange: IntRange = 0..-1
    println(emptyRange.isEmpty())//true
    println(range.contains(0))//true
    println(range.contains(10))//true
    println(ranges.contains(0))//true
    println(ranges.contains(10))//false
    for (i in ranges) {
        println("$i")//0-9
    }
}

