package kr.tekit.lion.daongil.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.domain.model.User
import kr.tekit.lion.daongil.domain.repository.AuthRepository

class GetMyInfoUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<User> {
        return authRepository.cachedUser
    }
}