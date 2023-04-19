package com.example.administrator.kotlindemo.tools

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import javax.xml.namespace.NamespaceContext
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * 1.协程的重要性：
 * 简化异步、并发编程、优化软件架构
 * 2.什么是协程：
 * 相互协作的程序
 * 3.如何启动协议：
 * launch ->射箭，不需要目标，射出去就不用管了，返回job对象
 * async ->钓鱼，有返回值，await即结果
 * runBlocking ->阻塞式
 * 4.suspend
 * 本质：
 * Callback
 * 原理：
 * CPS+状态机
 * https://www.jianshu.com/p/4e5d6b2f3730
 * 5.Job生命周期
 * 1.状态：
 * 对外暴露的只有3个：
 *  Active,Completed,Canceled
 * 对内的有6个
 *  New,Active,Completed,Canceled,Completing,Cancelling
 *  [New]:launch(start = CoroutineStart.LAZY) 懒加载方式启动，job调用start之前是New状态
 *  某个job运行完毕，不会立刻Completed，而是先Completing（时间很短），再Completed
 *  job.cancel同理
 *  2.高级api：
 *  job.invokeOnCompletion{} // 协程结束以后就会调用这里的代码
 *  job.join() // 等待协程执行完毕，类似线程的join，相当于插队执行，执行完了再切换到被查的协程。
 *
 *  3.async的返回值是一个特殊的job -> Deferred也是继承于job，他多了一个 await方法，有返回值，可非阻塞等待结果返回！
 *
 *  4.结构化并发
 *  是 Kotlin 协程的第二大优势，第一是：非阻塞式等待suspend
 *  定义：带有结构和层级的并发
 *  job = launch {
 *      job1 = launch{}
 *      job2 = launch{}
 *      job3 = launch{}
 *  }
 *  job和job1,job2,job3都是一个对象
 *  结构化并发场景：async实现多个等待结果
 *
 *  Job，相当于协程的句柄，Job 与协程的关系，有点像“遥控器与空调的关系”,获取状态，改变协程状态
 *
 * 那么结构化并发带来的最大优势就在于，我们可以实现只控制“父协程”，从而达到控制一堆子协程的目的。在前面的例子中，parentJob.join() 不仅仅只会等待它自身执行完毕，还会等待它内部的 job1、job2、job3 执行完毕。parentJob.cancel() 同理
 *
 * 思考一个问题：函数的顶层协程域切换为main是不是就不会存在并发问题了？？？
 *
 * Created by WangMaoBo.
 * Date: 2023-02-22
 */
suspend fun main() {
    runBlocking {
        withContext(Dispatchers.IO) {
            println("ThreadName is ${Thread.currentThread().name}")
            val job = launch { // 与上个的线程类型保持一致，但是并不是一个线程，比如上面是worker-2，下边是worker-1
                println("ThreadName is ${Thread.currentThread().name}")
            }
            job.children.forEach {

            }
        }
    }
    GlobalScope.launch {
        delay(100)
        println("1")
    }
    println("2")
    delay(1000)
    val mySingleDispatcher = Executors.newSingleThreadExecutor {
        Thread(it, "MySingleThread").apply { isDaemon = true }
    }.asCoroutineDispatcher()
    CoroutineScope(Job() +mySingleDispatcher).launch {
        // key:[CoroutineName]、[ContinuationInterceptor]、[Job]、[CoroutineExceptionHandler]
        // 如何获取携程上下文，一共四个。参考map的用法
        val same = coroutineContext[ContinuationInterceptor] == mySingleDispatcher
        println("isSame $same")
    }
}
