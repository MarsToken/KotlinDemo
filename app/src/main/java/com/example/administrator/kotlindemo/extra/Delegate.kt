package com.example.administrator.kotlindemo.extra

/**
 * Created by WangMaoBo.
 * Date: 2023-02-15
 */
class Delegate {

    // 1.属性委托
    var count:Int = 100
    // kotlin1.4 “::count”是属性的引用，它跟我们前面学过的函数引用是一样的概念
    var total:Int by ::count
    // 意味着，两者的get,set完全一致
    // 使用场景，比如，server端不统一，version1,用 count，version2用total

    // 2.懒加载，注意此场景下 by lazy{} 内部的代码只会走一次，底层lazy是个高阶函数
    // 默认为线程安全的委托

    // 3.Delegates.observable

}
