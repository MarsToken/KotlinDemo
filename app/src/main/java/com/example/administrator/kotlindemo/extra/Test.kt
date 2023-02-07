package com.example.administrator.kotlindemo.extra

// Kotlin 代码


open class Person {
    var name: String = ""
    var age: Int = 0
}

class Helper {
    private fun walkOnFoot() {
        println("用脚走路")
    }
    val Person.isAdult: Boolean
        get() = age >= 18

    fun Person.walk() {
        // 调用了Helper的私有方法
        walkOnFoot()
    }

    fun test() {
        val person = Person()
        // 仅可以在Helper类当中使用此扩展
        person.walk()
    }
}