package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.MyDefaultInfo
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.usecase.myinfo.GetMyDefaultInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.presentation.login.LogInState

class MyInfoMainViewModel(
    private val authRepository: AuthRepository,
    private val getMyDefaultInfoUseCase: GetMyDefaultInfoUseCase,
): ViewModel() {

    private val _loginState = MutableStateFlow<LogInState>(LogInState.Checking)
    val loginState = _loginState.asStateFlow()

    private val _myInfo = MutableStateFlow(MyDefaultInfo())
    val myInfo = _myInfo.asStateFlow()

    init {
        viewModelScope.launch {
            checkLoginState()
        }
    }

    private suspend fun checkLoginState(){
        authRepository.loggedIn.collect{ isLoggedIn ->
            if (isLoggedIn) _loginState.value = LogInState.LoggedIn
            else _loginState.value = LogInState.LoginRequired
        }
    }

    fun onStateLoggedIn() = viewModelScope.launch{
        getMyDefaultInfoUseCase().onSuccess {
            _myInfo.value = it
        }.onError {
            it.printStackTrace()
        }
    }
}