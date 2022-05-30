package com.example.administrator.kotlindemo.lesson5

/**
 * 尾递归，直接返回递归自己，后续没有任何操作
 * tailrec 关键字提示编译器优化尾递归为迭代
 * 尾递归：逻辑清晰
 * 迭代：执行效率高
 * 10 0000 没有 tailrec 会 stackoverflow
 * Created by tellerWang on 2021/6/22.
 */
const val MAX_COUNT = 1000000
fun main() {
    val head = ListNode(0)
    var p = head
    for (i in 1..MAX_COUNT) {
        p.next = ListNode(i)
        p = p.next!!
    }
    println("${search(head,80000)?.value}")

}
//没有 tailrec 会报 stackOverflow
tailrec fun search(head: ListNode?, value: Int): ListNode? {
    head ?:return null
    if(value==head.value) return head
    return search(head.next, value)
}
data class ListNode(val value: Int, var next: ListNode? = null)



















//const val MAX_COUNT = 10
//fun main() {
//    val head = ListNode(0)
//    var p = head
//    for (i in 1..MAX_COUNT) {
//        p.next = ListNode(i)
//        p = p.next!!
//    }
//    println("${search(head,8)}")
//}
//tailrec fun search(node: ListNode?, value: Int): ListNode? {
//    node?:return null
//    if (node.value == value) return node
//    return search(node.next, value)
//}
//
//data class ListNode(val value: Int, var next: ListNode? = null)


