package com.example.administrator.kotlindemo.lesson2

/**
 * Created by Administrator on 2021/6/15.
 */
// == 比较是否相等， === 表示引用是否一样
//println("$arg1+$arg2=${arg1 + arg2}") 0+1=1
//""" 三引号:原始字符串！无法使用转义
//所有类都继承 Any
//如果一个不可变的局部变量或属性已经判断出为某类型，那么检测后的分支中可以直接当作该类型使用，无需显式转换
//猜测：当然 函数的形参 也是不可变的
fun getStringLength(str: Any): Any = if (str is String) str.length else -1
