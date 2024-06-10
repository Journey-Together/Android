package kr.tekit.lion.daongil.presentation.onboarding.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.usecase.areacode.InitAreaCodeInfoUseCase
import kr.tekit.lion.daongil.presentation.login.LogInState

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val initAreaCodeInfoUseCase: InitAreaCodeInfoUseCase
): ViewModel() {

    private val _logInState = MutableStateFlow<LogInState>(LogInState.Checking)
    val uiState = _logInState.asStateFlow()

    init {
        viewModelScope.launch {
            initAreaCodeInfoUseCase()
            checkLoginStatus()
        }
    }

    private suspend fun checkLoginStatus(){
        authRepository.loggedIn.collect{ isLoggedIn ->
            if (isLoggedIn) _logInState.value = LogInState.LoggedIn
            else _logInState.value = LogInState.LoginRequired
        }
    }
}