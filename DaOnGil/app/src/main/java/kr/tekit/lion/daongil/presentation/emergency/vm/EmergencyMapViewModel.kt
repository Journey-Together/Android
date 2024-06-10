package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.AedMapInfo
import kr.tekit.lion.daongil.domain.model.EmergencyMapInfo
import kr.tekit.lion.daongil.domain.usecase.GetAedMapInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.GetEmergencyMapInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.GetUserLocationRegionUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class EmergencyMapViewModel(
    private val getUserLocationRegionUseCase: GetUserLocationRegionUseCase,
    private val getEmergencyMapInfoUseCase: GetEmergencyMapInfoUseCase,
    private val getAedMapInfoUseCase: GetAedMapInfoUseCase
) : ViewModel() {

    private val _area = MutableLiveData<String?>()
    val area : LiveData<String?> = _area

    private val _areaUpdate = MutableLiveData<Unit>()
    val areaUpdate : LiveData<Unit> = _areaUpdate

    private val _emergencyMapInfo = MutableLiveData<List<EmergencyMapInfo>>()
    val emergencyMapInfo : LiveData<List<EmergencyMapInfo>> = _emergencyMapInfo

    private val _aedMapInfo = MutableLiveData<List<AedMapInfo>>()
    val aedMapInfo : LiveData<List<AedMapInfo>> = _aedMapInfo

    // 두 개의 LiveData<Boolean>을 사용
    private val _aedMapInfoUpdate = MutableLiveData<Boolean>()
    val aedMapInfoUpdate: LiveData<Boolean> = _aedMapInfoUpdate

    private val _emergencyMapInfoUpdate = MutableLiveData<Boolean>()
    val emergencyMapInfoUpdate: LiveData<Boolean> = _emergencyMapInfoUpdate

    // 두 개의 LiveData가 모두 업데이트된 후 관찰할 MediatorLiveData
    private val _mapInfoUpdate = MediatorLiveData<Unit>()
    val mapInfoUpdate: LiveData<Unit> = _mapInfoUpdate

    init {
        _mapInfoUpdate.addSource(_aedMapInfoUpdate) { it ->
            if (it == true && _emergencyMapInfoUpdate.value == true) {
                _mapInfoUpdate.value = Unit
                _aedMapInfoUpdate.value = false
                _emergencyMapInfoUpdate.value = false
            }
        }

        _mapInfoUpdate.addSource(_emergencyMapInfoUpdate) { it ->
            if (it == true && _aedMapInfoUpdate.value == true) {
                _mapInfoUpdate.value = Unit
                _aedMapInfoUpdate.value = false
                _emergencyMapInfoUpdate.value = false
            }
        }
    }

    fun getUserLocationRegion(coords: String) =
        viewModelScope.launch {
            getUserLocationRegionUseCase(coords).onSuccess {
                if (it.code == 0) {
                    _area.value = "${it.results[0].area} ${it.results[0].areaDetail}"
                    /*val regex = Regex(".*?(시|구|군)")
                    val matchResult = regex.find(it.results[0].areaDetail.toString())
                    val detailArea = matchResult?.value*/
                }
            }
        }

    fun getEmergencyMapInfo(STAGE1: String?, STAGE2: String?) =
        viewModelScope.launch {
            val areaDetail = if (STAGE1 == "세종특별자치시") null else STAGE2
            getEmergencyMapInfoUseCase(STAGE1, areaDetail).onSuccess {
                _emergencyMapInfo.value = it
                _emergencyMapInfoUpdate.value = true
            }
        }

    fun getAedMapInfo(Q0: String?, Q1: String?)=
        viewModelScope.launch {
            val area = if (Q0 == "전북특별자치도") "전라북도" else Q0
            val areaDetail = if (Q0 == "세종특별자치시") null else Q1
            getAedMapInfoUseCase(area, areaDetail).onSuccess {
                _aedMapInfo.value = it
                _aedMapInfoUpdate.value = true
            }
        }

    fun setArea(area: String?, areaDetail: String?) {
        if(areaDetail.isNullOrEmpty()){
            _area.value = "$area"
        } else {
            _area.value = "$area $areaDetail"
        }
        _areaUpdate.value = Unit
    }
}