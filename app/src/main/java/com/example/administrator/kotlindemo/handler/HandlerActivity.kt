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

        // 异步消息为什么handler为null？？


    }

    /**
     * 结论：提高消息优先级的方式 1.postAtFrontOfQueue
     */
    private lateinit var mHandler: Handler
    private val mNormalHandler: Handler = Handler()
    fun onClick(view: View) {
        if (view.id == R.id.btnTestAsynchronous) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mHandler = Handler.createAsync(Looper.getMainLooper()) // 也是第 11个执行，因为没有 将其他消息屏蔽掉！可能会导致anr
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
//                    }, 10)
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
        } else if (view.id == R.id.btnTestIdleHandler) {

        }
    }

}