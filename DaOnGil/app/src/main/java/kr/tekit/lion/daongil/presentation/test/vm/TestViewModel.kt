package kr.tekit.lion.daongil.presentation.test.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class TestViewModel(
    private val placeDetailInfoUseCase: GetPlaceDetailInfoUseCase
): ViewModel() {
    init {
        viewModelScope.launch {
            placeDetailInfoUseCase(125266).onSuccess {
                Log.d("xczxczx", it.toString())
            }.onError {
                Log.d("xczxczx", it.toString())
            }
        }
    }

}