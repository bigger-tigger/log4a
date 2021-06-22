package com.miracle.lib_log4a.formatter

import com.miracle.lib_log4a.Level
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
class DateFileFormatter: Formatter {

    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date: Date
    private lateinit var stringBuffer: StringBuffer
    private var lastDateFormated: String? = null
    private var timeLength = 0

    constructor(): this("yyyy:MM:dd HH:mm:ss")

    constructor(pattern: String) {
        simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        stringBuffer = StringBuffer()
        val instance = Calendar.getInstance()
        instance.set(Calendar.SECOND, 0)
        date = instance.time
    }


    @Synchronized
    override fun format(logLevel: Int, tag: String?, msg: String): String {
        if ((System.currentTimeMillis() - date.time) > 1000 || lastDateFormated == null) {
            date.time = System.currentTimeMillis()
            lastDateFormated = simpleDateFormat.format(date)
            resetTimePrefix()
            return formatString(logLevel, tag, msg)
        }
        return formatString(logLevel, tag, msg)
    }

    private fun resetTimePrefix() {
        if (stringBuffer.isNotEmpty()) {
            stringBuffer.delete(0, stringBuffer.length)
        }
        timeLength = stringBuffer.append(lastDateFormated).append(' ').length
    }

    private fun formatString(logLevel: Int, tag: String?, msg: String): String {
        if (stringBuffer.length > timeLength) {
            stringBuffer.delete(timeLength, stringBuffer.length)
        }
        return stringBuffer.append(Level.getShortLevelName(logLevel)).append(tag).append(msg).append('\n').toString();
    }
}