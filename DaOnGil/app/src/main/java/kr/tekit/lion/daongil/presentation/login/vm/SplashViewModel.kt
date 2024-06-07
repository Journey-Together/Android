package kr.tekit.lion.daongil.presentation.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.usecase.areacode.InitAreaCodeInfoUseCase
import kr.tekit.lion.daongil.presentation.login.UiState

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val initAreaCodeInfoUseCase: InitAreaCodeInfoUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Checking)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            initAreaCodeInfoUseCase()
            checkLoginStatus()
        }
    }

    private suspend fun checkLoginStatus(){
        authRepository.loggedIn.collect{ isLoggedIn ->
            if (isLoggedIn) _uiState.value = UiState.LoggedIn
            else _uiState.value = UiState.LoginRequired
        }
    }
}