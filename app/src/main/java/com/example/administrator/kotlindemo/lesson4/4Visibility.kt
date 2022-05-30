package com.example.administrator.kotlindemo.lesson4

/**
 * 默认为public            public
 * internal:module之内可见 java没有
 * protected              protected
 * private                private
 * --kotlin没有            default(包内可见)
 * Created by Administrator on 2021/6/20.
 */
fun main(args: Array<String>) {
    val countryard = Countryard()
    val forbiddenCity = ForbiddenCity()
    forbiddenCity.flowers
}
class House
class Flower
class Countryard{
    private val house: House = House()
    private val flower = Flower()
}
class ForbiddenCity{
    val housees = arrayOf(House(), House())
    val flowers = arrayOf(Flower(), Flower())
}