package com.example.administrator.kotlindemo.jetpack.introduction.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.administrator.kotlindemo.handler.HandlerActivity
import com.example.administrator.kotlindemo.utils.LogUtil
import kotlinx.coroutines.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

val log = LogUtil.log(MainActivity::class.qualifiedName!!)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "hello"
        var message: String? by textView
        Handler().postDelayed(
            Runnable {
                message = "hehe"

            }, 2000
        )
        setContentView(textView)
        // showImage()
        //startActivity(Intent(this, HandlerActivity::class.java))
    }


    operator fun TextView.provideDelegate(value: Any?, property: KProperty<*>) = object : ReadWriteProperty<Any?, String?> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): String? = text.toString()
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
            text = value
        }
    }

    private val image_url = "http://www.xxx.png"
    private fun showImage() {
        GlobalScope.launch(Dispatchers.Main) {
            log("start launch coroutine")
            val data = fetchDataFromNetwork(image_url)
            show(data)
            log("end coroutine= $CoroutineName")
        }
        log("main $CoroutineName")
    }

    private suspend fun fetchDataFromNetwork(url: String): ByteArray {
        var data = ""
        withContext(Dispatchers.IO) {
            log("start asyncTask,=$CoroutineName")
            delay(2000)
            data = "$url/content=image_content"
            log("end asyncTask,=$CoroutineName")
        }
        return data.toByteArray()
    }

    private fun show(data: ByteArray) {
        //tv_image.text = String(data)
        log("show image,content is ${String(data)}")
    }


}
