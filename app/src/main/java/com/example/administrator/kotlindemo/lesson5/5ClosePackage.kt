package com.example.administrator.kotlindemo.lesson5

/**
 * 闭包
 * 函数和对其周围状态（lexical environment，词法环境）的引用捆绑在一起构成闭包
 *  函数的运行时环境
 *  持有函数运行的状态！！！这个很重要
 *  函数内部可以定义函数
 *  函数内部也可以定义类
 *
 * Created by Administrator on 2021/6/21.
 */
fun main(args: Array<String>) {
    //这个不是闭包，因为没有返回值，或者说返回值count不是函数，所以已经没有继续执行的逻辑了
    val count = getCount()
    count
    count
    count
    //下边才是闭包，x为函数，它可以继续执行
    val x = makeFun()
    x()//1
    x()//2
    x()//3
    x()//4
    x()//5

    val y = fibonacci()
    y()
    y()
    y()
    y()
    y()
    y()

    val z = fibonaccis()
    for (i in fibonaccis()) {
        if (i > 100) break
        println(i)
    }
    println("======add========")
    println(add(5)(2))//7
    println(addEx(5)(2))

}
//1与2等价 即函数柯里化
fun addEx(x:Int):(Int)->Int{
    return fun(y:Int):Int{
        return x+y
    }
}
//2与1等价，实际上是1的缩写而已 即函数柯里化
fun add(x:Int)= fun(y: Int) = x + y


fun fibonaccis():Iterable<Long>{
    var first = 0L
    var second = 1L
    return Iterable {
        object :LongIterator(){
            override fun hasNext() = true

            override fun nextLong(): Long {
                val result = second + first
                second = result
                first = result - first
                println("result=$result")
                return result
            }

        }
    }
}
//f(n)=f(n-1)+f(n-2)
fun fibonacci():()->Long{
    var first = 0L
    var second = 1L
    return fun():Long{
        val result = second + first
        second = result
        first = result - first
        println("result=$result")
        return result
    }
}

fun makeFun():()->Unit{
    var count = 0
    return fun(){
        println(++count)
    }
}
fun getCount(){
    var count = 0
    println(++count)
}