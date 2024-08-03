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

    private val _pageNo = MutableLiveData<Int>()
    val pageNo: LiveData<Int> = _pageNo

    init {
        getOpenPlanList()
    }

    fun getOpenPlanList() =
        viewModelScope.launch {
            getOpenPlanListUseCase(10, 0).onSuccess {
                _openPlanList.value = it.openPlanList
                _isLastPage.value = it.last
                _pageNo.value = it.pageNo + 1
            }
        }

    fun getOpenPlanListPaging() =
        viewModelScope.launch {
            if(isLastPage.value == false){
                pageNo.value?.let {
                    getOpenPlanListUseCase(10, it).onSuccess {
                        val currentList = _openPlanList.value.orEmpty()
                        _openPlanList.value = currentList + it.openPlanList
                        _isLastPage.value = it.last
                        _pageNo.value = it.pageNo + 1
                    }
                }
            }
        }

}