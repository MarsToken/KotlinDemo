package com.example.administrator.kotlindemo.lesson5

/**
 * 复合函数
 *  f(g(x)) m(x)=f(g(x))=f(y)
 *  将函数的返回值作为另一个函数的参数
 * Created by Administrator on 2021/6/21.
 */
//f(g(x)) m(x)=f(g(x))=f(y)
fun main(args: Array<String>) {
    println(multiply2(add5(8)))//(5+8)*2
    val add5AndMultiplyBy2 = add5 addThen multiply2
    println(add5AndMultiplyBy2(8))//m(x)=f(g(x)) 26
    val add5ComposeMultiply2 = add5 compose multiply2
    println(add5ComposeMultiply2(8))//m(x)=g(f(x)) 21=8*2+5

}

val add5 = { i: Int -> i + 5 }
val multiply2 = { i: Int -> i * 2 }

//中缀表达式 addThen，为Function1的扩展方法，自定义名字 （一共有23个Function）lambda表达式！
//P1 P2 表示参数值，R表示返回值 对于 Function1<P1, P2> P1表示参数类型，P2表示返回值类型

// 这么思考：m(x)=f(g(x))=f(y) y=g(x) 所以 被定义的对象为y=g(x) 返回值为 m(x)，入参为 f(y) x:P1,y:P2,m(x):R
infix fun <P1, P2, R> Function1<P1, P2>.addThen(function: Function1<P2, R>): Function1<P1, R> {
    return fun(p1:P1):R{
        return function.invoke(this.invoke(p1))
    }
}
//缩写
//infix fun<P1,P2,R>Function1<P1,P2>.addThen(function:Function1<P2,R>)= fun(p1: P1) = function.invoke(this.invoke(p1))
//先调用 compose 前边的，再调用后边的
infix fun<P1,P2,R> Function1<P2,R>.compose(function: Function1<P1,P2>):Function1<P1,R>{
    return fun(p1:P1):R{
        return this.invoke(function.invoke(p1))
    }
}
