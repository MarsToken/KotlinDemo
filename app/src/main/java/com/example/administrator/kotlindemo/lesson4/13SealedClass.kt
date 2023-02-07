package com.example.administrator.kotlindemo.lesson4


/**
 * 是增强版的枚举类，用法？
 * 密封类不允许有非-private 构造函数（其构造函数默认为 private）
 * 1.引用 sealed class 的类 必须定义在同一个文件内
 * 2.1的特点决定了 可以有效的保护指令集，外界不可以拓展！！！，无法被继承
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    val pause: PlayerCmd = PlayerCmd.Pause
    printPlayStatus(PlayStatus.PAUSED)
}

sealed class PlayerCmd {
    // 和枚举区别，它可以定义多个实例，枚举只能唯一。
    class Play(val url: String, val position: Long = 0) : PlayerCmd(){
        fun test(){

        }
    }
    class Seek(val position: Long) : PlayerCmd()
    object Pause : PlayerCmd()
    object Resume : PlayerCmd()
    object Stop : PlayerCmd()
}

//状态
enum class PlayStatus(val status: String) {
    PLAYING("playing"), PAUSED("paused")
}

private fun printPlayStatus(status: PlayStatus) {
    println(status.status)
}