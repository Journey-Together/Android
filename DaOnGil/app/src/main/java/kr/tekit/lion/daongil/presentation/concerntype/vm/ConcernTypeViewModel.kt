package kr.tekit.lion.daongil.presentation.concerntype.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.ConcernType
import kr.tekit.lion.daongil.domain.usecase.GetConcernTypeUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class ConcernTypeViewModel(
    private val getConcernTypeUseCase: GetConcernTypeUseCase
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
}