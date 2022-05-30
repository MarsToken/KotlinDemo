package com.example.administrator.kotlindemo.lesson6.coRountine3

import com.example.administrator.kotlindemo.lesson6.coRountine1.UIContinuationWrapper
import com.example.administrator.kotlindemo.utils.LogUtil
import com.example.kotlin.start.test.AsyncTask
import kotlin.coroutines.*

/**
 * 封装 拦截器 Continuation
 * Created by Administrator on 2021/6/23.
 */
private val mMessage = Message(Status.BLOCK,null)
private val log = LogUtil.log("CoRoutine3")

fun main() {
    prepare()
    loop()
}
private fun prepare() {
    startCoRoutine {
        log("刚进入协程")
        val byteArray = downloadImage("http:xxx.st.png")
        log("==刚进入协程===")
        mMessage.any = byteArray
        mMessage.status = Status.AWAIT
        log("马上退出协程")
    }
}
private fun startCoRoutine(block: suspend () -> Unit) {
    block.startCoroutine(AsyncContinuation(AsyncContext()))
}
private suspend fun downloadImage(url: String): ByteArray = suspendCoroutine { continuation ->
    AsyncTask().execute{
        log("开始下载图片:$url")
        Thread.sleep(1000)
        log("图片下载完成")
        val content = "content is picture".toByteArray()
        if (content.isNotEmpty()) {
            content.let(continuation::resume)
        } else {
            continuation.resumeWithException(Exception())
        }
    }

}

private fun loop() {
    while (true) {
        if (mMessage.status != Status.BLOCK) {
            if (mMessage.status == Status.AWAIT) {
                if (mMessage.any is ByteArray) {
                    log("加载图片UI")
                    log(String((mMessage.any as ByteArray)))
                    mMessage.status = Status.BLOCK
                }
            }
        }
        Thread.sleep(1)
    }
}
data class Message(var status: Status, var any: Any?)
enum class Status{
    BLOCK,AWAIT
}
class AsyncContinuation(coroutineContext: CoroutineContext):Continuation<Unit>{
    override val context: CoroutineContext = coroutineContext

    override fun resumeWith(result: Result<Unit>) {

    }
}
//伴生对象
class AsyncContext:AbstractCoroutineContextElement(ContinuationInterceptor),ContinuationInterceptor{
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return UIContinuationWrapper(continuation.context.fold(continuation) { continuation, element ->
            if (element != this && element is ContinuationInterceptor) {
                element.interceptContinuation(continuation)
            } else continuation
        })
    }

}
