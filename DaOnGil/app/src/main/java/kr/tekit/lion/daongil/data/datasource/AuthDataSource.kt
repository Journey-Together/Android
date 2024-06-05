package kr.tekit.lion.daongil.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.tekit.lion.daongil.data.dto.local.UserResponse
import kr.tekit.lion.daongil.data.dto.remote.response.signin.SignInResponse
import kr.tekit.lion.daongil.data.local.AppSettings
import kr.tekit.lion.daongil.data.local.dataStore
import kr.tekit.lion.daongil.data.network.service.AuthService

class AuthDataSource(
    private val context: Context,
    private val authService: AuthService
) {
    private val dataStore: DataStore<AppSettings>
        get() = context.dataStore

    private val data: Flow<AppSettings>
        get() = dataStore.data

    val user: Flow<UserResponse>
        get() {
            return dataStore.data.map {
                UserResponse(
                    email = it.email ?: "",
                    memberId = it.memberId ?: -1,
                    name = it.name ?: "",
                    profileUuid = it.profileUuid ?: ""
                )
            }
        }

    val loggedIn: Flow<Boolean>
        get() = data.map { it.accessToken.isNotBlank() }

    suspend fun signIn(type: String, token: String) = runCatching {
        authService.signIn(type = type, token = token)
    }

    suspend fun updateUser(newUser: SignInResponse) {
        dataStore.updateData {
            val user = newUser.data
            it.copy(
                email = user.email,
                loginType = user.loginType,
                memberId = user.memberId,
                memberType = user.memberType,
                name = user.name,
                profileUuid = user.profileUuid
            )
        }
    }
}













