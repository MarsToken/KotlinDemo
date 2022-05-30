package com.example.kotlin.start.test

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.concurrent.Executors
import kotlin.coroutines.*

/**
 * 解决异步，线程切换问题
 * Created by tellerWang on 2021/6/23.
 */
data class Message(var any:Any?) {
    var mStatus = -1
    val logo:Any?=null
    val x=if(logo is Message) logo?.any else null
}

private var mMessage = Message(null)
private const val IMAGE_DOWNLOAD_SUCCESS = 1

fun main() {
    enter()
    while (true) {
        if (mMessage.mStatus != -1) {
            if (mMessage.mStatus == IMAGE_DOWNLOAD_SUCCESS) {
                if (mMessage.any is ByteArray) {
                    val byteArray = mMessage.any as ByteArray
                    Log.logEx("加载图片：${String(byteArray)}")
                }

                mMessage.mStatus = -1
            }
        }
        //must
        Thread.sleep(1)
    }
}

fun enter() {
    Log.logEx("协程开始前")
    startCoroutine {
        Log.logEx("协程刚开始")
        val byteArray:ByteArray = downLoadImage("http://www.xxx.png")
        Log.logEx("开始加载图片了，准备...")
        mMessage.any = byteArray
        mMessage.mStatus = IMAGE_DOWNLOAD_SUCCESS
    }
    Log.logEx("协程已结束")
}

fun startCoroutine(block:suspend () -> Unit) {
    block.startCoroutine(BaseContinuation())

}

suspend fun downLoadImage(url:String):ByteArray{
    return suspendCoroutine<ByteArray> {
        continuation ->
            AsyncTask().execute {
                val uiContinuation = UIContinuation(continuation)
                try {
                    Log.logEx("耗时操作下载图片：$url")
                    Thread.sleep(1000)
                    Log.logEx("图片下载完成")
                    uiContinuation.resume("content=这是一张图片".toByteArray())
                    //"url=$url,content=这是一张图片".toByteArray().let(continuation::resume)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

    }
}
class BaseContinuation : Continuation<Unit> {
    override val context: CoroutineContext = EmptyCoroutineContext

    override fun resumeWith(result: Result<Unit>) {

    }

}

class UIContinuation<T>(private val continuation: Continuation<T>):Continuation<T>{
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<T>) {
        Log.logEx("可在此切换线程到主线程:逻辑也可以写到这儿")
//        mMessage.any = result.getOrNull()
//        mMessage.mStatus = IMAGE_DOWNLOAD_SUCCESS
        continuation.resumeWith(result)
    }

}

val threadPool by lazy {
    Executors.newCachedThreadPool()!!
}

class AsyncTask{
    fun execute(task: () -> Unit) {
        threadPool.execute(task)
    }
}

class Log {
    companion object {
        val logEx = ::log.compose("Coroutine!")
        private fun log(tag: String, info: Any) {
            println("[$tag-${formatTime()}-${Thread.currentThread().name}] $info")
        }

        private fun <P1, P2, R> Function2<P1, P2, R>.compose(p1: P1) = fun(p2: P2) = this(p1, p2)

        private fun formatTime()=
            SimpleDateFormat("yyyy-MM-dd:HH:mm:ss.SSS").format(System.currentTimeMillis())
    }
}