package com.quinn.hunter.debug

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import com.hunter.library.debug.Constants
import com.hunter.library.debug.HunterDebug
import com.hunter.library.debug.HunterLoggerHandler
import com.quinn.hunter.debug.test.ClassTest
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(DebugTree())
        super.onCreate(savedInstanceState)
        HunterLoggerHandler.installLog(object : HunterLoggerHandler() {
            override fun logEnter(priority: Int, tag: String, methodName: String, params: String) {
                Log.println(priority, "hunter-$tag", String.format("<IN> %s::%s(%s)", tag, methodName, params))
            }

            override fun logExit(priority: Int, tag: String, methodName: String, costedMillis: Long, result: String) {
                Log.println(priority, "hunter-$tag", String.format("<OUT> %s::%s(%sms) = %s", tag, methodName, costedMillis, result))
            }
        })
        setContentView(R.layout.activity_main)
        val mainPresenter = MainPresenter()
        mainPresenter.load("1212")
        mainPresenter.loadMore("12123232")
        val bool = true
        val byte_v: Byte = 1
        val char_v = 2.toChar()
        val short_v: Short = 3
        val int_v = 4
        val long_v: Long = 5
        val float_v = 6f
        val double_v = 7.0
        val string_v = "string"
        val int_arr = intArrayOf(1, 2, 3)
        method_test_parameter(bool, byte_v, char_v, short_v, int_v, long_v, float_v, double_v, string_v, int_arr, savedInstanceState)
        method_empty_parameter_empty_return()
        method_return_array()
        method_return_boolean()
        method_return_byte()
        method_return_char()
        method_return_double()
        method_return_float()
        method_return_int()
        method_return_long()
        method_return_object()
        method_return_object_array()
        method_return_short()
        method_static("parameter value")
        appendIntAndString(5, "billions")
        //        try {
//            method_throw_exception();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            testVoidMethodWithException();
//        } catch (Exception error) {
//        }
        val classTest = ClassTest()
        classTest.test(5, "hello world", 2.toByte())
        classTest.test2()
        try {
            classTest.test3()
        } catch (t: Throwable) {
        }
    }

    private fun appendIntAndString(a: Int, b: String): String {
        SystemClock.sleep(100)
        return "$a $b"
    }

    private val paramNames: List<String> = ArrayList()
    @HunterDebug(debugResult = false, logLevel = Log.ERROR, debugArguments = Constants.ARGUMENTS_FULL)
    private fun method_test_parameter(bool_v: Boolean, byte_v: Byte, char_v: Char, short_v: Short, int_v: Int, long_v: Long, float_v: Float, double_v: Double, string_v: String, arr: IntArray, savedInstanceState: Bundle?): Int {
        val insideLocal = 5
        val insideLocal2 = 6
        Log.i(TAG, "insideLocal $insideLocal")
        return insideLocal + insideLocal2
    }

    @HunterDebug(logLevel = Log.VERBOSE)
    private fun method_empty_parameter_empty_return() {
    }

    private fun method_return_boolean(): Boolean {
        return true
    }

    private fun method_return_char(): Char {
        return 'c'
    }

    private fun method_return_byte(): Byte {
        return 0x01
    }

    private fun method_return_short(): Short {
        return 2
    }

    private fun method_return_int(): Int {
        return 2
    }

    private fun method_return_long(): Long {
        return 2L
    }

    private fun method_return_double(): Double {
        return 2.0
    }

    private fun method_return_float(): Float {
        return 2.0f
    }

    private fun method_return_object(): MainPresenter {
        return MainPresenter()
    }

    private fun method_return_object_array(): Array<MainPresenter> {
        return arrayOf(MainPresenter(), MainPresenter(), MainPresenter())
    }

    private fun method_return_array(): IntArray {
        return intArrayOf(1, 2, 3)
    }

    @Throws(Exception::class)
    private fun method_throw_exception(): Int {
        val a = 10
        val b = 0
        require(b != 0) { "illagel argu" }
        return a / 0
    }

    private fun testVoidMethodWithException() {
        throw RuntimeException("not impl")
    }

    companion object {
        const val TAG = "MainActivity"
        private fun method_static(str: String): Any {
            return "object string$str"
        }
    }
}
