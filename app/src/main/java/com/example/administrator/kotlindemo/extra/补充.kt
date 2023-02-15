package com.example.administrator.kotlindemo.extra

/**
 * Created by WangMaoBo.
 * Date: 2023-01-31
 */

fun main() {
    // 1.kotlin里没有基本类型，完全面向对象 Int：语法层面这样，但是实际上，通过反编译为java可知，不是这样的，比如
    // val a:Int = 1, a为int val b:Int?=null,b为Integer。即：为空时为基本类型，不为空则为对象类型！
    // 2.kotlin 不能进行隐式转换
    // val intValue: Int = 10
    // val longValue :Long = intValue // wrong 因为本来就不是一个类型，严谨性，且存在精度损失
    /**3.被getter方法修饰的变量是不存在的 [TestSetter]*/
    // 4.内部类默认为静态的，和java相反，可以默认静态内部类不会持有外部类的引用，所以可以防止内存泄漏
    // 5.默认变量和类都是final的，也和java相反，可有效避免继承泛滥
    // 6.data 类 有 copy方法，可修改参数，深copy？
    // 7.枚举类，只有一个单例对象，如果有确定个数的需求，比如ocr or asr，可用枚举构造函数传递 type 详情见 13SealedClass
    // 8.密封类，对同一个类型可以生成多个对象，且内部有个私有构造函数，只能内部用，不可被继承，可以有效的保护指令集 13SealedClass
    // 9.kotlin里的接口的成员变量本质上是个方法，而默认方法：是个静态内部类里的静态同名方法实现的！这个变量在子类才会真正被定义
    // 10.company object 工厂方法模式
    // 11.拓展函数（属性）的应用场景 1.主动：核心业务与拓展分离，架构层面 2.被动：提供模板方法
    // 12.高阶函数 SAM(Single Abstract Method)转换 Lambda表达式 可代替接口，且通过inline更高效！
    // 13.T.() 待接收者的参数类型，语法上等同于 类里的成员函数
    // 14.函数式风格 不要用java思维写kotlin代码，杜绝命令式风格
    // 15.inline 平移代码 ，不使用inline会 1.函数调用多一次 2.高阶函数作为参数，则会生成一个额外的匿名内部类
    // Kotlin 官方只建议我们将 inline 用于修饰高阶函数，滥用不会提升性能，且会有警告
    // 所以总结：如何提高性能呢？尽量用高阶函数代替接口，且被调用的高阶函数用inline修饰
    // 16.操作符自定义
    // 17.委托的四种方式 两个属性之间的委托，by lazy,Delegates.observable,by map,还有自定义属性代理对象 by XXX 9PropertyProxy
    // 自定义委托也可以通过：借助 Kotlin 提供的 ReadWriteProperty（val）、ReadOnlyProperty(var) 这两个接口，来自定义委托
    // 场景1：属性封装，livedata  val 不可变 by ::_可变livedata
    // 场景2：数据双向绑定！！！operate provideDelegate 操作符！=>基础能力库！！composeUI声明式UI缩影，ui会变吗？
    // 18.泛型的不可变性，逆变和协变，星投影 * -> Any?
    /**
        open class Animal()
        class Dog : Animal()
        class Cat : Animal()
        // 一旦限定为List<out Animal>,则泛型只能获取不能赋值add了！！！
        fun foo(list: MutableList<out Animal>) {
            list.add(Dog()) // 报错，这是赋值
            list.add(Cat()) // 报错，这是赋值
            val dog = list[0] // 不报错
            val animal: Animal = list[0] // 不报错
        }
     */


}

// age为var可变 ！！！适用于
class TestSetter(var age: Int) { //反编译为java isAdult是个方法
    // 不存在变量，外部age变动时，会实时更新age再比较
    val isAdult: Boolean
        get() = age >= 18
    // 不能这样写！！！（var,且用到了age）存在变量，为final类型，外部age变动时，不会实时更新age，而是age <=12 在构造函数里就确定了
    //一句话，无set，必须声明变量，又因为val，所以是个final常量，所以同一个对象TestSetter，只在构造函数里赋值age一次了。
    val isChild: Boolean = age <= 12
}

/**
 * why?
 * 2.1但实际上，这种代码是无法正常工作的。由于它牵涉到 Kotlin 的原理，你可以在学完下一节“Kotlin 原理”之后，再回过头来看看这段代码为什么有问题。
 */
