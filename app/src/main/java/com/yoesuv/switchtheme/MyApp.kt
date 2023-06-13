package com.yoesuv.switchtheme

import android.app.Application
import com.yoesuv.switchtheme.utils.PreferencesHelper

class MyApp: Application() {

    companion object {
        var prefHelper: PreferencesHelper? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefHelper = PreferencesHelper(this)
    }

}