package com.example.administrator.kotlindemo.lesson4

import kotlin.reflect.KProperty

/**
 * 属性代理，灵感 by lazy
 * var 必须重写 get ，set
 * val 必须重写 get
 * 主要是使用属性
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    val delegates = Delegates()
    println(delegates.hello)
    println(delegates.hello2)
    println(delegates.hello3)
    delegates.hello3 = "hello3"//set
    println(delegates.hello3)
    //可观察属性 Observable
    var name: String by kotlin.properties.Delegates.observable("<no name>") { prop, old, new ->
        println("oldValue=$old,newValue=$new,$prop")
    }
    name = "first"
    name = "second"
    println("name=$name")
    //中途截取
    println("中途截取")
    var name_:String by kotlin.properties.Delegates.vetoable("<no name>"){ prop, old, new ->
        println("oldValue=$old,newValue=$new,$prop")
        false//替换失败
    }
    name_ = "tom"
    println("name_=$name_")
}
class Delegates{
    val hello by lazy {
        "Hello world"
    }
    val hello2 by X()

    var hello3 by Y()

}
class X{
    //灵感， by lazy
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = "hello"
}
class Y{
    private var value:String?=null
    //thisRef 为被代理的对象，即 delegates property.name为字段引用名字，value为字段实际值
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("getValue,$thisRef->${property.name}=$value")
        return value ?: "--"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>,value:String){
        println("setValue,$thisRef->${property.name}=$value")
        this.value=value
    }

}

//属性代理，抛弃的值被新值所替换
class MyClass{
    var newName = 0

    @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
    var oldName: Int by this::newName

    fun setName(name: Int) {
        oldName = name
    }
}
//将属性储存在映射中
class User(val map:Map<String,Any?>){
    val name:String by map
    val age:Int by map

    fun setValue() {
        val user = User(mapOf("name" to "John", "age" to 5))
    }
}
