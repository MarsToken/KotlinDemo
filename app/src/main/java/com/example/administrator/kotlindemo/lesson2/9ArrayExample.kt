package com.example.administrator.kotlindemo.lesson2

/**
 * Created by Administrator on 2021/6/16.
 */
val arrayOfInt: IntArray = intArrayOf(1, 3, 5, 7)
//与上面的表达等价
val arrayOfInteger: Array<Int> = arrayOf(2, 4, 5)
val arrayOfChar: CharArray = charArrayOf('H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd')
val arrayOfString: Array<String> = arrayOf("我", "是", "编程人员")
//Array<T> 元素为 泛型T 的数组
val arrayOfFruit: Array<Fruit> = arrayOf(Fruit("苹果"), Fruit("梨"))


fun main(args: Array<String>) {
    println(arrayOfChar.joinToString(""))//连接数组 HelloWorld
    println(arrayOfChar.joinToString(" "))//连接数组 H e l l o W o r l d
    println(arrayOfChar.joinToString())//连接数组 H, e, l, l, o, W, o, r, l, d
    println(arrayOfInt.slice(1..2))//3,5
    println(arrayOfInt.slice(1 until 2))//3

}

open class Fruit(val name: String){
    init {
        println("name is $name!")
    }
}
