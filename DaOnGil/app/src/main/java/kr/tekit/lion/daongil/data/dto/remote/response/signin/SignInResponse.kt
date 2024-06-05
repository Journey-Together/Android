package kr.tekit.lion.daongil.data.dto.remote.response.signin


import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.SignIn

@JsonClass(generateAdapter = true)
data class SignInResponse(
    val code: Int = 0,
    val data: Data,
    val message: String
){
    fun toDomainModel(): SignIn{
        return SignIn(
            code = code,
            email = data.email,
            loginType = data.loginType,
            memberId = data.memberId,
            memberType = data.memberType,
            name = data.name,
            profileUuid = data.profileUuid,
            refreshToken = data.refreshToken,
            accessToken = data.accessToken
        )
    }
}