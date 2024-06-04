package kr.tekit.lion.daongil.presentation.scheduleform.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import java.util.Date

class ScheduleFormViewModel : ViewModel() {
    private val _startDate = MutableLiveData<Date>()
    private val _endDate = MutableLiveData<Date>()
    private val _title = MutableLiveData<String>()
    private val _schedule = MutableLiveData<List<DailySchedule>>()

    val startDate: LiveData<Date> get() = _startDate

    fun setStartDate(startDate: Date){
        _startDate.value = startDate
    }

    val endDate: LiveData<Date> get() = _endDate

    fun setEndDate(endDate : Date){
        _endDate.value = endDate
    }

    val title: LiveData<String> get() = _title

    fun setTitle(title: String){
        _title.value = title
    }

    val schedule : LiveData<List<DailySchedule>> get() = _schedule

    fun setSchedule(schedule : List<DailySchedule>){
        _schedule.value = schedule
    }

    fun addNewPlace(dayPosition:Int, placeId: Int){
        // to do - 서버에서 장소 정보를 받아온다.
        val newPlace = FormPlace(placeId, "image", "서울 경기도 부산 등등", "장소이름", listOf(1,2,4,5))
        _schedule.value?.get(dayPosition)?.dailyPlaces?.add(newPlace)
    }

}