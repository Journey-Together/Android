package kr.tekit.lion.daongil.presentation.publicschedule.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.OpenPlanInfo
import kr.tekit.lion.daongil.domain.usecase.plan.GetOpenPlanListUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class PublicScheduleViewModel(
    private val getOpenPlanListUseCase: GetOpenPlanListUseCase
): ViewModel() {

    private val _openPlanList = MutableLiveData<List<OpenPlanInfo>>()
    val openPlanList : LiveData<List<OpenPlanInfo>> = _openPlanList

    private val _isLastPage = MutableLiveData<Boolean>()
    val isLastPage: LiveData<Boolean> = _isLastPage

    private val _currentPageNo = MutableLiveData<Int>()
    val currentPageNo: LiveData<Int> = _currentPageNo

    init {
        getOpenPlanList(8, 0)
    }

    fun getOpenPlanList(size: Int, page: Int) =
        viewModelScope.launch {
            getOpenPlanListUseCase(size, page).onSuccess {
                _openPlanList.value = it.openPlanList
                _isLastPage.value = it.last
                _currentPageNo.value = it.pageNo + 1
            }
        }
}