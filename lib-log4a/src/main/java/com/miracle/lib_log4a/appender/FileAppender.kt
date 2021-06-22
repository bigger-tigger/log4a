package com.miracle.lib_log4a.appender

import android.content.Context
import android.os.Environment
import com.miracle.lib_log4a.Level
import com.miracle.lib_log4a.LogBuffer
import com.miracle.lib_log4a.formatter.Formatter
import com.miracle.lib_log4a.interceptor.Interceptor
import java.io.File

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
class FileAppender : AbsAppender {

    private var logBuffer: LogBuffer? = null

    private var formatter: Formatter? = null


    internal constructor(builder: Builder) {
        logBuffer = LogBuffer(
            builder.bufferFilePath!!, builder.bufferSize,
            builder.logFilePath!!, builder.compress
        )
        setMaxSingleLength(builder.bufferSize)
        setLevel(builder.level)
        addInterceptor(builder.interceptors)
        setFormatter(builder.formatter)
    }

    fun getBufferPath(): String? {
        return logBuffer!!.getBufferPath()
    }

    fun getBufferSize(): Int {
        return logBuffer!!.getBufferSize()
    }

    fun getLogPath(): String? {
        return logBuffer!!.getLogPath()
    }

    fun changeLogPath(logPath: String?) {
        logBuffer!!.changeLogPath(logPath!!)
    }

    fun setFormatter(formatter: Formatter?) {
        if (formatter != null) {
            this.formatter = formatter
        }
    }

    override fun doAppend(logLevel: Int, tag: String?, msg: String) {
        logBuffer!!.write(formatter!!.format(logLevel, tag, msg))
    }

    override fun flush() {
        super.flush()
        logBuffer!!.flushAsync()
    }

    override fun release() {
        super.release()
        logBuffer!!.release()
    }

    class Builder {
        private var context: Context? = null

        internal var bufferFilePath: String? = null
        internal var logFilePath: String? = null
        internal var bufferSize = 4096
        internal var level: Int = Level.VERBOSE
        internal var interceptors: ArrayList<Interceptor>? = null
        internal var formatter: Formatter? = null
        internal var compress = false

        constructor(context: Context) {
            this.context = context
        }

        fun setBufferSize(size: Int) = apply {
            bufferSize = size
        }

        fun setBufferFilePath(path: String) = apply {
            bufferFilePath = path
        }

        fun setLogFilePath(path: String) = apply {
            logFilePath = path
        }

        fun setLevel(l: Int) = apply {
            level = l
        }

        fun addInterceptor(interceptor: Interceptor) = apply {
            if (interceptors == null) {
                interceptors = ArrayList()
            }
            interceptors!!.add(interceptor)
        }

        fun setFormatter(formatter: Formatter?) = apply {
            this.formatter = formatter
        }

        fun setCompress(compress: Boolean) = apply {
            this.compress = compress
        }

        fun create(): FileAppender {
            requireNotNull(logFilePath) { "logFilePath cannot be null" }
            if (bufferFilePath == null) {
                bufferFilePath = getDefaultBufferPath(context!!)
            }
            if (formatter == null) {
                formatter = object : Formatter {
                    override fun format(logLevel: Int, tag: String?, msg: String): String {
                        return String.format(
                            "%s/%s: %s\n",
                            Level.getShortLevelName(logLevel),
                            tag,
                            msg
                        )
                    }

                }
            }
            return FileAppender(this)
        }

        private fun getDefaultBufferPath(context: Context): String? {
            val bufferFile =
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED && context.getExternalFilesDir("log4a") != null) {
                    context.getExternalFilesDir("log4a")
                } else {
                    File(context.filesDir, "log4a")
                }
            if (bufferFile != null && !bufferFile.exists()) {
                bufferFile.mkdirs()
            }
            return File(bufferFile, ".log4aCache").absolutePath
        }
    }
}