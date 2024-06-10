package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllSigunguCodeUseCase

class SearchMainViewModel(
    private val getAllAreaCodeUseCase: GetAllAreaCodeUseCase,
    private val getAllSigunguCodeUseCase: GetAllSigunguCodeUseCase

): ViewModel() {

    init {
        viewModelScope.launch {
            _areaCode.value = getAllAreaCodeUseCase()
        }
    }

    private val _areaCode = MutableStateFlow<List<AreaCode>>(emptyList())
    val areaCode get() = _areaCode.asStateFlow()

    private val _villageCode = MutableStateFlow<List<VillageCode>>(emptyList())
    val villageCode get() = _villageCode.asStateFlow()

    // BottomSheet 에서 선택된 항목을 들을 유지하기 위한 데이터
    private val _physicalDisabilityOptions = MutableStateFlow<List<Int>>(emptyList())
    val physicalDisabilityOptions get() = _physicalDisabilityOptions.asStateFlow()

    private val _hearingImpairmentOptions = MutableStateFlow<List<Int>>(emptyList())
    val hearingImpairmentOptions get() = _hearingImpairmentOptions.asStateFlow()

    private val _visualImpairmentOptions = MutableStateFlow<List<Int>>(emptyList())
    val visualImpairmentOptions get() = _visualImpairmentOptions.asStateFlow()

    private val _infantFamilyOptions = MutableStateFlow<List<Int>>(emptyList())
    val infantFamilyOptions get() = _infantFamilyOptions.asStateFlow()

    private val _elderlyPersonOptions = MutableStateFlow<List<Int>>(emptyList())
    val elderlyPersonOptions get() = _elderlyPersonOptions.asStateFlow()

    fun onCompleteSelectPhysicalDisabilityOptions(options: List<Int>){
        _physicalDisabilityOptions.value = options
    }

    fun onCompleteSelectHearingImpairmentOptions(options: List<Int>){
        _hearingImpairmentOptions.value = options
    }

    fun onCompleteSelectVisualImpairmentOptions(options: List<Int>){
        _visualImpairmentOptions.value = options
    }

    fun onCompleteSelectInfantFamilyOptions(options: List<Int>){
        _infantFamilyOptions.value = options
    }
    fun onCompleteElderlyPersonOptions(options: List<Int>){
        _elderlyPersonOptions.value = options
    }


    fun onCompleteSelectArea(areaName: String) = viewModelScope.launch{
        val findAreaCode = areaCode.value.find { it.name == areaName }?.code
        if (findAreaCode != null){
            _villageCode.value = getAllSigunguCodeUseCase(findAreaCode)
        }
    }

    fun onClickResetIcon(){
        _physicalDisabilityOptions.value = emptyList()
        _hearingImpairmentOptions.value = emptyList()
        _visualImpairmentOptions.value = emptyList()
        _infantFamilyOptions.value = emptyList()
        _elderlyPersonOptions.value = emptyList()
    }
}

sealed class Category(val type: Int) {
    data object PhysicalDisability : Category(1)
    data object VisualImpairment : Category(2)
    data object HearingImpairment : Category(3)
    data object InfantFamily : Category(4)
    data object ElderlyPeople : Category(5)
}
