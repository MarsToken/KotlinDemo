package com.example.administrator.kotlindemo.tools

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.administrator.kotlindemo.R
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 运算符重载加属性代理（provideDelegate 相当于 by）实现数据双向绑定举例
 * TextView与String绑定，ImageView与drawable
 * @author WangMaoBo
 * @since 2023/2/16
 */

fun main(context: Context) {
    val textView = TextView(context)
    var message: String? by textView
    textView.text = "hello"
    Thread.sleep(100)
    message = "so go"


    val imageView = ImageView(context)
    var drawable: Drawable? by imageView
    drawable = context.getDrawable(R.mipmap.new1)
    Handler().postDelayed(Runnable {
        drawable = context.getDrawable(R.mipmap.ic_launcher_round)
    }, 2000)
}

/**
 * 被代理的“String?”，即“message” 与 代理对象textview的text属性的 get set完全绑定了
 */
operator fun TextView.provideDelegate(value: Any?, property: KProperty<*>) =
    object : ReadWriteProperty<Any?, String?> { // String? 为被代理的对象的类型
        override fun getValue(thisRef: Any?, property: KProperty<*>): String? {
            return text.toString()
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
            text = value
        }
    }

operator fun ImageView.provideDelegate(value: Any?, property: KProperty<*>) =
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