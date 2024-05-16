package kr.tekit.lion.daongil.presentation.main.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.areacode.GetAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.areacode.InitAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.base.onError
import kr.tekit.lion.daongil.usecase.base.onSuccess

class MainViewModel(
    private val initAreaCodeUseCase: InitAreaCodeUseCase,
    private val getAllAreaCodeUseCase: GetAllAreaCodeUseCase,
    private val getAreaCodeUseCase: GetAreaCodeUseCase
): ViewModel() {
    init {
        viewModelScope.launch {
            initAreaCodeUseCase(
                "t2ivQakqcZ/cvxzekT7Ra9Ja8J1N1lBKu6LqVkijMliEeoD1lLXU0Qei+V9AC8aMbNG+TjVkca70NqFB9akmSg==",
            ).onSuccess {
                Log.d("ApiTestResult", it.toString() )
            }.onError {
                Log.d("ApiTestResult", it.toString() )
            }
            Log.d("ApiTestResult", getAreaCodeUseCase("1").toString() )
        }
    }
}