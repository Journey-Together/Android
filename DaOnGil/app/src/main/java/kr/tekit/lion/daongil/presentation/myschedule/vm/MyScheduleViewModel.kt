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

    private val _upcomingSchedules = MutableLiveData<List<MyUpcomingScheduleInfo>?>()
    val upcomingSchedules : LiveData<List<MyUpcomingScheduleInfo>?> get() = _upcomingSchedules

    private val _upcomingPageNo = MutableLiveData<Int>()
    private val _isLastUpcoming = MutableLiveData<Boolean>()

    private val _elapsedSchedules = MutableLiveData<List<MyElapsedScheduleInfo>?>()
    val elapsedSchedules : LiveData<List<MyElapsedScheduleInfo>?> get() = _elapsedSchedules

    private val _elapsedPageNo = MutableLiveData<Int>()
    private val _isLastElapsed = MutableLiveData<Boolean>()

    init {
        getMyUpcomingScheduleList(0)
        getMyElapsedScheduleList(0)
    }
    private fun setUpcomingPageNo(pageNum: Int){
        _upcomingPageNo.value = pageNum
    }

    private fun setElapsedPageNo(pageNum: Int){
        _elapsedPageNo.value = pageNum
    }

    fun getMyUpcomingScheduleList(page: Int) {
        setUpcomingPageNo(page)
        viewModelScope.launch {
            getMyUpcomingSchedulesUseCase(page)
                .onSuccess {
                    _upcomingSchedules.value = it.myUpcomingScheduleList
                    _isLastUpcoming.value = it.last
                }.onError {
                    Log.e("getMyUpcomingScheduleList", "onError ${it.message}")
                }
        }
    }

    fun getMyElapsedScheduleList(page: Int) {
        setElapsedPageNo(page)
        viewModelScope.launch {
            getMyElapsedSchedulesUseCase(page)
                .onSuccess {
                    _elapsedSchedules.value = it.myElapsedScheduleList
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
            setUpcomingPageNo(page+1) // 페이지 번호 갱신

            viewModelScope.launch {
                getMyUpcomingSchedulesUseCase(page+1)
                    .onSuccess {
                        val newList = _upcomingSchedules.value.orEmpty() + it.myUpcomingScheduleList
                        _upcomingSchedules.value = newList
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
            setElapsedPageNo(page+1)

            viewModelScope.launch {
                getMyElapsedSchedulesUseCase(page+1)
                    .onSuccess {
                        val newList = _elapsedSchedules.value.orEmpty() + it.myElapsedScheduleList
                        _elapsedSchedules.value = newList
                        _isLastElapsed.value = it.last
                    }.onError {
                        Log.e("fetchNextElapsedSchedules", "onError ${it.message}")
                    }
            }
        }
    }

}