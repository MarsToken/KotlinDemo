package com.example.administrator.kotlindemo.tools

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * 1.属性代理，ViewModel里的Livedata写法，或者集合的写法(保护限制IO)
 *
 * @author WangMaoBo
 * @since 2023/2/17
 */

fun main() {
    val viewModel = ViewModel()
    viewModel.sendRequest()
    println(viewModel.liveData.value)
    // viewModel.liveData.value = "2" 编译报错


}

class ViewModel {
    /**
     * 私有变量，可读写，但是外界无法访问
     */
    private val _liveData: MutableLiveData<Int> = MutableLiveData()

    /**
     *
     * 对外暴露的LiveData,只允许读getValue，不允许写setValue
     * '::' 表示属性引用，'by::' 意味着liveData是个傀儡，把get，set方法全部交给了 _liveData
     */
    val liveData: LiveData<Int> by ::_liveData

    fun sendRequest() {
        _liveData.value = 1
    }
}


