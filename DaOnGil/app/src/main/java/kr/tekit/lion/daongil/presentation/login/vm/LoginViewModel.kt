package kr.tekit.lion.daongil.presentation.login.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.UserInfo
import kr.tekit.lion.daongil.domain.usecase.UserLoginUseCase

class LoginViewModel(
    private val userLoginUseCase: UserLoginUseCase
) : ViewModel() {


}