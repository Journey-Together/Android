package kr.tekit.lion.daongil.presentation.main.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.areacode.GetAreaCodeInfoUseCase
import kr.tekit.lion.daongil.usecase.areacode.InitAreaCodeInfoUseCase
import kr.tekit.lion.daongil.usecase.base.onError
import kr.tekit.lion.daongil.usecase.base.onSuccess

class MainViewModel(
    private val getAllAreaCodeUseCase: GetAllAreaCodeUseCase,
    private val getAreaCodeInfoUseCase: GetAreaCodeInfoUseCase,
    private val initAreaCodeInfoUseCase: InitAreaCodeInfoUseCase
): ViewModel() {
    init {
        viewModelScope.launch {
            initAreaCodeInfoUseCase()
            Log.d("ApiTestResult", getAreaCodeInfoUseCase("1").toString() )
        }
    }
}