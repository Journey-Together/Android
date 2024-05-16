package kr.tekit.lion.daongil.presentation.main.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.usecase.GetAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.base.onError
import kr.tekit.lion.daongil.usecase.base.onSuccess

class MainViewModel(
    private val getAreaCodeUseCase: GetAreaCodeUseCase
): ViewModel() {
    init {
        viewModelScope.launch {
            getAreaCodeUseCase(
                    serviceKey = "t2ivQakqcZ/cvxzekT7Ra9Ja8J1N1lBKu6LqVkijMliEeoD1lLXU0Qei+V9AC8aMbNG+TjVkca70NqFB9akmSg==",
                    pageNo = "2",
            ).onSuccess {
                Log.d("AreaCodeRequestResult", it.toString())
            }.onError {
                Log.d("AreaCodeRequestResult", it.toString())
            }
        }
    }
}