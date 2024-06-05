package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmergencyMapViewModel(

) : ViewModel() {

    private val _area = MutableLiveData<String>()
    val area : LiveData<String> = _area

    private val _areaDetail = MutableLiveData<String>()
    val areaDetail: LiveData<String> = _areaDetail

    val combinedArea: MediatorLiveData<String> = MediatorLiveData<String>().apply {
        addSource(area) { value = combineAreaAndDetail(area.value, areaDetail.value) }
        addSource(areaDetail) { value = combineAreaAndDetail(area.value, areaDetail.value) }
    }

    private fun combineAreaAndDetail(area: String?, areaDetail: String?): String {
        return "${area ?: ""} ${areaDetail ?: ""}".trim()
    }

    fun setArea(area: String, areaDetail: String) {
        _area.value = area
        _areaDetail.value = areaDetail
    }
}