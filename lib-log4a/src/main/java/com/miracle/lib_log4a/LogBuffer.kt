package com.miracle.lib_log4a

import android.util.Log

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
class LogBuffer {

    private val TAG = "LogBuffer"

    private var ptr = 0L
    private var logPath: String? = null
    private var bufferPath: String? = null
    private var bufferSize = 0
    private var compress = false

    constructor(bufferPath: String, capacity: Int, logPath: String, compress: Boolean) {
        this.bufferPath = bufferPath
        bufferSize = capacity
        this.logPath = logPath
        this.compress = compress
        try {
            ptr = initNative(bufferPath, capacity, logPath, compress)
        } catch (e: Exception) {
            Log.e(TAG, Log4a.getStackTraceString(e))
        }
    }

    fun changeLogPath(logPath: String) {
        if (ptr != 0L) {
            try {
                changeLogPathNative(ptr, logPath)
                this.logPath = logPath
            } catch (e: Exception) {
                Log.e(TAG, Log4a.getStackTraceString(e))
            }
        }
    }

    fun isCompress(): Boolean {
        return compress
    }

    fun getLogPath(): String? {
        return logPath
    }

    fun getBufferPath(): String? {
        return bufferPath
    }

    fun getBufferSize(): Int {
        return bufferSize
    }

    fun write(log: String) {
        if (ptr != 0L) {
            try {
                writeNative(ptr, log)
            } catch (e: Exception) {
                Log.e(TAG, Log4a.getStackTraceString(e))
            }
        }
    }

    fun flushAsync() {
        if (ptr != 0L) {
            try {
                flushAsyncNative(ptr)
            } catch (e: Exception) {
                Log.e(TAG, Log4a.getStackTraceString(e))
            }
        }
    }

    fun release() {
        if (ptr != 0L) {
            try {
                releaseNative(ptr)
            } catch (e: Exception) {
                Log.e(TAG, Log4a.getStackTraceString(e))
            }
            ptr = 0
        }
    }


    init {
        System.loadLibrary("log4a-lib")
    }

    private external fun initNative(bufferPath: String, capacity: Int, logPath: String, compress: Boolean): Long

    private external fun writeNative(ptr: Long, log: String)

    private external fun flushAsyncNative(ptr: Long)

    private external fun releaseNative(ptr: Long)

    private external fun changeLogPathNative(ptr: Long, logPath: String)
}