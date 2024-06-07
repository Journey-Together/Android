package kr.tekit.lion.daongil.data.network

import kr.tekit.lion.daongil.data.datasource.AuthDataSource
import kr.tekit.lion.daongil.data.datasource.TokenDataSource
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator(
    private val tokenDataSource: TokenDataSource,
    private val authDataSource: AuthDataSource,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        return response.request.newBuilder().header("Authorization", "Bearer ").build()
    }
}