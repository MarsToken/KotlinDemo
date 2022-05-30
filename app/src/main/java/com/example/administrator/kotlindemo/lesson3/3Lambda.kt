package com.example.administrator.kotlindemo.lesson3

/**
 * 1.lambda表达式为匿名函数
 * 格式{匿名函数}
 * 匿名函数格式：
 * 参数列表 -> 函数体，之后一行为返回值
 * 2.调用() 或者 invoke()
 * 3.注意四点，下边阐述！
 * Created by Administrator on 2021/6/17.
 */
val sum={arg1:Int,arg2:Int->
    println("$arg1+$arg2=${arg1+arg2}")
    arg1 + arg2
}
val sayHello={
    println("Hello")
}
//匿名函数
val int2Long=fun(x:Int):Long{
    return x.toLong()
}
//lambda表达式，与上述等价
val int2LongLambda={x:Int->x.toLong()}
//测试最后一个参数为lambda的函数
val lastLambda=fun(x:Int,f:(y:Any)->Unit):Unit{
    f(x)
}
fun main(args: Array<String>) {
    println(sum(1,3))
    println(sum.invoke(1,3))//与上述等价

    val array = arrayOf("!", "@", "#", "$", "%")
    for (arg in array) {
        print("$arg,")
    }
    println("=========")
    //array的拓展方法forEach
    //1.Lambda 只有一个参数可默认为 it
    array.forEach ({
        print("$it,")
    })
    //2.函数参数调用的最后一个Lambda可以移出去（移到小括号外）
    array.forEach (){ println("") }
    //3.函数参数只有一个lambda，调用时小括号可以省略
    array.forEach {
        print("$it,")
    }
    //4.入参，返回值与形参一致的函数可以用函数引用的方式作为实参传入 5 1HighFun 有特例，比如类里面的函数调用
    array.forEach(::println)
    //跳出当前循环，继续往下执行
    array.forEach ForEach@{
        if(it=="$") return@ForEach//跳出当前循环，继续往下执行
        print("$it,,,")
    }
    //2.函数参数调用的最后一个Lambda可以移出去（移到小括号外）
    //常规调用
    lastLambda(1,{ println("")})
    //精简版
    lastLambda(1){ println("")}
}