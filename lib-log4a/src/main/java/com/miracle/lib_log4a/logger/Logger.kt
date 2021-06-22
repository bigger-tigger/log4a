package com.miracle.lib_log4a.logger

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
interface Logger {

    fun println(priority: Int, tag: String?, msg: String)

    fun flush()

    fun release()
}