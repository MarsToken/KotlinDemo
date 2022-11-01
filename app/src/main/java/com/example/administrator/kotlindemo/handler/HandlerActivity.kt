package com.example.administrator.kotlindemo.handler

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.administrator.kotlindemo.R

class HandlerActivity : AppCompatActivity() {
    private val TAG = "HandlerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        // 疑问1：此方法的作用，流程图可以借鉴。
        // Handler().sendMessageAtFrontOfQueue()

        // 用链表实现的队列（网络上有很多误区，说 MessageQueue是个骗子，它明明是个单向链表，但是命名成了队列。google工程师不会犯这么低级的错误呀。）
        // 疑问2：prepare（allowQuit）的allowQuit的含义

        // 此方法不能被调用，因为ActivityThread里已经被调用过了，否则会抛异常，The main Looper has already been prepared.
        // 必须先prepare，再new handler(looper:Looper),因为prepare里，threadlocal.set()，一个map将 key：线程与value：looper绑定，而创建handler，需要获取这个looper对象的。
        // 设计的不好.只是在注释里加了说明
        // Looper.prepareMainLooper()

        // handler.dispatchMessage：1.Message的 callback 2.handler的callback的handleMessage 3.自己的handleMessage

        // 界面卡顿的原因：未必是有一个message耗时，也有可能是多个message小而多！死循环handler.post():暂时不用说了。
        // 享元模式-查看我写的blog - 附上连接。
        // api 28.android 10
        // Handler.createAsync(Looper.getMainLooper()) 有几个构造函数，28以上才可以，以下都无法调用

        // 疑问3：idelHandler 和 同步消息屏障
        // synchronous为同步的，asynchronous为异步的，构造函数传递的是是否为异步消息，而
        // 同步消息屏障也是为了获取异步消息 MessageQueue 的 next里 while (msg != null && !msg.isAsynchronous());

        // nativePollOnce loop没有要立刻处理的消息时（可能有延时消息） messageQueue的next方法 阻塞，mBlocked设置为true，MessageQueue里的 enqueueMessage
        //1.（队列为空，消息无需延时或消息执行时间比队列头部消息早) && (线程处于挂起状态时（mBlocked = true）)
        //2.【线程挂起（mBlocked = true）&& 消息循环处于同步屏障状态】，这时如果插入的是一个异步消息，则需要唤醒。
        // 激活


        // looper里：if (msg == null) {
        //                // No message indicates that the message queue is quitting.
        //                return;
        //            }
        // 问题2里字段为true时，调用 Looper.quit() -> MessageQueue.quit() 才会退出loop()

        // 异步消息为什么handler为null？？同步消息屏障？

        // idleHandler 触发的条件及举例
        /**
         *  if (pendingIdleHandlerCount < 0
                && (mMessages == null || now < mMessages.when)) {
         */
        //1.当当前消息队列（为什么是队列呢，因为他就是个链表，而不是单个Message,MessageQueue实际上就是插入到它内部的）mMessages为空，2.或者消息队列中有延时消息

        //loop如何退出的？ 只有设置了Looper.prepare->Looper(boolean allowQuit)->MessageQueue(boolean quitAllowed) 为 true，并且 MessageQueue的
        // next方法返回null（不代表mMessages为null）才会退出，但是主线程默认传的false，即不允许退出，一旦强制调用quit会抛异常！

        //为什么要用单向链表呢？很简单，增删快，查询正常即可
    }

    /**
     * 结论：提高消息优先级的方式 1.postAtFrontOfQueue
     */
    private lateinit var mHandler: Handler
    private val mNormalHandler: Handler = Handler()
    fun onClick(view: View) {
        if (view.id == R.id.btnTestAsynchronous) {
            // 如何让消息优先执行？
            // 方法一：同步消息插队
            // testSynchronousMessage()
            // 方法二：为异步消息发送同步屏障
            testAsynchronousMessage()
        } else if (view.id == R.id.btnTestIdleHandler) {

        }
    }


    /**
     * 1.有本质区别：一个是同步消息，一个是异步消息。
     * 2.异步消息优先级，优于同步消息，message next() 方法源码里先优选处理同步消息（需要验证）
     * 3.异步消息有“vip”权限，它可以不受when时间的影响，设置消息屏障，优先执行！
     * 4.不设置同步屏障呢？
     */

    /**
     * 如何让消息优先执行。
     *
         *   //发现为屏障消息
            if (msg != null && msg.target == null) {
            // 进入循环当中，直到成功获取异步消息
            // 所以这里要特别注意，假如只发送屏障，后续没有清除屏障就会进入死循环
                do {
                    prevMsg = msg; // 前一个消息 = 当前的屏障消息
                    msg = msg.next; // 当前消息 = 下一个消息
                } while (msg != null && !msg.isAsynchronous());
            }
            // 根据MessageQueue里的源码，只有满足屏障消息后一个消息为null（则会nativePollOnce）或它为异步消息时才退出，并交个looper处理下个异步消息
            // 直到mMessages遍历完毕！

    当设置了同步屏障之后，next函数将会忽略所有的同步消息，返回异步消息。
    也就是说，如果第一条消息就是屏障，那么就往后遍历 看看有没有异步消息
    有 ：再看离这个消息触发 还有多久，设置一个超时继续休眠(可能会导致anr，要验证！)
    没有：就继续休眠（nativePollOnce），等待被别人唤醒，此时该屏障一直存在在消息队列头部
    换句话说就是，设置了同步屏障SyncBarrier之后，Handler只会处理isAsynchronous异步消息。


     * 方法二：为异步消息发送同步屏障消息
     * 步骤缺一不可！
     * 步骤1.将消息设置为异步消息 方法有三种1.构造函数，2.Handler.createAsync（P以上）3.
     * 步骤2.如何设置同步屏障，即将Message的target(Handler)设置为null，这意味着不能通过queueMessage加入到消息队列，因为它会默认将target赋值
     * 而是反射调用postSyncBarrier方法
     */
    private fun testAsynchronousMessage() {

    }

    /**
     * 如何让消息优先执行。
     * 方法一：同步消息插队，插入的when为绝对的0，不会+开机时间，而其他消息即when为0，也会+开机时间！
     */
    private fun testSynchronousMessage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 也是第 11个执行，因为消息虽然是异步，但是没有设置消息屏障（handler设置为null） 将其他消息屏蔽掉！屏障不接触。可能会导致anr
            mHandler = Handler.createAsync(Looper.getMainLooper())
            // mHandler = Handler() // 第 11个执行，如果是调用 postAtFrontOfQueue，则第一个执行，原因，插入消息队列的时候 time为真正的0，没有加系统开机时间。
        } else {
            // 反射
        }
        (0..30).forEach {
            mNormalHandler.postDelayed({
                Log.e(TAG, "$it")
            }, 0)
            if (it == 10) {
//                    mHandler.postDelayed({
//                        Log.e(TAG, "这个是异步消息")
//                    }, 0)
                mHandler.postAtFrontOfQueue {
                    Log.e(TAG, "这个是插队消息")
                }
            }
        }
        mNormalHandler.postDelayed({
            Log.e(TAG, "100")
        }, 100)
        mNormalHandler.postDelayed({
            Log.e(TAG, "200")
        }, 200)
    }

}