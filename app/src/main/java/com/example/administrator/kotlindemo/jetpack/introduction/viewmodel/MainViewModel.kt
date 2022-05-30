package com.example.administrator.kotlindemo.jetpack.introduction.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.administrator.kotlindemo.jetpack.introduction.model.bean.ResultBean
import com.example.administrator.kotlindemo.jetpack.introduction.model.repository.MainRepository
import com.example.administrator.kotlindemo.utils.LogUtil

/**
 * Created by tellerWang on 2021/6/30.
 */
val log = LogUtil.log("MainViewModel")

class MainViewModel(private val mRepository: MainRepository = MainRepository()) : ViewModel() {

    fun getData() = mRepository.getData()

    fun getData4Content():LiveData<String>{
        val liveData = mRepository.getData()
        return Transformations.map(liveData as LiveData<ResultBean>) { resultBean ->
            resultBean.content
        }
    }

    override fun onCleared() {
        super.onCleared()
        log("${javaClass.canonicalName} onCleared!")
    }

    //使用newInstance反射实例ViewModel，并且传出去
    class MainFactory(private val tag:String): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            log("$tag factory create!")
            return modelClass.getConstructor(String.javaClass).newInstance(tag)
        }

    }
}