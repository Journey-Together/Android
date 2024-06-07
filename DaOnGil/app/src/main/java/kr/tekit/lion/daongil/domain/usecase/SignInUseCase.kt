package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository){
    suspend operator fun invoke(type: String, token: String) {
        authRepository.signIn(type = type, token = token)
    }
}