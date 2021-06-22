package com.miracle.lib_log4a

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
class LogData {
    var logLevel: Int = 0
    var tag: String? = null
    var msg: String? = null

    private var next: LogData? = null

    companion object {
        private val sPoolSync = Object()
        private var sPool: LogData? = null
        private var sPoolSize = 0
        private const val MAX_POOL_SIZE = 50

        fun obtain(): LogData {
            synchronized(sPoolSync) {
                if (sPool != null) {
                    val m = sPool
                    sPool = m!!.next
                    m!!.next = null
                    sPoolSize--
                    return m
                }
            }
            return LogData()
        }

        fun obtain(logLevel: Int, tag: String?, msg: String): LogData {
            val obtain = obtain()
            obtain.logLevel = logLevel
            obtain.tag = tag
            obtain.msg = msg
            return obtain
        }
    }

    fun recycle() {
        logLevel = 0
        tag = null
        msg = null
        synchronized(sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool
                sPool = this
                sPoolSize++
            }
        }
    }
}