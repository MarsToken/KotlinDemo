package com.example.administrator.kotlindemo.tools

/**
 * 本地数据库版本升级，改变字段
 *
 * @author WangMaoBo
 * @since 2023/2/17
 */

fun main() {
    //version2
    val item2 = Item2()
    // 鼠标选中 "item2.count" ，停留片刻，会有个菜单 第二行：Replace with total，点击自动替换
    println("${item2.count}")
}

// version1
class Item {
    // version1 赋值给 count
    var count: Int = 0
}

// version2
class Item2 {
    // version1 继续使用count，但是数据来源是total
    @Deprecated("count命名不规范，在version2及以上版本已被废弃", ReplaceWith("total"),DeprecationLevel.WARNING)
    var count: Int by ::total

    // version2 使用total
    var total: Int = 0
}