package com.noorheroes.car24assignment.core.common.logging

import timber.log.Timber

interface Logger {
    fun v(tag: String, message: String)
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}

class AppLogger : Logger {
    override fun v(tag: String, message: String) = Timber.tag(tag).v("[$tag][VERBOSE] $message")
    override fun d(tag: String, message: String) = Timber.tag(tag).d("[$tag][DEBUG] $message")
    override fun i(tag: String, message: String) = Timber.tag(tag).i("[$tag][INFO] $message")
    override fun w(tag: String, message: String, throwable: Throwable?) = Timber.tag(tag).w(throwable, "[$tag][WARNING] $message")
    override fun e(tag: String, message: String, throwable: Throwable?) = Timber.tag(tag).e(throwable, "[$tag][ERROR] $message")
}
