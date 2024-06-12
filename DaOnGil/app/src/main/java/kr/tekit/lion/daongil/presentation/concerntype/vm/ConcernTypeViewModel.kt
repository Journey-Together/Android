package kr.tekit.lion.daongil.presentation.concerntype.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.ConcernType
import kr.tekit.lion.daongil.domain.usecase.GetConcernTypeUseCase
import kr.tekit.lion.daongil.domain.usecase.UpdateConcernTypeUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class ConcernTypeViewModel(
    private val getConcernTypeUseCase: GetConcernTypeUseCase,
    private val updateConcernTypeUseCase: UpdateConcernTypeUseCase
) : ViewModel() {

    private val _concernType = MutableLiveData<ConcernType>()
    val concernType: LiveData<ConcernType> = _concernType

    init {
        getConcernType()
    }

    fun getConcernType() {
        viewModelScope.launch {
            getConcernTypeUseCase().onSuccess {
                _concernType.value = it
            }.onError {
                Log.d("getConcernType", it.toString())
            }
        }
    }

    fun updateConcernType(requestBody: ConcernType) {
        viewModelScope.launch {
            updateConcernTypeUseCase(requestBody).onSuccess {
            }.onError {
                Log.d("updateConcernType", it.toString())
            }
        }

        _concernType.value = _concernType.value?.copy(
            isPhysical = requestBody.isPhysical,
            isVisual = requestBody.isVisual,
            isHear = requestBody.isHear,
            isChild = requestBody.isChild,
            isElderly = requestBody.isElderly
        )
    }
}