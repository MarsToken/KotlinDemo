package com.example.administrator.kotlindemo.jetpack.introduction

import com.example.administrator.kotlindemo.utils.LogUtil
import kotlinx.coroutines.*

/**
 * Created by tellerWang on 2021/6/22.
 */
val log = LogUtil.log("Coroutine")

fun main() {
    test()
}

private fun test() {
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        log("start launch")
        delay(1500L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        log("World!,name= $CoroutineName") // 在延迟后打印输出
    }
    log("Hello,=$CoroutineName") // 协程已在等待时主线程还在继续
    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
}
