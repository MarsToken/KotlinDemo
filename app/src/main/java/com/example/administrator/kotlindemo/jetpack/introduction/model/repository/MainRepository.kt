package com.example.administrator.kotlindemo.jetpack.introduction.model.repository

import android.arch.lifecycle.MutableLiveData
import com.example.administrator.kotlindemo.jetpack.introduction.model.bean.ResultBean

/**
 * Created by tellerWang on 2021/6/30.
 */
class MainRepository {
    private lateinit var mMutableLiveData: MutableLiveData<ResultBean>

    fun getData():MutableLiveData<ResultBean>{
        if (null == mMutableLiveData) {
            mMutableLiveData = MutableLiveData()
        }
        val resultBean = ResultBean("url","content")
        mMutableLiveData.value = resultBean
        return mMutableLiveData
    }

}