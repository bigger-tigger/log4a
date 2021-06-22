package com.miracle.lib_log4a.appender

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
interface Appender {

    fun append(logLevel: Int, tag: String?, msg: String)

    fun flush()

    fun release()
}