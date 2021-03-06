package com.example.androidclient.sharedpreferences

import android.app.Application

class App: Application() {

    companion object {
        lateinit var prefs: SharedPreferences
    }

    override fun onCreate() {
        prefs = SharedPreferences(applicationContext)
        super.onCreate()
    }
}