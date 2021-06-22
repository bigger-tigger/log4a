package com.miracle.lib_log4a.appender

import android.util.Log
import com.miracle.lib_log4a.Level
import com.miracle.lib_log4a.interceptor.Interceptor

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
class AndroidAppender: AbsAppender {

    constructor(builder: Builder) {
        setLevel(builder.level)
        addInterceptor(builder.interceptors)
    }

    override fun doAppend(logLevel: Int, tag: String?, msg: String) {
        Log.println(logLevel, tag, msg)
    }

    class Builder {
        internal var level: Int = Level.VERBOSE
        internal var interceptors: ArrayList<Interceptor>? = null

        fun setLevel(level: Int) = apply {
            this.level = level
        }

        fun addInterceptor(interceptor: Interceptor) = apply {
            if (interceptors == null) interceptors = ArrayList()
            interceptors!!.add(interceptor)
        }

        fun create() = AndroidAppender(this)
    }
}