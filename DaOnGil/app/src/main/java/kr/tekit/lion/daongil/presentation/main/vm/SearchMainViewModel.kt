package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchMainViewModel: ViewModel() {
    private val _physicalDisability = MutableStateFlow<List<String>>(emptyList())
    val physicalDisability: StateFlow<List<String>> get() = _physicalDisability

    fun onSelectPhysicalDisabilityOption(options: List<String>){
        _physicalDisability.value = options
    }

    fun onClickResetIcon(){
        _physicalDisability.value = emptyList()
    }
}