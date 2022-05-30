package com.example.administrator.kotlindemo.lesson2

/**
 * Created by Administrator on 2021/6/15.
 */
fun main(args: Array<String>) {
    val parent: Parent = Child()
    if (parent is Child) {
        parent.getName()//java需要强制类型转换
    }
    val parent1: Parent = Parent()
    //val child1: Child? = parent1 as Child //ClassCastException
    val child1: Child? = parent1 as? Child//防止类型转换异常
    println(child1)//null

}

open class Parent(){

}
class Child():Parent(){
    fun getName():String{
        return "name"
    }
}