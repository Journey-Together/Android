package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.usecase.areacode.InitAreaCodeInfoUseCase

class MainViewModel(
    private val initAreaCodeInfoUseCase: InitAreaCodeInfoUseCase
) : ViewModel() {
    init {
        viewModelScope.launch {
            initAreaCodeInfoUseCase()
        }
    }
}