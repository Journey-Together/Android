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
        AuthManager.init(this)
    }
}

object AuthManager {
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"

    lateinit var prefs: SharedPreferences

    fun init(context: Context){
        prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    }

    var accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN, null)
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    var refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    fun clear() = prefs.edit().clear().apply()
}