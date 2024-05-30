package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllDetailAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.InitAreaCodeInfoUseCase

class SearchMainViewModel(
    private val initAreaCodeInfoUseCase: InitAreaCodeInfoUseCase,
    private val getAllAreaCodeUseCase: GetAllAreaCodeUseCase,
    private val getAllDetailAreaCodeUseCase: GetAllDetailAreaCodeUseCase

): ViewModel() {
    init {
        viewModelScope.launch {
            initAreaCodeInfoUseCase()

            _areaCode.value = getAllAreaCodeUseCase()
        }
    }
    private val _areaCode = MutableStateFlow<List<AreaCode>>(emptyList())
    val areaCode: StateFlow<List<AreaCode>> get() = _areaCode

    private val _villageCode = MutableStateFlow<List<VillageCode>>(emptyList())
    val villageCode: StateFlow<List<VillageCode>> get() = _villageCode

    private val _physicalDisability = MutableStateFlow<List<String>>(emptyList())
    val physicalDisability: StateFlow<List<String>> get() = _physicalDisability

    fun onCompleteSelectArea(areaName: String) = viewModelScope.launch{
        val findAreaCode = areaCode.value.find { it.name == areaName }?.code
        if (findAreaCode != null){
            _villageCode.value = getAllDetailAreaCodeUseCase(findAreaCode)
        }
    }

    fun onSelectPhysicalDisabilityOption(options: List<String>){
        _physicalDisability.value = options
    }

    fun onClickResetIcon(){
        _physicalDisability.value = emptyList()
    }
}