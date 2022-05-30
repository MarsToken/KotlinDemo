package com.example.administrator.kotlindemo.jetpack.introduction.model.event

import android.view.View

/**
 * Created by tellerWang on 2021/6/30.
 */
class OnClickListener {
    //v: View 怎么传递？
    fun onClick(v: View) {
        println("send request!")
    }
}