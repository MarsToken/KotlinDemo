package com.example.administrator.kotlindemo.lesson4

/**
 *
 * 1.类单继承
 * 2.接口可定义具体方法
 * 3.抽象类与接口区别：
 *  抽象类有状态，接口没状态
 *  抽象类有方法实现，接口只有无状态默认实现
 *  抽象类只能单继承，接口可以多实现
 *  抽象类反应本质，接口体现能力
 * 4.接口的变量只能声明，无法初始化，抽象类可以初始化
 * 5.父类需要open才可被继承，变量和方法，类都是
 * 6.接口，接口方法，抽象类默认为open
 * 7.复写父类成员需要override关键字
 * Created by Administrator on 2021/6/19.
 */
interface InputDevice {
    var str_input:String
    fun hello() {
        println(this.javaClass.canonicalName + ":I am input device interface! $str_input")
    }

}
interface OutputDevice{
    var str_output:String
    fun hi(){
        println(this.javaClass.canonicalName +"I am output device interface! $str_output")
    }

}
open abstract class Mouse(open var name:String):InputDevice,OutputDevice{
    abstract fun test()
    //open才可以被子类复写
    open fun open_test(){}
}

class LogiteMouse(override var str_input: String, override var str_output: String) :Mouse("tom") {
    override fun test() {
        println("name is $name,str_input is $str_input,str_output is $str_output")
    }

    override fun open_test() {
        super.open_test()
    }
}
//个人感觉 复写变量没大有用！
class LightMouse(override var name: String, override var str_input: String, override var str_output: String) : Mouse(name) {
    override fun test() {
        println("name is $name,str_input is $str_input,str_output is $str_output")
    }

}
fun main(args: Array<String>) {
    var logiteMouse = LogiteMouse("input device","output device")
    logiteMouse.test()
    logiteMouse.hello()
    logiteMouse.hi()
    //测试复写的变量
    val lightMouse = LightMouse("jerry", "input device", "output device")
    lightMouse.test()
}