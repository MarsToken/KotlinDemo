package com.example.administrator.kotlindemo.lesson6.coRountine1

import com.example.administrator.kotlindemo.utils.LogUtil
import java.lang.Exception
import java.lang.Thread.sleep
import kotlin.coroutines.*

/**
 * 协程
 * 1.各个子任务协作运行，解决异步问题
 * 2.应用层完成调度
 * 3.协程要解决的问题
 *  1.异步代码像同步代码一样直观
 *  2.简化异步代码异常处理
 *  3.轻量级的并发方案
 * 4.kotlin如何支持，
 *  1.编译器对suspend函数的编译支持，kotlin识别后会挂起
 *  2.标准库的基本API，
 *  3.kotlinx.coroutine 框架 应用及支持
 * 5.协程没有异步的能力
 *
 * Created by tellerWang on 2021/6/22.
 */
class CoRoutine {

}

val log = LogUtil.log("CoRoutine")
fun main() {
    log("协程开始之前")
    startCoRoutine {
        log("协程开始")
        val imageData = loadImage("图片地址：http://www.piseeg")
        log("拿到图片了")
        log("设置图片:${String(imageData)}")
    }
    log("协程结束了")
}

fun startCoRoutine(block: suspend () -> Unit) {
    block.startCoroutine(BaseContinuation())//suspend方法才有

}
suspend fun loadImage(url: String) = suspendCoroutine<ByteArray> {
    continuation ->
    log("异步任务开始前")
    val uiContinuationWrapper = UIContinuationWrapper(continuation)
    try {
        log("耗时操作，下载图片")
        val byteArray= getImage(url)
        if (byteArray.isNotEmpty()) {
            byteArray.let{//let(continuation::resume)
                uiContinuationWrapper.resume(it)
            }
        } else {
            uiContinuationWrapper.resumeWithException(HttpException(-1))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        uiContinuationWrapper.resumeWithException(e)
    }
}

fun getImage(url: String): ByteArray {
    sleep(2000)
    return "$url 图片内存地址".toByteArray()
}

class BaseContinuation:Continuation<Unit>{
    override val context: CoroutineContext = EmptyCoroutineContext

    override fun resumeWith(result: Result<Unit>){

    }
}

data class HttpException(val code:Int):Exception()

class UIContinuationWrapper<T>(val continuation: Continuation<T>):Continuation<T>{
    override val context: CoroutineContext = EmptyCoroutineContext


    override fun resumeWith(result: Result<T>) {
        log("handler 在此切线程")
        continuation.resumeWith(result)
        log("handler 在此切线程2")
    }

}