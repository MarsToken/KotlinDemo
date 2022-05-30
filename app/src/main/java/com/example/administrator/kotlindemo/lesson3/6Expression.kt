package com.example.administrator.kotlindemo.lesson3

/**
 * 1.中缀表达式
 *  只有一个参数，且用infix修饰 比如 Complex里的book与desk关系
 * 2.分支（if）表达式
 * 3.when 表达式
 * Created by Administrator on 2021/6/18.
 */
class Expression{

}

private const val USERNAME = "kotlin"
private const val PASSWORD = "jetbrains"
private const val SUCCESS = 1
private const val FAILURE = 0

fun main(args: Array<String>) {
    //when表达式
    whenExpression()
    println("请输入用户名")
    val username = readLine()
    println("请输入密码")
    val password = readLine()
    //if表达式
    val isLoginSuccess = if (username == USERNAME && password == PASSWORD) {
        SUCCESS
    } else {
        FAILURE
    }
    if (isLoginSuccess == SUCCESS) {
        println("登录成功")
    } else {
        println("登录失败")
    }

}

fun whenExpression() {
    val x = 5
    when(x){//只会执行一个
        is Int -> println("Hello,$x")
        in 1..100 -> println("$x is in 1..100")
        !in 1..100 -> println("$x is not in 1..100")
        5 -> println("$x is 5")
        else -> println("not match one,exit!")
    }
}
