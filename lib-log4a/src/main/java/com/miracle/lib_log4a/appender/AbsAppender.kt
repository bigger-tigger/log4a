package com.miracle.lib_log4a.appender

import com.miracle.lib_log4a.LogData
import com.miracle.lib_log4a.interceptor.Interceptor
import com.miracle.lib_log4a.interceptor.LevelInterceptor
import kotlin.math.max
import kotlin.math.min

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
abstract class AbsAppender: Appender {

    companion object {
        const val MAX_LENGTH_OF_SINGLE_MESSAGE = 4063
    }

    private var maxSingleLength = MAX_LENGTH_OF_SINGLE_MESSAGE
    private val interceptors = ArrayList<Interceptor>()
    private val levelInterceptor = LevelInterceptor()

    constructor() {
        addInterceptor(levelInterceptor)
    }

    fun setLevel(level: Int) {
        levelInterceptor.setLevel(level)
    }

    fun addInterceptor(interceptors: List<Interceptor>?) {
        if (interceptors != null && interceptors.isNotEmpty()) {
            this.interceptors.addAll(interceptors)
        }
    }

    fun addInterceptor(interceptor: Interceptor?) {
        if (interceptor != null) {
            interceptors.add(interceptor)
        }
    }

    fun setMaxSingleLength(length: Int) {
        maxSingleLength = length
    }

    private fun appendInner(logLevel: Int, tag: String?, msg: String) {
        if (msg.length <= maxSingleLength) {
            doAppend(logLevel, tag, msg)
            return
        }
        val msgLength = msg.length
        var start = 0
        var end = start + maxSingleLength
        while (start < msgLength) {
            doAppend(logLevel, tag, msg)
            start = end
            end = min(start + maxSingleLength, msgLength)
        }
    }

    abstract fun doAppend(logLevel: Int, tag: String?, msg: String)

    override fun append(logLevel: Int, tag: String?, msg: String) {
        val logData = LogData.obtain(logLevel, tag, msg)
        var intercepted = false
        interceptors.forEach {
            if (!it.interceptor(logData))
                intercepted = true
        }
        if (!intercepted) {
            appendInner(logData.logLevel, logData.tag, logData.msg!!)
        }
        logData.recycle()
    }

    override fun flush() {

    }

    override fun release() {

    }
}