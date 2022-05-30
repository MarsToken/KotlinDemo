package com.example.administrator.kotlindemo

/**
 * 泛型，嵌套类，内部类，内联类（kotlin 1.3+）
 * kotlin没有通配符
 * 它有声明处型变(declaration-site variance) 与 类型投影(type projections)
 *
 * Java里
 *  消费者-只能从中写入的对象
 *  生产者-只能从中读取的对象
 *
 * 为了灵活性最大化，在表示生产者或消费者的输入参数上使用通配符类型
 *
 * ? extends E 读取用，不能写入 生产者类型具有 协变性 out
 * ? super E 写入用，不能读取 消费者类型具有 逆变性 in
 * Created by tellerWang on 2021/6/30.
 */
//一.声明处型变：型变注解

//out（只有读取，可忽略写入） 声明处型变,对比Generic.java 相当于是 ? extends Object
//我们可以标注 Source 的类型参数 T 来确保它仅从 Source<T> 成员中返回（生产），并从不被消费（写入）。 为此，我们提供 out 修饰符

//假设有一个泛型接口 Source<T>，该接口中不存在任何以 T 作为参数的方法，只是方法返回 T 类型值
//即只有读取，没有写入
interface Source<out T> {
    fun nextT(): T
}

fun read(strs: Source<String>) {
    val objects: Source<Any> = strs //因为 T 是一个 out-参数，只允许读取

}
//只有写入，可忽略读取
interface Source2<in T>{
    fun setValue(value:T):Int
}

//二.类型投影
//只允许 from 读取
fun copy(from: Array<out Any>, to: Array<Any>) {

}
//只允许dest 写入
fun fill(dest: Array<in String>, value: String) {

}

//在尖括号中只能指定一个上界。 如果同一类型参数需要多个上界，我们需要一个单独的 where-子句
fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
        where T : CharSequence,
              T : Comparable<T> {
    return list.filter { it > threshold }.map { it.toString() }
}

//嵌套类与内部类
class OutA {
    private val age = 1
    val name = "tom"

    class NestA {
        fun nest() {
            //嵌套类无法访问
            //println("name=$name,age=$age")
        }
    }

    inner class InnerA {
        //内部类持有外部类引用，可以反问外部类里的变量
        fun inner() {
            println("name=$name,age=$age")
        }
    }
}
//内联类
inline class Password(val name:String){

}
fun main() {
    val from = arrayOf(1, 2, 3)
    from[1] = 9
    val to = Array<Any>(3) {
    }
    copy(from, to)
    println("${Password("jack").name}")

}
