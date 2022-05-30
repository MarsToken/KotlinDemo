package com.example.administrator.kotlindemo.inline

/**
 * 资料：
 * 1.https://juejin.cn/post/6844904201353429006
 * 重学 Kotlin —— inline，包治百病的性能良药
 * 2.官方文档：
 * https://www.kotlincn.net/docs/reference/inline-functions.html#%E5%85%B7%E4%BD%93%E5%8C%96%E7%9A%84%E7%B1%BB%E5%9E%8B%E5%8F%82%E6%95%B0
 * 为什么用：
 * 1.节省匿名内部类的创建，2.节省帧栈的创建（这个影响会乎其微，主要是第一点，会飘黄
 * 2.主要就是为了拯救lambda表达式
 * 概述：
 *
 * 1. 默认情况下， Hotspot 不会内联大于35个字节的方法
 * 2.inline 关键字应该只用在需要内联特性的函数中，比如高阶函数作为参数和具体化的类型参数时
 *  2.1什么叫具体化的类型参数 reified
 *      有时候我们需要访问一个作为参数传给我们的一个类型
 *      本质上是类型，是一个作为参数传给我们的类型 Class<T>
 *      可以用 reified 代替：
 *          reified 修饰后 ，可以作为普通的类 使用 ，比如 is T
 *
 * 3.如果lambda表达式是作为 成员变量，则内敛无意义
 * 4.内敛函数两个参数及以上时，可以制定某个内敛，某个不内敛 noline
 * 5. 内联函数可以直接返回return，返回的是最外层函数
 *  一个 internal 声明可以由 @PublishedApi 标注，这会允许它在公有 API 内联函数中使用。
 *  当一个 internal 内联函数标记有 @PublishedApi 时，也会像公有函数一样检测其函数体
 *  '''
 *      fun foo() {
inlined {
return // OK：该 lambda 表达式是内联的
}
}
 *  '''
 *  6.crossline -本质是 解决 return
 *  一些内联函数可能调用传给它们的不是直接来自函数体、而是来自另一个执行上下文的 lambda 表达式参数，
 *  例如来自局部对象或嵌套函数。在这种情况下，该 lambda 表达式中也不允许非局部控制流。为了标识这种情况，
 *  该 lambda 表达式参数需要用 crossinline 修饰符标记
 *  什么时候用？
 *      需要突破内敛函数的“不能间接调用参数”的限制时，不需要判断，因为编译器会飘红，提示，与noinline一样
 *
 *  7.公有 API 内联函数体内不允许使用非公有声明，即，不允许使用 private 与 internal 声明以及其部件。
 *
 * Created by WangMaoBo.
 * Date: 2022/5/30
 */
class InLineTest {

    inline fun click(block: () -> Unit) {
        block.invoke()
    }

    fun notInline(block: () -> Unit) {
        block.invoke()
    }

    inline fun withInlineAndNo(hasInline: () -> Unit, noinline notHasInLine: () -> Unit) {
        hasInline.invoke()
        notHasInLine.invoke()
    }

    // 具体化的参数类型
    inline fun <reified T> TreeNode<T>.getTarget(): T? {
        var p = parent
        while (p != null && p !is T) {
            p = p.parent
        }
        return p as T?
    }

    // 常规
    fun <T> TreeNode<T>.getTargets(clazz: Class<T>): T? {
        var p = parent
        while (p != null && !clazz.isInstance(p)) {
            p.parent
        }
        return p as T?
    }

    // crossline 要传入的lambda表达式可能会被函数体内非lambda表达式调用，这样，return只能return到runOnUiThread了，无法控制 testCrossLine了。
    inline fun testCrossLine(crossinline postAction: () -> Unit) {
        runOnUiThread {
            postAction()
            // return //kotlin编译无法通过，内敛函数里被crossline修饰的函数类型的参数（实际上是lambda），将不在享有“lambda表达式可以使用return的权利”
            // 言外之意：间接调用和lambda的return只能二选一
        }
    }

    fun runOnUiThread(callback: () -> Unit) {
        callback.invoke()
    }

}

fun main() {
//    var pool: () -> Unit = {
//        println("I am pool.")
//    }
    InLineTest().apply {
//        click {
//            println("I am inline")
//        }
//        notInline{
//            println("I am not inline")
//        }
        withInlineAndNo({
            println("has in line")
            return
        }, {
            println("not has in line")
            return@withInlineAndNo
        })

    }
}

class TreeNode<T> {
    var parent: TreeNode<T>? = null
}