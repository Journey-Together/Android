package kr.tekit.lion.daongil.presentation.schedule.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.ScheduleDetail
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailInfoGuestUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailInfoUseCase
import kr.tekit.lion.daongil.presentation.login.LogInState

class ScheduleDetailInfoViewModel(
    private val getScheduleDetailInfoUseCase: GetScheduleDetailInfoUseCase,
    private val authRepository: AuthRepository,
    private val getScheduleDetailInfoGuestUseCase: GetScheduleDetailInfoGuestUseCase
) : ViewModel() {

    private val _scheduleDetailInfo = MutableLiveData<ScheduleDetail>()
    val scheduleDetailInfo: LiveData<ScheduleDetail> = _scheduleDetailInfo

    private val _loginState = MutableStateFlow<LogInState>(LogInState.Checking)
    val loginState = _loginState.asStateFlow()

    init {
        checkLoginState()
    }

    fun getScheduleDetailInfo(planId: Long) =
        viewModelScope.launch {
            getScheduleDetailInfoUseCase.invoke(planId).onSuccess {
                _scheduleDetailInfo.value = it
            }
        }

    fun getScheduleDetailInfoGuest(planId: Long) =
        viewModelScope.launch {
            getScheduleDetailInfoGuestUseCase.invoke(planId).onSuccess {
                _scheduleDetailInfo.value = it
            }
        }

    private fun checkLoginState() =
        viewModelScope.launch {
            authRepository.loggedIn.collect { isLoggedIn ->
                if (isLoggedIn) _loginState.value = LogInState.LoggedIn
                else _loginState.value = LogInState.LoginRequired
            }
        }
}