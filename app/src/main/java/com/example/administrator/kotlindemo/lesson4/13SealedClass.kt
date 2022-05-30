package com.example.administrator.kotlindemo.lesson4


/**
 * 是增强版的枚举类，用法？
 * 密封类不允许有非-private 构造函数（其构造函数默认为 private）
 * 1.引用 sealed class 的类 必须定义在同一个文件内
 * 2.1的特点决定了 可以有效的保护指令集，外界不可以拓展！！！
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    val pause: PlayerCmd = PlayerCmd.Pause
}
sealed class PlayerCmd{
    class Play(val url: String, val position: Long = 0):PlayerCmd()
    class Seek(val position:Long):PlayerCmd()
    object Pause:PlayerCmd()
    object Resume:PlayerCmd()
    object Stop:PlayerCmd()
}
//状态
enum class PlayStatus{

}