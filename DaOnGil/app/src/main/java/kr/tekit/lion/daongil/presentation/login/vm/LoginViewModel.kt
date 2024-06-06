package kr.tekit.lion.daongil.presentation.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.usecase.SignInUseCase

class LoginViewModel(
    private val userLoginUseCase: SignInUseCase,
) : ViewModel() {

    private val _sigInInUiState = MutableStateFlow(false)
    val sigInInUiState = _sigInInUiState.asStateFlow()

    fun onCompleteSignIn(type: String, token: String) = viewModelScope.launch {
        userLoginUseCase(type, token)
        _sigInInUiState.value = true
    }
}

