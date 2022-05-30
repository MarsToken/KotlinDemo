package com.example.administrator.kotlindemo.lesson6

import java.util.concurrent.Executors

/**
 * Created by Administrator on 2021/6/23.
 */
private val pool by lazy{
    Executors.newCachedThreadPool()
}

class AsyncTask(private val block: () -> Unit){
    fun execute() = pool.execute(block)
}
