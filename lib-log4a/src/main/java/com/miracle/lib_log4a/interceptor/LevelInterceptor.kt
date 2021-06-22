package com.miracle.lib_log4a.interceptor

import com.miracle.lib_log4a.Level
import com.miracle.lib_log4a.LogData

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
class LevelInterceptor: Interceptor {
    private var level = Level.VERBOSE

     fun setLevel(level: Int) {
         this.level = level
     }

    override fun interceptor(logData: LogData?): Boolean {
        return logData != null && logData!!.logLevel >= level
    }
}