package com.tikhomirov.ticktacktoe.base

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class ResourcesManager(private val context: Context) {
    fun getString(@StringRes resId: Int): String = context.getString(resId)
    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
        context.getString(resId, *formatArgs)

    fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(context, resId)
}