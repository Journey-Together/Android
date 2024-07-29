package kr.tekit.lion.daongil.presentation.scheduleform.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.BookmarkedPlace
import kr.tekit.lion.daongil.domain.model.DailyPlan
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.domain.model.PlaceSearchInfoList
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.model.ScheduleDetail
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceBookmarkListUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceSearchResultUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailInfoUseCase
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ModifyScheduleFormViewModel (
    private val getScheduleDetailInfoUseCase: GetScheduleDetailInfoUseCase,
    private val getPlaceSearchResultUseCase: GetPlaceSearchResultUseCase,
    private val getPlaceDetailInfoUseCase: GetPlaceDetailInfoUseCase,
    private val getPlaceBookmarkListUseCase: GetPlaceBookmarkListUseCase,
    // + patch schedule use case
) : ViewModel() {

    // 수정 전 일정 정보
    private val _originalSchedule = MutableLiveData<ScheduleDetail>()

    // UI에 보여지는 정보 (시작일, 종료일, 여행명, 스케쥴)
    private val _startDate = MutableLiveData<Date?>()
    val startDate: LiveData<Date?> get() = _startDate

    private val _endDate = MutableLiveData<Date?>()
    val endDate: LiveData<Date?> get() = _endDate

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
                dailyDate = getDayNString(dailyPlan.dailyPlanDate, null, index),
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

    private fun getDayNString(dateString: String = "", dateInfo: Date? = null, index: Int): String {
        val date = when {
            dateString.isNotEmpty() -> dateStringToDate(dateString)
            dateInfo != null -> dateInfo
            else -> throw IllegalArgumentException("date error")
        }

        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, index)

        val dayString = SimpleDateFormat("M월 d일 (E)", Locale.KOREAN).format(calendar.time)

        return dayString
    }

    private fun formatDateValue(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd(E)", Locale.KOREA)
        val formattedDate = dateFormat.format(date)

        return formattedDate
    }

    fun formatPickedDates() : String {
        val startDateFormatted = _startDate.value?.let { formatDateValue(it) } ?: ""
        val endDateFormatted = _endDate.value?.let { formatDateValue(it) } ?: ""
        return "$startDateFormatted - $endDateFormatted"
    }

    fun isPeriodReset(){
        val originalStart = _originalSchedule.value?.startDate?.let { dateStringToDate(it) }
        val currentStart = _startDate.value

        val originalEnd =_originalSchedule.value?.endDate?.let { dateStringToDate(it) }
        val currentEnd =_endDate.value

        val isStartDateChanged = originalStart==currentStart
        val isEndDateChanged = originalEnd==currentEnd

        if(!isStartDateChanged || !isEndDateChanged){
            initScheduleList()
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
                val dateInfo = getDayNString("", startDate, day)
                schedule.add(DailySchedule(day + 1, dateInfo, mutableListOf<FormPlace>()))
            }

            _schedule.value = schedule.toList()
            Log.d("test1234", "_schedule.value: ${_schedule.value}")
        }
    }
}