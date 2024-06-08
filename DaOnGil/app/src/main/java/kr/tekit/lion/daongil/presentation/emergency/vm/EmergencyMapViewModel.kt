package kr.tekit.lion.daongil.presentation.emergency.vm

import android.util.Log
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

    private val _areaDetail = MutableLiveData<String?>()
    val areaDetail: LiveData<String?> = _areaDetail

    private var areaSet = false
    private var areaDetailSet = false

    val combinedArea: MediatorLiveData<String> = MediatorLiveData<String>().apply {
        addSource(area) { newArea ->
            areaSet = true
            if (areaDetailSet) {  // 두 값이 모두 한 번 이상 설정된 경우에만 업데이트
                value = combineAreaAndDetail(newArea, areaDetail.value)
            }
        }
        addSource(areaDetail) { newAreaDetail ->
            areaDetailSet = true
            if (areaSet) {  // 두 값이 모두 한 번 이상 설정된 경우에만 업데이트
                value = combineAreaAndDetail(area.value, newAreaDetail)
            }
        }
    }

    private val _emergencyMapInfo = MutableLiveData<List<EmergencyMapInfo>>()
    val emergencyMapInfo : LiveData<List<EmergencyMapInfo>> = _emergencyMapInfo

    private val _aedMapInfo = MutableLiveData<List<AedMapInfo>>()
    val aedMapInfo : LiveData<List<AedMapInfo>> = _aedMapInfo

    fun getuserLocationRegion(coords: String) =
        viewModelScope.launch {
            getUserLocationRegionUseCase(coords).onSuccess {
                if (it.code == 0) {
                    /*val regex = Regex(".*?(시|구|군)")
                    val matchResult = regex.find(it.results[0].areaDetail.toString())
                    val detailArea = matchResult?.value*/
                    setArea(it.results[0].area, it.results[0].areaDetail,)
                }
            }
        }

    fun getEmergencyMapInfo(STAGE1: String?, STAGE2: String?) =
        viewModelScope.launch {
            getEmergencyMapInfoUseCase(STAGE1, STAGE2).onSuccess {
                _emergencyMapInfo.value = it
            }
        }

    fun getAedMapInfo(Q0: String?, Q1: String?)=
        viewModelScope.launch {
            var area = ""
            if(Q0 == "전북특별자치도"){
                area = "전라북도"
            } else {
                area = _area.value.toString()
            }
            getAedMapInfoUseCase(area, Q1).onSuccess {
                _aedMapInfo.value = it
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