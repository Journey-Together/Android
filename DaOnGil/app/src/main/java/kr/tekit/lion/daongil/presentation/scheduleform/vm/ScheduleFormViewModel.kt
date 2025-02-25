package kr.tekit.lion.daongil.presentation.scheduleform.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.BookmarkedPlace
import kr.tekit.lion.daongil.domain.model.DailyPlace
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.model.PlaceSearchInfoList
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.usecase.plan.AddNewPlanUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceBookmarkListUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceSearchResultUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFormViewModel(
    private val getPlaceSearchResultUseCase: GetPlaceSearchResultUseCase,
    private val getPlaceDetailInfoUseCase: GetPlaceDetailInfoUseCase,
    private val addNewPlanUseCase: AddNewPlanUseCase,
    private val getPlaceBookmarkListUseCase: GetPlaceBookmarkListUseCase
) : ViewModel() {

    private val _startDate = MutableLiveData<Date?>()
    val startDate: LiveData<Date?> get() = _startDate

    private val _endDate = MutableLiveData<Date?>()
    val endDate: LiveData<Date?> get() = _endDate

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?> get() = _title

    private val _schedule = MutableLiveData<List<DailySchedule>?>()
    val schedule : LiveData<List<DailySchedule>?> get() = _schedule

    private val _bookmarkedPlaces = MutableLiveData<List<BookmarkedPlace>>()
    val bookmarkedPlaces: LiveData<List<BookmarkedPlace>> get() = _bookmarkedPlaces

    // 여행지 검색 화면 - 검색 결과 목록 + pageNo(0~), pageSize(itemSize), totalPages, last (t/f)
    private val _placeSearchResult = MutableLiveData<PlaceSearchResult>()
    // read only
    val placeSearchResult : LiveData<PlaceSearchResult> get() = _placeSearchResult

    private val _keyword = MutableLiveData<String>()

    // 검색 결과 수와 장소 목록을 하나의 List로 관리
    private val _searchResultsWithNum = MediatorLiveData<List<PlaceSearchInfoList>>().apply {
        // _placeSearchResult: 이 값이 변경될 때마다 값 update
        addSource(_placeSearchResult) {
            val combinedList = mutableListOf<PlaceSearchInfoList>()
            combinedList.add(it.totalElements)
            combinedList.addAll(it.placeInfoList)
            value = combinedList
        }
    }
    val searchResultsWithNum : LiveData<List<PlaceSearchInfoList>> get() = _searchResultsWithNum

    init {
        getBookmarkedPlaceList()
    }

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

    fun hasStartDate() : Boolean {
        return startDate.value != null
    }

    fun getScheduleTitle(): String {
        return _title.value ?: ""
    }

    fun getSchedulePeriod(): String {
        val startDateString = _startDate.value?.let { formatDateValue(it) }
        val endDateString = _endDate.value?.let { formatDateValue(it) }

        return "$startDateString - $endDateString"
    }

    private fun addNewPlace(newPlace:FormPlace, dayPosition:Int){
        // 업데이트 될 기존 데이터
        val updatedSchedule = _schedule.value?.toMutableList()
        val daySchedule = updatedSchedule?.get(dayPosition)

        if (daySchedule != null) {
            // 업데이트할 날짜의 장소 목록
            val updatedPlaces = daySchedule.dailyPlaces.toMutableList()
            // 검색 결과에서 선택한 장소 정보를 추가
            updatedPlaces.add(newPlace)
            // copy = 일부 속성만 변경할 수 있게 해준다.
            // 여기선 dailyPlaces 속성만 updatedPlaces 로 바꿔준다.
            updatedSchedule[dayPosition] = daySchedule.copy(dailyPlaces = updatedPlaces)
            // 데이터 갱신
            _schedule.value = updatedSchedule
        }
    }

    fun removePlace(dayPosition: Int, placePosition: Int){
        // 하나의 장소를 삭제할 예정인 기존 데이터
        val removedSchedule = _schedule.value?.toMutableList()
        val daySchedule = removedSchedule?.get(dayPosition)

        if(daySchedule != null){
            // 하나의 장소를 삭제할 날짜의 장소 목록
            val removedPlaces = daySchedule.dailyPlaces.toMutableList()
            // 선택된 장소 정보를 List에서 제거
            removedPlaces.removeAt(placePosition)
            // 수정된 데이터를 반영해준다.
            removedSchedule[dayPosition] = daySchedule.copy(dailyPlaces = removedPlaces)
            // 데이터 갱신
            _schedule.value = removedSchedule
        }
    }

    fun getPlaceSearchResult(word: String, page: Int){
        _keyword.value = word

        // viewModelScope = ViewModel에 정의된 코루틴 스코프
        viewModelScope.launch {
            getPlaceSearchResultUseCase(word, page)
                .onSuccess {
                // 검색 결과를 받아오면 _placeSearchResult에 값을 갱신해준다
                _placeSearchResult.value = it
            }.onError {
                //Log.e("getPlaceSearchResult", "onError ${it.message}")
            }
        }
    }

    fun isLastPage(): Boolean {
        return _placeSearchResult.value?.last ?: true
    }

    fun fetchNextPlaceResults() {
        val page = _placeSearchResult.value?.pageNo
        val keyword = _keyword.value

        if (keyword != null && page != null) {
            viewModelScope.launch {
                getPlaceSearchResultUseCase(keyword, page + 1)
                    .onSuccess {
                        // orEmpty() : null 인 경우 빈 리스트로 처리해준다.
                        val newList =
                            _placeSearchResult.value?.placeInfoList.orEmpty() + it.placeInfoList
                        // 'placeInfoList'만 갱신
                        val updatedResult = it.copy(placeInfoList = newList)
                        _placeSearchResult.value = updatedResult
                    }
                    .onError {
                        Log.e("fetchNextPlaceResults", "onError ${it.message}")
                    }
            }
        }
    }

    fun isPlaceAlreadyAdded(
        dayPosition: Int,
        selectedPlacePosition: Int,
        isBookmarkedPlace: Boolean
    ): Boolean {
        val placeId = if (isBookmarkedPlace) {
            _bookmarkedPlaces.value?.get(selectedPlacePosition)?.bookmarkedPlaceId
        } else {
            _placeSearchResult.value?.placeInfoList?.get(selectedPlacePosition)?.placeId
        }

        // 선택한 관광지 정보가 같은 날에 추가된 경우
        val daySchedule = _schedule.value?.get(dayPosition)?.dailyPlaces
        daySchedule?.forEach {
            if (it.placeId == placeId) {
                return true
            }
        }
        return false
    }

    fun getSearchedPlaceDetailInfo(
        dayPosition: Int,
        selectedPlacePosition: Int,
        isBookmarkedPlace: Boolean
    ) {
        val placeId = if (isBookmarkedPlace) {
            _bookmarkedPlaces.value?.get(selectedPlacePosition)?.bookmarkedPlaceId
        } else {
            _placeSearchResult.value?.placeInfoList?.get(selectedPlacePosition)?.placeId
        }

        viewModelScope.launch {
            placeId?.let {
                getPlaceDetailInfoUseCase(placeId).onSuccess {
                    val formPlace =
                        FormPlace(it.placeId, it.image, it.name, it.category)
                    addNewPlace(formPlace, dayPosition)

                }.onError {
                    //Log.e("getSearchedPlaceDetailInfo", "placeId $placeId onError ${it.message}")
                }
            }
        }
    }

    fun submitNewPlan(callback: (Boolean, Boolean) -> Unit) {
        val title = _title.value
        val startDateString = _startDate.value?.let { formatDateValue(it) }
        val endDateString = _endDate.value?.let { formatDateValue(it) }
        val dailyPlace = getDailyPlaceList()

        if (title != null && startDateString != null && endDateString != null) {
            val newPlan = NewPlan(title, startDateString, endDateString, dailyPlace)

            var requestFlag = false

            viewModelScope.launch {
                val success = try {
                    addNewPlanUseCase(newPlan).onSuccess {
                        requestFlag = true
                    }.onError {
                        Log.d("submitNewPlan", "onError : ${it}")
                    }
                    true
                } catch (e: Exception) {
                    Log.d("submitNewPlan", "Error: ${e.message}")
                    false
                }
                // 작업한 결과가 끝나면 success에 true를 반환해준다.
                callback(success, requestFlag)
            }
        }
    }


    private fun getDailyPlaceList() : List<DailyPlace>{
        val dailyPlaceList = mutableListOf<DailyPlace>()
        val schedule = _schedule.value
        val startDate = _startDate.value

        startDate?.let {
            schedule?.forEachIndexed { index, dailySchedule ->
                val date = getDayNString(startDate, index)
                val places = mutableListOf<Long>()
                dailySchedule.dailyPlaces.forEach {
                    places.add(it.placeId)
                }
                dailyPlaceList.add(DailyPlace(date, places))
            }
        }

        return dailyPlaceList.toList()
    }

    private fun formatDateValue(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val formattedDate = dateFormat.format(date)

        return formattedDate
    }

    private fun getDayNString(date: Date, n: Int): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, n)

        val dayString = formatDateValue(calendar.time)

        return dayString
    }

    private fun getBookmarkedPlaceList(){
        viewModelScope.launch {
            getPlaceBookmarkListUseCase().onSuccess {
                _bookmarkedPlaces.postValue(it)
            }.onError {
                Log.e("getBookmarkedPlaceList", "onError $it")
            }
        }
    }

    fun getPlaceId(selectedPlacePosition: Int): Long {
        val placeId = _placeSearchResult.value?.placeInfoList?.get(selectedPlacePosition)?.placeId ?: -1L

        return placeId
    }

}