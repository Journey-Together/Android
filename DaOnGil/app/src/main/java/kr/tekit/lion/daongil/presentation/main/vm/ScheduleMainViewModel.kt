package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.MyMainSchedule
import kr.tekit.lion.daongil.domain.model.OpenPlanInfo
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.usecase.plan.GetMyMainScheduleUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetOpenPlanListUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.presentation.login.LogInState

class ScheduleMainViewModel(
    private val getOpenPlanListUseCase: GetOpenPlanListUseCase,
    private val getMyMainScheduleUseCase: GetMyMainScheduleUseCase,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _openPlanList = MutableLiveData<List<OpenPlanInfo>>()
    val openPlanList : LiveData<List<OpenPlanInfo>> = _openPlanList

    private val _myMainPlanList = MutableLiveData<List<MyMainSchedule?>?>()
    val myMainPlanList : LiveData<List<MyMainSchedule?>?> = _myMainPlanList

    private val _loginState = MutableStateFlow<LogInState>(LogInState.Checking)
    val loginState = _loginState.asStateFlow()

    init {
        getOpenPlanList(5, 0)
        checkLoginState()
    }

    fun getOpenPlanList(size: Int, page: Int) =
        viewModelScope.launch {
            getOpenPlanListUseCase(size, page).onSuccess {
                _openPlanList.value = it.openPlanList
            }
        }


    fun getMyMainPlanList() =
        viewModelScope.launch {
            getMyMainScheduleUseCase().onSuccess {
                _myMainPlanList.value = it
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