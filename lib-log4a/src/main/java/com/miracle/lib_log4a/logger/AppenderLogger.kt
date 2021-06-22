package com.miracle.lib_log4a.logger

import com.miracle.lib_log4a.appender.Appender

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
class AppenderLogger internal constructor(): Logger {

    private val appenderList = ArrayList<Appender>()

    fun getAppenderList() = appenderList

    fun addAppender(appender: Appender) {
        appenderList.add(appender)
    }

    override fun println(priority: Int, tag: String?, msg: String) {
        if (appenderList == null) {
            return
        }
        for (appender in appenderList) {
            appender.append(priority, tag, msg)
        }
    }

    override fun flush() {
        for (appender in appenderList) {
            appender.flush()
        }
    }

    override fun release() {
        for (appender in appenderList) {
            appender.release()
        }
        appenderList.clear()
    }

    class Builder {
        private var logger: AppenderLogger = AppenderLogger()

        fun addAppender(appender: Appender?): Builder {
            logger.addAppender(appender!!)
            return this
        }

        fun create(): AppenderLogger {
            return logger
        }
    }
}