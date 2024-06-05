package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.domain.model.UserInfo
import kr.tekit.lion.daongil.domain.usecase.base.Result

interface UserLoginRepository {
    suspend fun loginWithKakao(LoginType: String): UserInfo
}