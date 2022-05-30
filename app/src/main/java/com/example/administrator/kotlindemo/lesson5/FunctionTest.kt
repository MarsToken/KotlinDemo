package com.example.administrator.kotlindemo.lesson5

import java.io.OutputStream

/**
 * Created by Administrator on 2021/6/21.
 */
fun main() {
    val res1 = multiply2Ex(add5Ex(8))
    println("res1=$res1")
    val res2 = add5Ex addThenEx2 multiply2Ex
    println("res2=${res2(8)}")

    logEx("Currying",System.out,"Hello!")
    logEx2("Currying")(System.out)("Hello,again!")
    ::logEx.curryingEx()("Currying")(System.out)("Hello,again and again!")
}

private val add5Ex={x:Int-> x + 5}
private val multiply2Ex={y:Int-> y * 2}
//complex function m(x)=g(f(x)) y=f(x) m(x)=g(y) P1=x,P2=y,P3=m(x)
private infix fun <P1,P2,R>Function1<P1,P2>.addThenEx(function:Function1<P2,R>):Function1<P1,R>{
    return fun(p1: P1):R {
        return function.invoke(this.invoke(p1))
    }
}
private infix fun<P1,P2,R>Function1<P1,P2>.addThenEx2(function: Function1<P2,R>)=fun(p1:P1)=function.invoke(this.invoke(p1))

private fun logEx(tag: String, target: OutputStream, info: Any) {
    target.write("[$tag] $info\n".toByteArray())
}
//柯里化 currying
private fun logEx1(tag:String)=fun(target:OutputStream)=fun(info:Any)=target.write("[$tag] $info\n".toByteArray())
//details
private fun logEx2(tag:String):(OutputStream)->(Any)->Unit{
    return fun(target:OutputStream):(Any)->Unit{
        return fun(info:Any){
            target.write("[$tag] $info\n".toByteArray())
        }
    }
}
//偏函数
private fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.curryingEx() = fun(tag: String) = fun(target: OutputStream) = fun(info: Any) = target.write("[$tag] $info\n".toByteArray())

