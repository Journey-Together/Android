package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.OpenPlanInfo
import kr.tekit.lion.daongil.domain.usecase.GetOpenPlanListUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class ScheduleMainViewModel(
    private val getOpenPlanListUseCase: GetOpenPlanListUseCase
): ViewModel() {

    private val _openPlanList = MutableLiveData<List<OpenPlanInfo>>()
    val openPlanList : LiveData<List<OpenPlanInfo>> = _openPlanList

    init {
        getOpenPlanList(5, 0)
    }

    fun getOpenPlanList(size: Int, page: Int) =
        viewModelScope.launch {
            getOpenPlanListUseCase(size, page).onSuccess {
                _openPlanList.value = it.openPlanList
            }
        }

}