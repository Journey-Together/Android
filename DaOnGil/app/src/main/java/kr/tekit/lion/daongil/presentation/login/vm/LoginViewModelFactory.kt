package kr.tekit.lion.daongil.presentation.login.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.usecase.SignInUseCase

class LoginViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val authRepositoryImpl = AuthRepository.create(context)
    private val signInUseCase = SignInUseCase(authRepositoryImpl)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(signInUseCase) as T
        }
        return super.create(modelClass)
    }
}