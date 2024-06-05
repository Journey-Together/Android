package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.UserInfo
import kr.tekit.lion.daongil.domain.repository.UserLoginRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class UserLoginUseCase(private val userLoginRepository: UserLoginRepository) : BaseUseCase() {
    suspend operator fun invoke(LoginType: String): Result<UserInfo> = execute { // 함수를 객체처럼 사용하는 애.
        // 자동으로 실행되는 애.
        userLoginRepository.loginWithKakao(LoginType)
    }
}