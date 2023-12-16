package com.whereismymotivation.utils.log

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.whereismymotivation.BuildConfig
import timber.log.Timber

object Logger {

    init {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    fun d(tag: String, s: String, vararg params: Any) =
        Timber.tag(tag).d(s, params)

    fun d(tag: String, throwable: Throwable, vararg params: Any) =
        Timber.tag(tag).d(throwable, tag, params)

    fun i(tag: String, s: String, vararg params: Any) =
        Timber.tag(tag).i(s, params)

    fun i(tag: String, throwable: Throwable, vararg params: Any) =
        Timber.tag(tag).i(throwable, tag, params)

    fun w(tag: String, s: String, vararg params: Any) =
        Timber.tag(tag).w(s, params)

    fun w(tag: String, throwable: Throwable, vararg params: Any) =
        Timber.tag(tag).w(throwable, tag, params)

    fun e(tag: String, s: String, vararg params: Any) =
        Timber.tag(tag).e(s, params)

    fun e(tag: String, throwable: Throwable, vararg params: Any) =
        Timber.tag(tag).e(throwable, tag, params)

    fun record(e: Throwable) = Firebase.crashlytics.recordException(e)

}
