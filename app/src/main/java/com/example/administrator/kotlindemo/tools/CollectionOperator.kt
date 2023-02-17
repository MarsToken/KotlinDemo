package com.example.administrator.kotlindemo.tools

import android.widget.TextView

/**
 * 运算符重载，或者自定义运算符（中缀表达式），场景：判断TextView的值和现有的是否一样？
 * Kotlin官方规定的运算符：
 * https://www.kotlincn.net/docs/reference/operator-overloading.html
 * @author WangMaoBo
 * @since 2023/2/16
 */


fun main() {
    val textView = TextView(null)
    val text = "hello"
    if (!(textView same text)) {
        textView.text = text
    }
}

infix fun TextView.same(newValue: String): Boolean {
    return text == newValue
}





