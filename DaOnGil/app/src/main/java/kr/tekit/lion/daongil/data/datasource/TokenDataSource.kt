package kr.tekit.lion.daongil.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kr.tekit.lion.daongil.data.local.AppSettings
import kr.tekit.lion.daongil.data.local.dataStore

class TokenDataSource(
    private val context: Context
) {
    private val dataStore: DataStore<AppSettings>
        get() = context.dataStore

    private val data: Flow<AppSettings>
        get() = dataStore.data

    suspend fun getAccessToken(): String = data.first().accessToken

    suspend fun getToken(): Pair<String, String> =
        data.first().run { Pair(accessToken, refreshToken) }

    suspend fun saveTokens(accessToken: String, refreshToken: String){
        dataStore.updateData {
            it.copy(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }
}