package com.miracle.lib_log4a

/**
 * Created with Android Studio
 * Talk is Cheap
 *
 * @author: chenxukun
 * @date: 2021/6/21
 * @desc:
 */
object Level {

    const val VERBOSE = 2

    const val DEBUG = 3

    const val INFO = 4

    const val WARN = 5

    const val ERROR = 6

    fun getLevelName(logLevel: Int): String? {
        return when (logLevel) {
                VERBOSE -> "VERBOSE"
                DEBUG -> "DEBUG"
                INFO -> "INFO"
                WARN -> "WARN"
                ERROR -> "ERROR"
                else -> if (logLevel < VERBOSE) {
                    "VERBOSE-" + (VERBOSE - logLevel)
                } else {
                    "ERROR+" + (logLevel - ERROR)
                }
            }
    }

    fun getShortLevelName(logLevel: Int): String? {
        return when (logLevel) {
                VERBOSE -> "V"
                DEBUG -> "D"
                INFO -> "I"
                WARN -> "W"
                ERROR -> "E"
                else -> if (logLevel < VERBOSE) {
                    "V-" + (VERBOSE - logLevel)
                } else {
                    "E+" + (logLevel - ERROR)
                }
            }
    }
}