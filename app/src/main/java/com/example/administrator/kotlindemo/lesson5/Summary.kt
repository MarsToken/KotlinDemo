package com.example.administrator.kotlindemo.lesson5

/**
 * Created by tellerWang on 2021/6/22.
 */
class Summary {
    //函数复合 m(x)=f(g(x))=f(y) y=g(x) 所以 被定义的对象为y=g(x) 返回值为 m(x)，入参为 f(y) x:P1,y:P2,m(x):R
    infix fun <P1, P2, R> Function1<P1, P2>.addThen(function: Function1<P2, R>) = fun(p1: P1) = function.invoke(this.invoke(p1))
    //调用-val value = add5 addThen multiply2 value(8)

    //柯里化
    fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.currying() = fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3)
    //调用-::log.currying()("Currying")(System.out)("Hello,again and again!")

    //偏函数
    fun <P1, P2, R> Function2<P1, P2, R>.partial2(p2: P2) = fun(p1: P1) = this(p1, p2)
    //调用-::makeString.partial2(charset("GBK"))(partial1)

}