package kr.tekit.lion.daongil.presentation.schedulereview.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.BriefScheduleInfo
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.plan.GetBriefScheduleInfoUseCase

class WriteScheduleReviewViewModel(
    private val getBriefScheduleInfoUseCase: GetBriefScheduleInfoUseCase
) : ViewModel() {

    private val _briefSchedule = MutableLiveData<BriefScheduleInfo?>()
    val briefSchedule: LiveData<BriefScheduleInfo?> get() = _briefSchedule

    fun getBriefScheduleInfo(planId: Long){
        viewModelScope.launch {
            getBriefScheduleInfoUseCase(planId).onSuccess {
                _briefSchedule.postValue(it)
            }.onError {
                Log.e("getBriefScheduleInfo", "onError: $it")
            }
        }
    }
}