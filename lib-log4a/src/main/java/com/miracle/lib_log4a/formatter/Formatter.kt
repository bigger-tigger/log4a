package com.miracle.lib_log4a.formatter

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
interface Formatter {

    fun format(logLevel: Int, tag: String?, msg: String): String
}