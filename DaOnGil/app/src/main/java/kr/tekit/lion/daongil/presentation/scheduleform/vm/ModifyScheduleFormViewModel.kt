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
import kr.tekit.lion.daongil.domain.model.DailyPlan
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.model.PlaceSearchInfoList
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.model.ScheduleDetail
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceBookmarkListUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceSearchResultUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.ModifyScheduleUseCase
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ModifyScheduleFormViewModel (
    private val getScheduleDetailInfoUseCase: GetScheduleDetailInfoUseCase,
    private val getPlaceSearchResultUseCase: GetPlaceSearchResultUseCase,
    private val getPlaceDetailInfoUseCase: GetPlaceDetailInfoUseCase,
    private val getPlaceBookmarkListUseCase: GetPlaceBookmarkListUseCase,
    private val modifyScheduleUseCase: ModifyScheduleUseCase
) : ViewModel() {

    // 수정 전 일정 정보
    private val _originalSchedule = MutableLiveData<ScheduleDetail>()

    // UI에 보여지는 정보 (시작일, 종료일, 여행명, 스케쥴)
    private val _startDate = MutableLiveData<Date?>()
    val startDate: LiveData<Date?> get() = _startDate

    private val _endDate = MutableLiveData<Date?>()
    val endDate: LiveData<Date?> get() = _endDate
    
    // 기간이 한 번이라도 수정되었는지 확인하는 flag
    val _isPeriodChanged = MutableLiveData<Boolean>(false)

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?> get() = _title

    private val _schedule = MutableLiveData<List<DailySchedule>?>()
    val schedule : LiveData<List<DailySchedule>?> get() = _schedule

    // 북마크한 여행지 목록
    private val _bookmarkedPlaces = MutableLiveData<List<BookmarkedPlace>>()
    val bookmarkedPlaces: LiveData<List<BookmarkedPlace>> get() = _bookmarkedPlaces

    // 여행지 검색 결과 목록
    private val _placeSearchResult = MutableLiveData<PlaceSearchResult>()

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

    fun setTitle(title: String?){
        _title.value = title
    }

    fun setStartDate(startDate: Date?){
        _startDate.value = startDate
    }

    fun setEndDate(endDate : Date?){
        _endDate.value = endDate
    }

    fun hasStartDate() : Boolean {
        return startDate.value != null
    }

    fun isLastPage(): Boolean {
        return _placeSearchResult.value?.last ?: true
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

    fun initScheduleDetailInfo(planId: Long) {
        viewModelScope.launch {
            getScheduleDetailInfoUseCase.invoke(planId).onSuccess {
                _originalSchedule.value = it
                initScheduleData()
            }
        }
    }

    private fun initScheduleData() {
        _originalSchedule.value?.let {
            _startDate.value = dateStringToDate(it.startDate)
            _endDate.value = dateStringToDate(it.endDate)
            _title.value = it.title
            _schedule.value = processScheduleInfoData(it.dailyPlans)
        }
    }

    private fun dateStringToDate(dateString: String) : Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date = formatter.parse(dateString) ?: Date()

        return date
    }

    private fun processScheduleInfoData(plans: List<DailyPlan>) : List<DailySchedule>{
        return plans.mapIndexed { index, dailyPlan ->
            DailySchedule(
                dailyIdx = index,
                dailyDate = formatDateValue(
                    dateString = dailyPlan.dailyPlanDate,
                    pattern = "M월 d일 (E)"
                ),
                dailyPlaces = dailyPlan.schedulePlaces.map { schedulePlace ->
                    FormPlace(
                        placeId = schedulePlace.placeId,
                        placeName = schedulePlace.name,
                        placeImage = "",
                        placeCategory = schedulePlace.category

                    )
                }
            )
        }
    }

    private fun getDayNString(date: Date, index: Int, pattern: String): String {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, index)

        val dayString = SimpleDateFormat(pattern, Locale.KOREAN).format(calendar.time)

        return dayString
    }

    private fun formatDateValue(dateString: String = "", dateObj: Date? = null, pattern: String): String {
        val date = when {
            dateString.isNotEmpty() -> dateStringToDate(dateString)
            dateObj != null -> dateObj
            else -> throw IllegalArgumentException("date error")
        }
        val dateFormat = SimpleDateFormat(pattern, Locale.KOREA)
        val formattedDate = dateFormat.format(date)

        return formattedDate
    }

    fun formatPickedDates() : String {
        val datePattern = "yyyy.MM.dd(E)"
        val startDateFormatted =
            _startDate.value?.let { formatDateValue(dateObj = it, pattern = datePattern) } ?: ""
        val endDateFormatted =
            _endDate.value?.let { formatDateValue(dateObj = it, pattern = datePattern) } ?: ""
        return "$startDateFormatted - $endDateFormatted"
    }

    fun isPeriodReset(){
        val originalStart = _originalSchedule.value?.startDate?.let { dateStringToDate(it) }
        val currentStart = _startDate.value

        val originalEnd =_originalSchedule.value?.endDate?.let { dateStringToDate(it) }
        val currentEnd =_endDate.value

        val isStartDateChanged = originalStart==currentStart
        val isEndDateChanged = originalEnd==currentEnd

        // 수정되기 전 일정의 여행 기간과, 새로 선택한 날짜가 다른 경우
        if(!isStartDateChanged || !isEndDateChanged){
            initScheduleList()
            _isPeriodChanged.value = true
        }else{
            // 여행 기간은 똑같이 설정되었지만, 이미 기간이 변경된 적이 있다면
            val isChanged = _isPeriodChanged.value ?: false
            if(isChanged) initScheduleList()
        }
    }

    private fun initScheduleList() {
        // 선택된 기간을 기준으로 리스트 초기화
        val startDate = _startDate.value
        val endDate = _endDate.value
        if (startDate != null && endDate != null) {
            val diffInMillies = kotlin.math.abs(endDate.time - startDate.time)
            val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillies).toInt()

            val schedule = mutableListOf<DailySchedule>()
            for (day in 0..diffInDays) {
                val dateInfo = getDayNString(startDate, day, "M월 d일 (E)")
                schedule.add(DailySchedule(day + 1, dateInfo, mutableListOf<FormPlace>()))
            }

            _schedule.value = schedule.toList()
            Log.d("test1234", "_schedule.value: ${_schedule.value}")
        }
    }

    fun getScheduleTitle(): String {
        return _title.value ?: ""
    }

    fun getSchedulePeriod(): String {
        val datePattern = "yyyy-MM-dd"
        val startDateString = _startDate.value?.let { formatDateValue(dateObj = it, pattern = datePattern) }
        val endDateString = _endDate.value?.let { formatDateValue(dateObj = it, pattern = datePattern) }

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

    fun getPlaceSearchResult(word: String, page: Int){
        _keyword.value = word

        viewModelScope.launch {
            getPlaceSearchResultUseCase(word, page)
                .onSuccess {
                    _placeSearchResult.value = it
                }.onError {
                    Log.e("getPlaceSearchResult", "${it.message}")
                }
        }
    }

    fun fetchNextPlaceResults() {
        val page = _placeSearchResult.value?.pageNo
        val keyword = _keyword.value

        if (keyword != null && page != null) {
            viewModelScope.launch {
                getPlaceSearchResultUseCase(keyword, page + 1)
                    .onSuccess {
                        val newList =
                            _placeSearchResult.value?.placeInfoList.orEmpty() + it.placeInfoList
                        val updatedResult = it.copy(placeInfoList = newList)
                        _placeSearchResult.value = updatedResult
                    }
                    .onError {
                        Log.e("fetchNextPlaceResults", "onError ${it.message}")
                    }
            }
        }
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

    fun getPlaceId(selectedPlacePosition: Int): Long {
        val placeId = _placeSearchResult.value?.placeInfoList?.get(selectedPlacePosition)?.placeId ?: -1L

        return placeId
    }

    fun submitRevisedSchedule(planId: Long, callback: (Boolean, Boolean) -> Unit) {
        val datePattern = "yyyy-MM-dd"
        val title = _title.value
        val startDateString = _startDate.value?.let { formatDateValue(dateObj = it, pattern = datePattern) }
        val endDateString = _endDate.value?.let { formatDateValue(dateObj = it, pattern = datePattern) }
        val dailyPlace = getDailyPlaceList()

        if (title != null && startDateString != null && endDateString != null) {
            val revisedPlan = NewPlan(title, startDateString, endDateString, dailyPlace)

            var requestFlag = false

            viewModelScope.launch {
                val success = try {
                    modifyScheduleUseCase(planId, revisedPlan).onSuccess {
                        requestFlag = true
                    }.onError {
                        Log.d("submitRevisedSchedule", "onError : ${it.message}")
                    }
                    true
                } catch (e: Exception) {
                    Log.d("submitRevisedSchedule", "${e.message}")
                    false
                }
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
                val date = getDayNString(startDate, index, "yyyy-MM-dd")
                val places = mutableListOf<Long>()
                dailySchedule.dailyPlaces.forEach {
                    places.add(it.placeId)
                }
                dailyPlaceList.add(DailyPlace(date, places))
            }
        }

        return dailyPlaceList.toList()
    }

}