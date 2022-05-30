package com.example.administrator.kotlindemo

/**
 * Created by tellerWang on 2021/6/25.
 */
fun main() {
    Person(1)
    Person(Person(1))
    Child()
    testTypeAlias()
    testRight()
    testExFun()
    testDataClass()
}
//一.类与继承
//1.一个类可以无主构造函数
class Person {
    var children: MutableList<Person> = mutableListOf()
    constructor(parent: Person){
        parent.children.add(this)
    }
    constructor(age: Int){
        println("age is $age")
    }
}
//2.一个类 如果有主构造必须声明在类头部，且其他次构造必须直接委托给主构造或者间接委托给主构造，这一结论直接导致主构造里的变量必须有默认值
open class Parent(val age:Int){
    constructor(name: String, age: Int) : this(age)
    constructor(name: String) : this(age = 1)


    open fun test() {
        println("我是外部类的超类")
    }
}
//3.如果主构造函数的所有的参数都有默认值，编译器会生成 一个额外的无参构造函数，它将使用默认值
class Child(val name:String="tome")

//4.如果派生类有一个主构造函数，其基类可以（并且必须） 用派生类主构造函数的参数就地初始化
class Girl(age:Int) : Parent(age)
class Girl2(age:Int):Parent(age=1)//此时意义不大呀！
//5.1如果派生类没有主构造函数，那么每个次构造函数必须使用 super 关键字初始化其基类型，或委托给另一个构造函数做到这一点
class Girls:Parent{
    constructor():super(age=1)
}
//5.2 or
class Girls2:Parent(age=1)
//6.方法复写
class Girls3:Parent{
    constructor():super(age=1)

    override fun test() {
        super.test()
        println("我是外部类")
    }
    fun test1() {
        println("我是外部类")
    }

    //7.在一个内部类中访问"外部类的超类"，可以通过由外部类名限定的 super 关键字来实现：super@Outer：
    inner class Test{
        fun testAll() {
            super@Girls3.test()//外部类的超类.test方法
            test1()//外部类的.test方法
        }
    }
}
interface Speck{
    fun test(){
        println("I am from interface!")
    }
}

//8.为了表示采用从哪个超类型继承的实现，我们使用由尖括号中超类型名限定的 super，如 super<Base>
class Girl4 : Parent(age = 1), Speck {
    override fun test() {
        //默认是父类，而不是接口的，因为有super
        //super.test()
        super<Parent>.test()
        super<Speck>.test()
    }
}

//9.我们可以用一个抽象成员覆盖一个非抽象的开放成员-java无此功能
abstract class Girl5 : Parent(age = 1) {
    abstract override fun test()

}

//二.属性:
//1.要检测一个 lateinit var 是否已经初始化过，请在该属性的引用上使用 .isInitialized：
class Test{
    private lateinit var field: String
    fun check() {
        if (this::field.isInitialized) {

        }
    }
}
//三.函数式接口
//kotlin 1.4+新特性
fun interface A{
    fun test1(str:String)
    fun test2(int: Int){

    }
    fun test2(boolean: Boolean){

    }

}

val a = object : A {
    override fun test1(str: String) {

    }
}
//SAM kotlin 1.4+新特性
val a1 = A {
    println("it is $it")
}
//四.类型别名
typealias s = String
typealias one = () -> Unit
typealias OuttersInner = Outter.Inner

class Outter{
    inner class Inner
}

fun testTypeAlias() {
    val str: s = "I'm String"
    val one_params :one = { println("i am one params function.") }
    val outter = Outter()
    val inner:OuttersInner = outter.Inner()

    println("string's alias is $s")
}

//五.可见性修饰符
fun testRight() {
    Mama().test()
    Mama().b_
}
open class Papa{
    private val a_ = 1
    open val b_ = 2
    open fun test() {
        println("a_ is $a_,b_ is $b_")//1,3 note 由于 b_为重写，a_没有被复写，所以a_都用自己的，b_都用被复写过的
    }
}
class Mama:Papa(){
    val a_= 2
    override val b_ = 3

    override fun test() {
        super.test()
        println("a_ = $a_,b_ = $b_")//2,3
    }
}
//六.拓展函数:静态解析
//如果一个类定义有一个成员函数与一个扩展函数，而这两个函数又有相同的接收者类型、 相同的名字，并且都适用给定的参数，这种情况总是取成员函数
fun testExFun(){
    println(Rectangle().getName())//Rectangle
    printClassName(Rectangle())//Shape note:静态解析，仅仅看代码，取决于形式参数
    //open子类重写父类的方法，这些函数的分发对于分发接收者类型是虚拟的（假的）即只有实现被调用者的类的方法，不会涉及到父类，但对于扩展接收者类型是静态的。
    println("null reference -> ${null.toString()}")

}


open class Shape
class Rectangle:Shape(){
    //拓展声明为成员-对于分发接收者与扩展接收者的成员名字冲突的情况，扩展接收者优先
    fun Parent.getString(){
        toString()//扩展接收者
        this@Rectangle.toString()//分发接收者
    }
}

fun Shape.getName() = "Shape"
fun Rectangle.getName() = "Rectangle"
fun printClassName(s:Shape){
    println(s.getName())//由于静态解析，永远是 "Shape"
}
fun Any?.toString():String{
    if (null == this) return "null"
    return toString()
}
fun testDataClass() {
    val person1=Persons("john")
    val person2 = Persons("john")
    person1.age = 11
    person2.age = 15
    println(person1 == person2)//true
    println(person1 === person2)//false
    val tom = person1.copy()
    println(person1 == tom)//true
    println(person1 === tom)//false
}
//七.数据类 data :标准库提供了 Pair 与 Triple
//在 toString()、 equals()、 hashCode() 以及 copy() 的实现中只会用到 name 属性，并且只有一个 component 函数 component1()。虽然两个 Person 对象可以有不同的年龄，但它们会视为相等
data class Persons(val name: String){
    var age: Int = 0
}