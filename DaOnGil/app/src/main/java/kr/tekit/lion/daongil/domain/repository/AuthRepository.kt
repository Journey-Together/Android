package kr.tekit.lion.daongil.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.data.datasource.AuthDataSource
import kr.tekit.lion.daongil.data.datasource.TokenDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.AuthService
import kr.tekit.lion.daongil.data.repository.AuthRepositoryImpl

interface AuthRepository {
    suspend fun signIn(type: String, token: String)

    val loggedIn: Flow<Boolean>

    companion object{
        fun create(context: Context): AuthRepositoryImpl{
            return AuthRepositoryImpl(
                AuthDataSource(
                    context,
                    RetrofitInstance.serviceProvider(AuthService::class.java)
                ),
                TokenDataSource(
                    context
                )
            )
        }
    }
}