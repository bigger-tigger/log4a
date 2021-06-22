package com.miracle.lib_log4a.logger

import android.util.Log

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
class AndroidLogger: Logger {
    override fun println(priority: Int, tag: String?, msg: String) {
        Log.println(priority, tag, msg)
    }

    override fun flush() {

    }

    override fun release() {

    }
}