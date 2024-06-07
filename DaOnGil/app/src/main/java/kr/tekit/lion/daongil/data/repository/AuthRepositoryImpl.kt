package kr.tekit.lion.daongil.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.tekit.lion.daongil.data.datasource.AuthDataSource
import kr.tekit.lion.daongil.data.datasource.TokenDataSource
import kr.tekit.lion.daongil.domain.model.User
import kr.tekit.lion.daongil.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val tokenDataSource: TokenDataSource,
) : AuthRepository {

    override val loggedIn: Flow<Boolean>
        get() = authDataSource.loggedIn

    override val cachedUser: Flow<User>
        get() = authDataSource.user.map { it.toDomainModel() }

    override suspend fun signIn(type: String, token: String) {

        authDataSource.signIn(type, token).onSuccess { response ->
            tokenDataSource.saveTokens(response.data.accessToken, response.data.refreshToken)
            authDataSource.updateUser(response)
        }.onFailure {
            it.printStackTrace()
        }
    }

}