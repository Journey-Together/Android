package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.AedMapInfo
import kr.tekit.lion.daongil.domain.model.EmergencyMapInfo
import kr.tekit.lion.daongil.domain.model.HospitalMapInfo
import kr.tekit.lion.daongil.domain.usecase.emergency.GetEmergencyMapInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.GetUserLocationRegionUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class EmergencyMapViewModel(
    private val getUserLocationRegionUseCase: GetUserLocationRegionUseCase,
    private val getEmergencyMapInfoUseCase: GetEmergencyMapInfoUseCase
) : ViewModel() {

    private val _area = MutableLiveData<String?>()
    val area : LiveData<String?> = _area

    private val _emergencyMapInfo = MutableLiveData<List<EmergencyMapInfo>>()
    val emergencyMapInfo: LiveData<List<EmergencyMapInfo>> = _emergencyMapInfo

    fun getUserLocationRegion(coords: String) = viewModelScope.launch {
            getUserLocationRegionUseCase(coords).onSuccess {
                if (it.code == 0) {
                    _area.value = "${it.results[0].area} ${it.results[0].areaDetail}"
                    /*val regex = Regex(".*?(시|구|군)")
                    val matchResult = regex.find(it.results[0].areaDetail.toString())
                    val detailArea = matchResult?.value*/
                }
            }
        }

    fun getEmergencyMapInfo(area: String?, areaDetail: String?) =
        viewModelScope.launch {
            getEmergencyMapInfoUseCase(area, areaDetail).onSuccess {
                _emergencyMapInfo.value = it
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