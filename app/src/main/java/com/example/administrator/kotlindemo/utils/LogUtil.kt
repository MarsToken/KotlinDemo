package com.example.administrator.kotlindemo.utils

import java.io.OutputStream
import java.text.SimpleDateFormat

/**
 * Created by tellerWang on 2021/6/22.
 */
class LogUtil {
    companion object{
        val log = ::logPartial.partial1(System.out)
        //val test = log("xxx")
        private fun logPartial(info: Any, tag: String, target: OutputStream) {
            target.write("\"[$tag-${Thread.currentThread().name}-${getTime()}] $info\n".toByteArray())
        }
        private fun getTime(): String = SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis())
        private fun<P1,P2,P3,R>Function3<P1,P2,P3,R>.partial2(p2:P2,p3:P3)= fun(p1: P1) = this(p1, p2, p3)
        private fun<P1,P2,P3,R>Function3<P1,P2,P3,R>.partial1(p3:P3)= fun(p2: P2)= fun(p1: P1) = this(p1, p2, p3)
    }

}

fun main() {
    //非伴生对象调用
//    val logUtil = LogUtil()
//    logUtil.log("Hello,I am partial function's usage!")
    //::调用的是函数的引用,所有涉及到对象里的函数（方法）引用的无法通过::调用具体函数
    //伴生对象调用
    LogUtil.log("Hello,I am partial function's usage!")
}
