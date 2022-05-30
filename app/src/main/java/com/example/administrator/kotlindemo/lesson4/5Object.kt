package com.example.administrator.kotlindemo.lesson4

/**
 * object
 * 1.只有一个实例的类
 * 2.不能自定义构造方法
 * 3.可以实现接口，继承父类
 * 4.本质上是单例模式的具体实现
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    MusicPlayer.stop()
}
object MusicPlayer:Player(),OnExternalDriverMountListener{
    override fun onMount(driver: Drivers) {

    }

    override fun onUnMount(driver: Drivers) {

    }

    val state: Int = 0
    fun play(url: String) {

    }
    fun stop() {

    }
}
abstract class Player

interface OnExternalDriverMountListener{
    fun onMount(driver:Drivers)
    fun onUnMount(driver: Drivers)
}
class Drivers