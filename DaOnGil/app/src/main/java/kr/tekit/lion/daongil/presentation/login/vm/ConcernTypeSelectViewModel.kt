package kr.tekit.lion.daongil.presentation.login.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.ConcernType
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.myinfo.UpdateConcernTypeUseCase

class ConcernTypeSelectViewModel(
    private val updateConcernTypeUseCase: UpdateConcernTypeUseCase
) : ViewModel() {

    fun selectConcernType(requestBody: ConcernType) = viewModelScope.launch {
        updateConcernTypeUseCase(requestBody).onSuccess {
            Log.d("selectConcernType", it.toString())
        }.onError {
            Log.d("selectConcernType", it.toString())
        }
    }
}