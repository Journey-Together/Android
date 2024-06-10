package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.PharmacyMapInfo
import kr.tekit.lion.daongil.domain.usecase.GetPharmacyMapInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.GetUserLocationRegionUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class PharmacyMapViewModel(
    private val getUserLocationRegionUseCase: GetUserLocationRegionUseCase,
    private val getPharmacyMapInfoUseCase: GetPharmacyMapInfoUseCase
) : ViewModel() {

    private val _area = MutableLiveData<String?>()
    val area : LiveData<String?> = _area

    private val _pharmacyMapInfo = MutableLiveData<List<PharmacyMapInfo>>()
    val pharmacyMapInfo : LiveData<List<PharmacyMapInfo>> = _pharmacyMapInfo

    fun getPharmacyMapInfo(Q0: String?, Q1: String?) =
        viewModelScope.launch {
            val areaDetail = if (Q0 == "세종특별자치시") null else Q1
            getPharmacyMapInfoUseCase(Q0, Q1).onSuccess {
                _pharmacyMapInfo.value = it
            }
        }
    fun getUserLocationRegion(coords: String) =
        viewModelScope.launch {
            getUserLocationRegionUseCase(coords).onSuccess {
                if (it.code == 0) {
                    _area.value = "${it.results[0].area} ${it.results[0].areaDetail}"
                }
            }
        }

    fun setArea(area: String?, areaDetail: String?) {
        if(areaDetail.isNullOrEmpty()){
            _area.value = "$area"
        } else {
            _area.value = "$area $areaDetail"
        }
    }

}