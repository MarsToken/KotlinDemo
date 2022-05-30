package com.example.administrator.kotlindemo.lesson5

/**
 * Created by tellerWang on 2021/6/21.
 */

fun main() {
    val result = multiply2(add5(8))
    println("1,result=$result")
    val add = add55(8)
    println("2,result=${multiply2(add)}")
    val res = add55 addThens multiply22//(x+5)*2
    println("3,result=${res(8)}")
    val res2 = add55 composes multiply22
    println("4,result=${res2(8)}")
}

infix fun <P1,P2,R> Function1<P1,P2>.addThens(function:Function1<P2,R>)= fun(p1: P1) = function.invoke(this.invoke(p1))
infix fun <P1,P2,R> Function1<P2,R>.composes(function:Function1<P1,P2>)= fun(p1: P1) = this.invoke(function.invoke(p1))

val add55 = { x: Int -> x + 5 }
val multiply22 = { y: Int -> y * 2 }

val addX = { x: Int, y: Int -> x + y }



