package kr.tekit.lion.daongil.presentation.login.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.User
import kr.tekit.lion.daongil.domain.usecase.GetMyInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.SignInUseCase

class LoginViewModel(
    private val userLoginUseCase: SignInUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : ViewModel() {
    init {
       onCompleteLogIn()
    }
    private val _myInfo = MutableLiveData<User>()
    val myInfo: LiveData<User> get() = _myInfo

    fun onCompleteSignIn(type: String, token: String) = viewModelScope.launch {
        userLoginUseCase(type, token)
    }

    private fun onCompleteLogIn() = viewModelScope.launch{
        getMyInfoUseCase().collect { user ->
            _myInfo.value = user
        }
    }
}

