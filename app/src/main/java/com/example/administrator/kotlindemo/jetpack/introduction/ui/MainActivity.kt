package com.example.administrator.kotlindemo.jetpack.introduction.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.administrator.kotlindemo.R
import com.example.administrator.kotlindemo.utils.LogUtil
import kotlinx.coroutines.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

val log = LogUtil.log(MainActivity::class.qualifiedName!!)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = generateTextView()
        val imageView = generateImageView()
        setContentView(imageView)

        // showImage()
        //startActivity(Intent(this, HandlerActivity::class.java))
    }

    private fun generateImageView(): ImageView {
        val imageView = ImageView(this)
//        imageView.setImageDrawable(getDrawable(R.mipmap.new1).apply {
//            println("MainActivity drawable is $this")
//        })
//        Handler().postDelayed(Runnable {
//            imageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher_round))
//        }, 2000)
//        println("MainActivity is ${imageView.drawable}")

        var drawable: Drawable? by imageView
        drawable = getDrawable(R.mipmap.new1)
        println("MainActivity drawable is $drawable")
        Handler().postDelayed(Runnable {
            drawable = getDrawable(R.mipmap.ic_launcher_round)
        }, 2000)
        return imageView
    }

    private operator fun ImageView.provideDelegate(value: Any?, property: KProperty<*>) =
        object : ReadWriteProperty<Any?, Drawable?> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Drawable? {
                println("MainActivity getValue $value")
                return drawable
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: Drawable?) {
                println("MainActivity setValue $value")
                setImageDrawable(value)
            }

        }


    private fun generateTextView(): TextView {
        val textView = TextView(this)
        textView.text = "hello"
        var message: String? by textView
        Handler().postDelayed(
            Runnable {
                message = "hehe"

            }, 2000
        )
        return textView
    }


    private operator fun TextView.provideDelegate(value: Any?, property: KProperty<*>) =
        object : ReadWriteProperty<Any?, String?> {
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
