package kr.tekit.lion.daongil

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import timber.log.Timber

class HighThemeApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance : HighThemeApp? = null
        lateinit var prefs: TokenManager

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
        Timber.plant(Timber.DebugTree())
        prefs = TokenManager(applicationContext)
    }
}

class TokenManager(context: Context) {
    private val accessToken = context.getSharedPreferences("accessToken", Context.MODE_PRIVATE)
    private val refreshToken = context.getSharedPreferences("refreshToken", Context.MODE_PRIVATE)

    private val prefs = context.getSharedPreferences("user_token", Context.MODE_PRIVATE)

    fun setAccessToken(token: String) {
        accessToken.edit().putString("token", token).apply()
    }

    fun setRefreshToken(token: String) {
        refreshToken.edit().putString("refreshToken", token).apply()
    }

    fun getToken(): Pair<String, String>{
        val accessToken = prefs.getString("accessToken", "") ?: ""
        val refreshToken = prefs.getString("refreshToken", "") ?: ""
        return Pair(accessToken, refreshToken)
    }

    fun setToken(accessToken: String, refreshToken: String){
        val editor = prefs.edit()
        editor.putString(accessToken, accessToken).apply()
        editor.putString(refreshToken, refreshToken).apply()
        editor.apply()
    }
}