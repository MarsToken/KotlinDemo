package com.example.administrator.kotlindemo.jetpack.introduction.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.administrator.kotlindemo.handler.HandlerActivity
import com.example.administrator.kotlindemo.utils.LogUtil
import kotlinx.coroutines.*

val log = LogUtil.log(MainActivity::class.qualifiedName!!)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this))
        // showImage()
        startActivity(Intent(this, HandlerActivity::class.java))
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
