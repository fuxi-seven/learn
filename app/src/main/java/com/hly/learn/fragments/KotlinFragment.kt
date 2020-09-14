package com.hly.learn.fragments

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.hly.learn.R

class KotlinFragment : BaseFragment() {

    private var TAG = "KotlinFragment"
    private var mTv: TextView? = null
    private var mIv: ImageView? = null
    private var mSv: TextView? = null

    override fun getLayoutId(): Int {
        return R.layout.kt_layout
    }

    override fun initData(view: View) {
        val text : String = getText()
        val sum: Int = getSum(2)// 调用该函数时可不传已经设置了默认值的参数，只传无设默认值的参数
        val str: String = getStr(str = "hello")//使用参数的命名来指定值, getStr("hello")编译不过
        val user = User("seven", 32)
        val man =  Man();
        mTv = view.findViewById(R.id.kt_text_view)
        mIv = view.findViewById(R.id.kt_image_view)
        mSv = view.findViewById(R.id.kt_study_view)
        mSv?.let { it.text = "with, run, let, when, apply, interface, class等学习" }
        with(mTv) {
            this?.setOnClickListener { Toast.makeText(mContext, "点击了该TextView", Toast.LENGTH_SHORT).show() }
            this?.setText(text + sum + str + user.getName() + user.getAge() + man.getSex() + man.getName())
        }
        with(mIv) { this?.setImageDrawable(resources.getDrawable(R.drawable.ic_chuancai)) }
        //let是以闭包的形式返回，返回函数体内最后一行的值
        //let函数适应的场景：1.使用let函数处理需要针对一个可null的对象统一做判空处理；2.需要去明确一个变量所处特定的作用域范围内可以使用
        val str2 = user.let {
            val info: String = it.getName() + it.getAge()
            info
            "testLet"
        }//user? 判断user是否为null,不为null的情况下,才去执行let函数体, 需要用it代表user对象来调用方法
        Log.e(TAG, "let result is: $str2")
        //also函数的结构实际上和let很像唯一的区别就是返回值的不一样，let是以闭包的形式返回，返回函数体内最后一行的值，如果最后一行为空就返回一个Unit类型的默认值。而also函数返回的则是传入对象的本身
        val str3 = "testAlso".also {
            println(it.length)
            1000
        }
        Log.e(TAG, "also result is: $str3")
        //with适用于调用同一个类的多个方法时，可以省去类名重复，直接调用类的方法即可
        val str0 = with(man) {
            val str : String = getName() + getSex() + "with"
            str
        }
        Log.e(TAG, "with result is: $str0")
        //run函数实际上可以说是let和with两个函数的结合体，run函数只接收一个lambda函数为参数，以闭包形式返回，返回值为最后一行的值或者指定的return的表达式。
        val str1 = man.run {
            val str : String = getName() + getSex() + "run"
            str
        }
        Log.e(TAG, "run result is: $str1")
        //从结构上来看apply函数和run函数很像，唯一不同点就是它们各自返回的值不一样，run函数是以闭包形式返回最后一行代码的值，而apply函数的返回的是传入对象的本身。
        //apply一般用于一个对象实例初始化的时候，需要对对象中的属性进行赋值
        val man1 = man.apply {
            setName("Kobe")
            setSex("man")
        }
        Log.e(TAG, "apply result is: ${man1.getName()}" + ", ${man1.getSex()}")
        /*
        08-28 10:40:33.895 E/KotlinFragment(30700): let result is: testLet
        08-28 10:40:33.896 E/KotlinFragment(30700): also result is: testAlso
        08-28 10:40:33.896 E/KotlinFragment(30700): with result is: humanmanwith
        08-28 10:40:33.896 E/KotlinFragment(30700): run result is: humanmanrun
        08-28 10:40:33.896 E/KotlinFragment(30700): apply result is: Kobe, man*/
    }

    //控制流使用
    private fun method() {
        val a: Int = 1
        val b: Int = 2
        val max: Int
        val min: Int
        min = if (a < b) a else b
        max = if (a > b) a else b

        val y = 5
        when(y) {
            1 -> println("y==1")
            2 -> println("y==2")
            else -> {
                println("x !=1 && x!=2")
            }
        }

        val arrays = listOf("a","b","c")
        for (i in arrays) {
            print("$i")
        }

        var z = 2
        while (z > 0) {
            print("${z--}")
        }
    }

    private fun alphabetWith() : String {
        var stringBuilder = StringBuilder()
        return with(stringBuilder) {
            for (letter in 'A'..'Z') {
                append(letter)  //this代表stringBuilder, this.append(letter),this可以省略
            }
            append("\n Now I know the alphabet")//省略this
            toString()//省略this
        }
    }

    // method
    private fun getText() : String {
        return "Kotlin学习"
    }

    private fun getSum(int : Int, int1 : Int = 1): Int {
        return int1 + int
    }

    private fun getStr(int : Int = 1, str : String): String {
        return str + int
    }

    //类
    class User (name : String, age : Int) {
        private val mName : String = name
        private val mAge: Int = age

        fun getName(): String {
            return mName
        }
        fun getAge(): Int {
            return mAge
        }
    }

    //接口
    interface Human {
        fun getSex() : String
        fun getName() : String {
            return "human"
        }
        fun setName(name : String)
        fun setSex(sex : String)
    }

    class Man : Human {
        private var mSex : String = "woman"
        private var s : String = "seven"
        override fun getSex(): String {
            return mSex
        }

        override fun getName(): String {
            return s
        }

        override fun setName(name: String) {
            s = name
        }

        override fun setSex(sex: String) {
            mSex = sex
        }
    }
}