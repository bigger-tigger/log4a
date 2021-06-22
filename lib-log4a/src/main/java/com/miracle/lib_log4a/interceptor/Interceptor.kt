package com.miracle.lib_log4a.interceptor

import com.miracle.lib_log4a.LogData

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
interface Interceptor {
    fun interceptor(logData: LogData?): Boolean
}