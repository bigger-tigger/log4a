package com.miracle.lib_log4a


import com.miracle.lib_log4a.Level.DEBUG
import com.miracle.lib_log4a.Level.ERROR
import com.miracle.lib_log4a.Level.INFO
import com.miracle.lib_log4a.Level.VERBOSE
import com.miracle.lib_log4a.Level.WARN
import com.miracle.lib_log4a.logger.AndroidLogger
import com.miracle.lib_log4a.logger.Logger
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
object Log4a {

    private var sLoggerDelegate: Logger = AndroidLogger()

    fun setLogger(logger: Logger) {
        sLoggerDelegate = logger
    }

    fun getLogger() = sLoggerDelegate

    fun v(tag: String?, msg: String?) {
        println(VERBOSE, tag, msg)
    }

    fun d(tag: String?, msg: String?) {
        println(DEBUG, tag, msg)
    }

    fun i(tag: String?, msg: String?) {
        println(INFO, tag, msg)
    }

    fun w(tag: String?, msg: String?) {
        println(WARN, tag, msg)
    }

    fun w(tag: String?, msg: String, tr: Throwable?) {
        println(WARN, tag, msg + "\n" + getStackTraceString(tr))
    }

    fun w(tag: String?, tr: Throwable?) {
        println(WARN, tag, getStackTraceString(tr))
    }

    fun e(tag: String?, msg: String?) {
        Log4a.println(ERROR, tag, msg)
    }

    fun e(tag: String?, msg: String, tr: Throwable?) {
        println(ERROR, tag, msg + "\n" + getStackTraceString(tr))
    }

    fun e(tag: String?, tr: Throwable?) {
        println(ERROR, tag, getStackTraceString(tr))
    }

    fun println(priority: Int, tag: String?, msg: String?) {
        sLoggerDelegate.println(priority, tag, msg!!)
    }

    fun flush() {
        sLoggerDelegate.flush()
    }

    fun release() {
        if (sLoggerDelegate != null) {
            sLoggerDelegate.release()
        }
    }

    fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }
}