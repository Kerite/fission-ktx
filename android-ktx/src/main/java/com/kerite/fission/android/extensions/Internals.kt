package com.kerite.fission.android.extensions

import android.content.Intent
import android.os.Bundle
import java.io.Serializable

object Internals {
    @JvmStatic
    fun internalFillIntent(intent: Intent, vararg params: Pair<String, Any?>) = intent.apply {
        for (param in params) {
            when (val value = param.second) {
                null -> putExtra(param.first, value as Serializable?)
                is Int -> putExtra(param.first, value)
                is Long -> putExtra(param.first, value)
                is String -> putExtra(param.first, value)
                is CharSequence -> putExtra(param.first, value)
                is Float -> putExtra(param.first, value)
                is Double -> putExtra(param.first, value)
                is Char -> putExtra(param.first, value)
                is Short -> putExtra(param.first, value)
                is Boolean -> putExtra(param.first, value)
                is Bundle -> putExtra(param.first, value)
                is Array<*> -> putExtra(param.first, value)
                is IntArray -> putExtra(param.first, value)
                is LongArray -> putExtra(param.first, value)
                is FloatArray -> putExtra(param.first, value)
                is DoubleArray -> putExtra(param.first, value)
                is Serializable -> putExtra(param.first, value)
            }
        }
    }
}