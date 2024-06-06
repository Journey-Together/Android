package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.usecase.GetUserLocationRegionUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class EmergencyMapViewModel(
    private val getUserLocationRegionUseCase: GetUserLocationRegionUseCase
) : ViewModel() {

    private val _area = MutableLiveData<String?>()
    val area : LiveData<String?> = _area

    private val _areaDetail = MutableLiveData<String?>()
    val areaDetail: LiveData<String?> = _areaDetail

    val combinedArea: MediatorLiveData<String> = MediatorLiveData<String>().apply {
        addSource(area) { value = combineAreaAndDetail(area.value, areaDetail.value) }
        addSource(areaDetail) { value = combineAreaAndDetail(area.value, areaDetail.value) }
    }

    fun getuserLocationRegion(coords: String) =
        viewModelScope.launch {
            getUserLocationRegionUseCase(coords).onSuccess {
                if (it.code == 0) {
                    setArea(it.results[0].area, it.results[0].areaDetail)
                }
            }
        }


    private fun combineAreaAndDetail(area: String?, areaDetail: String?): String {
        return "${area ?: ""} ${areaDetail ?: ""}".trim()
    }

    fun setArea(area: String?, areaDetail: String?) {
        _area.value = area
        _areaDetail.value = areaDetail
    }
}