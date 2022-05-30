package com.example.administrator.kotlindemo.lesson4

/**
 * 1.Kotlin里默认的内部类时静态内部类
 * 2.如果设置为非静态可以通过 inner 关键字
 * 3.匿名内部类通过：object:内部类{}实现
 *  kotlin 里的匿名内部类可以继承其他某个类，并实现多个接口
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    val staticInner = Outter.StaticInner()

    val outter = Outter()
    val notStaticInner=outter.NotStaticInner()

    //匿名内部类
    val view = View()
    view.onClickListener = object : Outter(), OnClickListener {
        override fun onClick(view: View) {
            println("click $view")
            this.test()
        }
    }
    view.onClickListener.onClick(view)

}
class View{
    lateinit var onClickListener:OnClickListener

}
interface OnClickListener{
    fun onClick(view:View)
}

open class Outter{
    val a: Int = 0

    //静态
    class StaticInner{
        fun hello() {
            //println(this@Outter.a)//静态不持有外部类引用
        }
    }
    //非静态
    inner class NotStaticInner{
        fun hello() {
            println(this@Outter.a)//持有外部类引用
            println(a)
        }
    }
    fun test(){
        println("I am outter class's test method!")
    }
}
