package kr.tekit.lion.daongil.presentation.scheduleform.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.usecase.GetPlaceSearchResultUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import java.util.Date

class ScheduleFormViewModel(
    private val getPlaceSearchResultUseCase: GetPlaceSearchResultUseCase
) : ViewModel() {
    private val _startDate = MutableLiveData<Date?>()
    val startDate: LiveData<Date?> get() = _startDate

    private val _endDate = MutableLiveData<Date?>()
    val endDate: LiveData<Date?> get() = _endDate

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?> get() = _title

    private val _schedule = MutableLiveData<List<DailySchedule>?>()
    val schedule : LiveData<List<DailySchedule>?> get() = _schedule

    // 여행지 검색 화면 - 검색 결과 목록
    private val _placeSearchResult = MutableLiveData<PlaceSearchResult>()
    // read only
    val placeSearchResult : LiveData<PlaceSearchResult> get() = _placeSearchResult

    fun setStartDate(startDate: Date?){
        _startDate.value = startDate
    }

    fun setEndDate(endDate : Date?){
        _endDate.value = endDate
    }

    fun setTitle(title: String?){
        _title.value = title
    }

    fun setSchedule(schedule : List<DailySchedule>?){
        _schedule.value = schedule
    }

    fun addNewPlace(dayPosition:Int, placeId: Long){
        // to do - 서버에서 장소 정보를 받아온다.
        val newPlace = FormPlace(placeId, "image", "서울 경기도 부산 등등", "장소이름", listOf(1,2,4,5))
        _schedule.value?.get(dayPosition)?.dailyPlaces?.add(newPlace)
    }

    fun removePlace(dayPosition: Int, placePosition: Int){
        _schedule.value?.get(dayPosition)?.dailyPlaces?.removeAt(placePosition)
        _schedule.value?.let { // schedule data 갱신
            setSchedule(it)
        }
    }

    fun getPlaceSearchResult(word: String, page: Int, size: Int){
        // viewModelScope = ViewModel에 정의된 코루틴 스코프
        viewModelScope.launch {
            getPlaceSearchResultUseCase(word, page, size).onSuccess {
                // 검색 결과를 받아오면 _placeSearchResult에 값 갱신해준다.
                Log.d("getPlaceSearchResult", "onSuccess")
                Log.d("getPlaceSearchResult", it.toString())
                _placeSearchResult.value = it
            }.onError {
                Log.d("getPlaceSearchResult", "onError")
                Log.d("getPlaceSearchResult", it.toString())
            }
        }
    }


}