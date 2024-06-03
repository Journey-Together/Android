package kr.tekit.lion.daongil

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class HighThemeApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance : HighThemeApp? = null

        fun context(): Context {
            return instance!!.applicationContext
        }

        fun getInstance(): HighThemeApp {
            return instance!!
        }
    }

    fun getApplicationPrefs(key: String): SharedPreferences {
        return context().getSharedPreferences(key, MODE_PRIVATE)
    }

    fun setThemePrefs(theme: String) {
        val prefs = getApplicationPrefs("theme_prefs")
        prefs.edit().putString("theme", theme).apply()
    }

    fun getThemePrefs(): String? {
        val prefs = getApplicationPrefs("theme_prefs")
        return prefs.getString("theme", "")
    }

    fun isFirstLogin() : Boolean {
        val prefs = getApplicationPrefs("login_prefs")
        return prefs.getBoolean("isFirstLogin", true)
    }

    fun setFirstLogin(isFirstLogin: Boolean) {
        val prefs = getApplicationPrefs("login_prefs")
        prefs.edit().putBoolean("isFirstLogin", isFirstLogin).apply()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}