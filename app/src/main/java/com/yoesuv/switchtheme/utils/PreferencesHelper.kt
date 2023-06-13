package com.yoesuv.switchtheme.utils

import android.content.Context

class PreferencesHelper(context: Context) {

    private val prefHelper = context.getSharedPreferences("pref_app", Context.MODE_PRIVATE)

    fun setBoolean(key: String, value: Boolean) {
        prefHelper.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return prefHelper.getBoolean(key, false)
    }
}