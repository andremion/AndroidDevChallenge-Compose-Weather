package com.example.androiddevchallenge.util

import android.util.Log

fun <T> logger(clazz: Class<T>) = Logger(clazz)

class Logger<T>(clazz: Class<T>) {

    private val tag: String = clazz.name.toString()

    fun debug(msg: String, tr: Throwable? = null) {
        Log.d(tag, msg, tr)
    }

    fun error(msg: String, tr: Throwable) {
        Log.e(tag, msg, tr)
    }
}
