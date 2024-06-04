package kr.tekit.lion.daongil.presentation.scheduleform.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date

class ScheduleFormViewModel : ViewModel() {
    private val _startDate = MutableLiveData<Date>()
    private val _endDate = MutableLiveData<Date>()
    private val _title = MutableLiveData<String>()

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


}