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
import kr.tekit.lion.daongil.domain.usecase.plan.DeleteMyPlanReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.DeleteMyPlanScheduleUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailGuestUsecase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.UpdateMyPlanPublicUseCase
import kr.tekit.lion.daongil.presentation.login.LogInState

class ScheduleDetailViewModel(
    private val getScheduleDetailInfoUseCase: GetScheduleDetailUseCase,
    private val authRepository: AuthRepository,
    private val getScheduleDetailGuestUsecase: GetScheduleDetailGuestUsecase,
    private val deleteMyPlanReviewUseCase: DeleteMyPlanReviewUseCase,
    private val updateMyPlanPublicUseCase: UpdateMyPlanPublicUseCase,
    private val deleteMyPlanScheduleUseCase: DeleteMyPlanScheduleUseCase,
) : ViewModel() {

    private val _scheduleDetail = MutableLiveData<ScheduleDetail>()
    val scheduleDetail: LiveData<ScheduleDetail> = _scheduleDetail

    private val _loginState = MutableStateFlow<LogInState>(LogInState.Checking)
    val loginState = _loginState.asStateFlow()

    init {
        checkLoginState()
    }

    fun getScheduleDetailInfo(planId: Long) =
        viewModelScope.launch {
            getScheduleDetailInfoUseCase.invoke(planId).onSuccess {
                _scheduleDetail.value = it
            }
        }

    fun getScheduleDetailInfoGuest(planId: Long) =
        viewModelScope.launch {
           getScheduleDetailGuestUsecase.invoke(planId).onSuccess {
               _scheduleDetail.value = it
           }
        }

    fun deleteMyPlanReview(reviewId: Long, planId: Long) =
        viewModelScope.launch {
            deleteMyPlanReviewUseCase.invoke(reviewId, planId).onSuccess {
                _scheduleDetail.value = _scheduleDetail.value?.copy(
                reviewId = it.reviewId,
                content = it.content,
                grade = it.grade,
                reviewImages = it.imageList,
                hasReview = it.hasReview
                )
            }
        }

    fun updateMyPlanPublic(planId: Long) =
        viewModelScope.launch {
            updateMyPlanPublicUseCase.invoke(planId).onSuccess {
                _scheduleDetail.value = _scheduleDetail.value?.copy(
                    isPublic = it.isPublic
                )
            }
        }

    fun deleteMyPlanSchedule(planId: Long) =
        viewModelScope.launch {
            deleteMyPlanScheduleUseCase.invoke(planId).onSuccess {

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