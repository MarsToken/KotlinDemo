package com.example.administrator.kotlindemo.lesson3

/**
 * 运算符重载的定义：
 * 1.必须operator
 * 2.函数名字必须相同（参考官方文档），形式参数也必须相同，返回值无要求！！！
 * 3.注意必须依赖类
 *  a+b a.plus(b)
 *  a-b a.minus(b)
 *  a*b a.times(b)
 *  a/b a.div(b)
 * Created by Administrator on 2021/6/18.
 */
//方法一
class Complex(var real: Double, var imaginary: Double){
    operator fun plus(other:Complex):Complex{
        return Complex(real+other.real,imaginary+other.imaginary)
    }
    operator fun minus(other: Complex):Complex{
        return Complex(real-other.real,imaginary-other.imaginary)
    }
    operator fun times(other: Complex):Complex{
        return Complex(real*other.real,imaginary*other.imaginary)
    }
    operator fun div(other: Complex):Complex{
        return Complex(real/other.real,imaginary/other.imaginary)
    }

    override fun toString(): String {
        return "($real+$imaginary i)"
    }
}

//方法二
class A(var number: Double){
    operator fun plus(a: A):Double {
        return number+a.number
    }

    override fun toString(): String {
        return "$number"
    }
}
//自定义运算符-中缀表达式 一般dsl会用
class Book{
    infix fun on(any:Any):Boolean{
        return true
    }
}
class Desk{

}

fun main(args: Array<String>) {
    val c1 = Complex(3.0, 4.0)
    val c2 = Complex(2.0, 7.5)
    println("$c1+$c2=${c1 + c2}")
    println("$c1-$c2=${c1 - c2}")
    println("$c1*$c2=${c1 * c2}")
    println("$c1/$c2=${c1 / c2}")
    //方法二
    val a1 = A(1.0)
    val a2 = A(2.0)
    println("$a1+$a2=${a1 + a2}")

    if (Book() on Desk()) {//dsl
        println("book is on the desk.")
    }
}
