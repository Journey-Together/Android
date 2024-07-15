package kr.tekit.lion.daongil.presentation.myschedule.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.MyElapsedScheduleInfo
import kr.tekit.lion.daongil.domain.model.MyUpcomingScheduleInfo
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.plan.GetMyElapsedSchedulesUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetMyUpcomingSchedulesUseCase

class MyScheduleViewModel(
    private val getMyUpcomingSchedulesUseCase: GetMyUpcomingSchedulesUseCase,
    private val getMyElapsedSchedulesUseCase: GetMyElapsedSchedulesUseCase
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 10
    }

    init {
        getMyUpcomingScheduleList(PAGE_SIZE, 0)
        getMyElapsedScheduleList(PAGE_SIZE, 0)
    }

    private val _upcomingSchedules = MutableLiveData<List<MyUpcomingScheduleInfo>?>()
    val upcomingSchedules : LiveData<List<MyUpcomingScheduleInfo>?> get() = _upcomingSchedules

    private val _upcomingPageNo = MutableLiveData<Int>()
    private val _isLastUpcoming = MutableLiveData<Boolean>()

    private val _elapsedSchedules = MutableLiveData<List<MyElapsedScheduleInfo>?>()
    val elapsedSchedules : LiveData<List<MyElapsedScheduleInfo>?> get() = _elapsedSchedules

    private val _elapsedPageNo = MutableLiveData<Int>()
    private val _isLastElapsed = MutableLiveData<Boolean>()

    private fun getMyUpcomingScheduleList(size: Int, page: Int) {
        viewModelScope.launch {
            getMyUpcomingSchedulesUseCase(size, page)
                .onSuccess {
                    _upcomingSchedules.value = it.myUpcomingScheduleList
                    _upcomingPageNo.value = it.pageNo
                    _isLastUpcoming.value = it.last
                }.onError {
                    Log.e("getMyUpcomingScheduleList", "onError ${it.message}")
                }
        }
    }

    private fun getMyElapsedScheduleList(size: Int, page: Int) {
        viewModelScope.launch {
            getMyElapsedSchedulesUseCase(size, page)
                .onSuccess {
                    _elapsedSchedules.value = it.myElapsedScheduleList
                    _elapsedPageNo.value = it.pageNo
                    _isLastElapsed.value = it.last
                }.onError {
                    Log.e("getMyElapsedScheduleList", "onError ${it.message}")
                }
        }
    }

    fun getUpcomingPlanId(planPosition: Int) : Long {
        return _upcomingSchedules.value?.get(planPosition)?.planId ?: -1
    }

    fun getElapsedPlanId(planPosition: Int) : Long {
        return _elapsedSchedules.value?.get(planPosition)?.planId ?: -1
    }

    fun isUpcomingLastPage(): Boolean {
        return _isLastUpcoming.value ?: true
    }

    fun isElapsedLastPage(): Boolean {
        return _isLastElapsed.value ?: true
    }

    fun fetchNextUpcomingSchedules() {
        val page = _upcomingPageNo.value

        if(page != null){
            viewModelScope.launch {
                getMyUpcomingSchedulesUseCase(PAGE_SIZE, page+1)
                    .onSuccess {
                        val newList = _upcomingSchedules.value.orEmpty() + it.myUpcomingScheduleList
                        _upcomingSchedules.value = newList
                        _upcomingPageNo.value = it.pageNo
                        _isLastUpcoming.value = it.last
                    }.onError {
                        Log.e("fetchNextUpcomingSchedules", "onError ${it.message}")
                    }
            }
        }
    }

    fun fetchNextElapsedSchedules() {
        val page = _elapsedPageNo.value

        if(page != null){
            viewModelScope.launch {
                getMyElapsedSchedulesUseCase(PAGE_SIZE, page+1)
                    .onSuccess {
                        val newList = _elapsedSchedules.value.orEmpty() + it.myElapsedScheduleList
                        _elapsedSchedules.value = newList
                        _elapsedPageNo.value = it.pageNo
                        _isLastElapsed.value = it.last
                    }.onError {
                        Log.e("fetchNextElapsedSchedules", "onError ${it.message}")
                    }
            }
        }

    }

}