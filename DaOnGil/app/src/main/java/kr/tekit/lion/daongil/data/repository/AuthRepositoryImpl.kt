package kr.tekit.lion.daongil.data.repository

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.data.datasource.AuthDataSource
import kr.tekit.lion.daongil.data.datasource.TokenDataSource
import kr.tekit.lion.daongil.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val tokenDataSource: TokenDataSource,
) : AuthRepository {

    override val loggedIn: Flow<Boolean>
        get() = authDataSource.loggedIn

    override suspend fun signIn(type: String, token: String) {

        authDataSource.signIn(type, token).onSuccess { response ->
            tokenDataSource.saveTokens(response.data.accessToken, response.data.refreshToken)
        }.onFailure {
            it.printStackTrace()
        }
    }
}