//package com.example.administrator.kotlindemo.tools
//
///**
// * 泛型的理解及应用
// *  真实对象 （抽象）-> 程序 （抽象）-> 泛型
// *
// * 不变&型变（协变&逆变）&星投影
// *
// * 总结：一句话 ->
// *  协变：out，只读取（返回值）不能写入
// *  逆变：in，只写入（形式参数）不能读取
// * 特例：语法层面、注解忽略、权限private修饰 下边有详细介绍
// *
// * 源码：协变：迭代器Iterator<out T> 逆变：Comparable<in T>
// *
// * @author WangMaoBo
// * @since 2023/2/17
// */
//
//fun main() {
//    // Controller<TV>().turnOn(XiaoMi1(""))
//    // 泛型的不变性
//    val tvList = mutableListOf<TV>()
//    val xiaoMi1List = mutableListOf<XiaoMi1>()
//    // tvList与xiaoMi1List 没有任何关系，泛型是不变的
//    // tvList = xiaoMi1List 报错，两者不等价
//    // xiaoMi1List.addAll(tvList) 报错
//    // 可以编译通过，这个是理解的基础，有了这个基础继续往下看！
//    tvList.addAll(xiaoMi1List) // ⑩
//    // 不变性理解：为什么？不能阻止foo1，foo2内部做危险操作
//    // 集合没任何关系，子元素的in、out走继承关系！
//    foo1(mutableListOf<XiaoMi1>(XiaoMi1(""))) // 编译报错，因为①处将要添加XiaoMi2，而如果此处被允许， ①处必然报错，相当于让XiaoMi1的集合添加XiaoMi2元素
//    foo2(mutableListOf<TV>(TV())) // 编译报错，因为②处将要访问XiaoMi2对象，而如果此处被允许，②处必然报错,相当于把TV赋值给了XiaoMi2的引用
//
//    // 用逆变优化foo2改为foo22，但是禁止对应泛型的读out
//    foo22(mutableListOf<TV>(TV("")))
//    foo22(mutableListOf<Any>(Any())) // ③
//    // foo22(mutableListOf(XiaoMi1(""))) // ④ 疑问！XiaoMi1 不是 XiaoMi2 的父类
//
//    // 用协变优化foo1为foo11，但是禁止对应泛型的in
//    foo11(mutableListOf<XiaoMi1>(XiaoMi1(""))) // ⑤
//
//    // 用“星号”作为泛型的实参，他一般表示 Any? ，但是它未必一定代表 Any? 取决于 "具体的类对泛型的声明！！！"
//    val foodAny: Any? = findRestaurant().orderFood() // 返回值为T是任意的Any?
//    val food: Food? = findFoodRestaurant().orderFood() // 返回值为T是Food或其子类
//
//    // 特例：语法层面、注解忽略、权限private修饰
//    // 1.特例协变 ⑥ out修饰的data，由于被val修饰，所以它是个成员变量，不是参数，且它是个常量，有get方法，只能被读 out ，所以被out修饰
//    /**
//     * 2.特例协变 Kotlin官方的[List]<out E> 集合，E既有输入也有输出，但是可以在输入参数的地方加@[UnsafeVariance]注解，比如[List.contains]方法
//     * 对于 [List.contains] 这样的方法，它们虽然以 E 作为参数类型，但本质上并没有产生写入的行为
//     */
//    // 3.特例协变&逆变 权限操作符 private会遮掉任何in out修饰，因为P,T是由外界传进来的，内部无法生成，只要保证外界无权访问即可，get，set都是私有的，比如⑦、⑧ 去掉private 会报错！
//
//    //逆变实战：买遥控器
////    buyController(Controller<XiaoMi1>()) // 没问题
////    buyController(Controller<TV>())
//}
//
//abstract class BaseSingleton<in P, out T> {
//    private var instance: T? = null // ⑦
//    private var instance2: P? = null // ⑧
//
//    fun test() {
//        instance.toString()
//        instance2.toString()
//    }
//
//    fun getInstance2():P? { // ⑧ 可以是因为private修饰
//        return instance2
//    }
//
//    fun setInstance(t:T?) { // ⑦ 可以是因为private修饰
//        this.instance = t
//    }
//
//}
//
//class Success<out T>(val data: T) // ⑥
//class Success1<out T>(var data: T) // ⑦ 因为data有get，set方法，违背了out只读的特性
//
////==============================================
//fun findRestaurant(): Restaurant<*> {
//    return Restaurant<String>()
//}
//
//class Restaurant<out T> {
//    fun orderFood(): T? {
//        return null
//    }
//}
////==============================================
//
////==============================================
//fun findFoodRestaurant(): FoodRestaurant<*> {
//    return FoodRestaurant<Food>()
//}
//
//class FoodRestaurant<out T : Food> {
//    fun orderFood(): T? {
//        return null
//    }
//}
//
//class Food
////==============================================
//
////==============================================
///**
// * 1.协变优化，out所修饰的泛型 只能 读 out，不能 写 in
// * 2.代码角度 继承依赖正常：MutableList<XiaoMi1> 依赖于 MutableList<TV>
// * 3.out 可作用在方法参数里，叫“使用处协变”；也可以作用到源代码类上，叫“声明式协变” Java 当中是没有声明处型变的。Java 里面只有使用处型变
// * 4.为什么禁止 写入，因为 实际参数的写入是随机的，只要是TV及其子类即可，比如⑤，所以 foo11 函数体内部 add写入就不确定了，语言层面无法确认list的泛型具体的类型，但是有一点是确定的，它肯定是TV类型，所以只能读为TV类型
// */
//fun foo11(list: MutableList<out TV>) {
//    list.add(XiaoMi2(""))
//    val tv: TV = list[0]
//}
//
//fun foo1(list: MutableList<TV>) {
//    list.add(XiaoMi2("")) // ① 可以添加其子类XiaoMi2,相当于⑩
//    val tv: TV = list[0]
//}
////==============================================
//
////==============================================
///**
// * 1.逆变优化 in 所修饰的泛型 只能写 in，不能读 out，
// * 2.代码角度继承依赖颠倒了：MutableList<TV> 依赖于 MutableList<XiaoMi2>了
// * 3.in 可作用在方法参数里，叫“使用处逆变”；也可以作用到源代码类上，叫“声明式逆变” Java 当中是没有声明处型变的。Java 里面只有使用处型变
// * 4.为什么禁止读，因为实际参数的写入是随机的，只要是XiaoMi2及其父类即可，比如③，所以读就不确定了！站在foo22内部，语言层面无法确认list的泛型具体的类型
// */
//fun foo22(list: MutableList<in XiaoMi2>) {
//    list.add(XiaoMi2(""))
//    // 报错了，上面的第四条描述
//    val xiaoMi2: XiaoMi2 = list[0]
//}
//
//fun foo2(list: MutableList<XiaoMi2>) {
//    list.add(XiaoMi2(""))
//    val xiaoMi2: XiaoMi2 = list[0] // ② 访问自己的元素XiaoMi2
//}
////==============================================
//
////==============================================
//fun buyController(controller: Controller<XiaoMi1>) {
//    println("tv is $controller")
//    val xiaoMi1 = XiaoMi1("")
//    controller.turnOn(xiaoMi1)
//}
//
//// 遥控器
//class Controller<T> {
//    fun turnOn(tv: T) {
//        println("tv is $tv")
//    }
//}
////==============================================
//
//class XiaoMi1(name: String) : TV(name)
//class XiaoMi2(name: String) : TV(name)
//
//open class TV(val name: String) {
//    override fun toString(): String {
//        return "name is $name"
//    }
//}