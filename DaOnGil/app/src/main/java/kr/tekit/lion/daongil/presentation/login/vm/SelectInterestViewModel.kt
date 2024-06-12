package kr.tekit.lion.daongil.presentation.login.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.data.dto.remote.response.Interest.InterestTypeRes
import kr.tekit.lion.daongil.domain.model.InterestType
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.interestType.GetInterestTypeUseCase
import kr.tekit.lion.daongil.domain.usecase.interestType.UpdateInterestTypeUseCase

class SelectInterestViewModel(
    private val getInterestTypeUseCase: GetInterestTypeUseCase,
    private val updateInterestTypeUseCase: UpdateInterestTypeUseCase
) : ViewModel() {

    private val _interestType = MutableLiveData<List<InterestType>>()
    val interestType: LiveData<List<InterestType>> = _interestType

    private val _updateResult = MutableLiveData<Result<Unit>>()
    val updateResult: LiveData<Result<Unit>> = _updateResult

    // 고른 관심 유형을 가져오는 함수
    fun getInterestType() {
        viewModelScope.launch {
            val result = getInterestTypeUseCase()
            result.onSuccess {
                _interestType.value = it
                Log.d("getInterestType", it.toString())
            }.onError {
                Log.d("getInterestType", it.toString())
            }
        }
    }

    // 관심 유형을 업데이트하는 함수
    fun updateInterestType(interestType: InterestTypeRes) {
        viewModelScope.launch {
            val result = updateInterestTypeUseCase(interestType)
            result.onSuccess {
                Log.d("updateInterestType", it.toString())
                _updateResult.value = Result.success(Unit)
            }.onError { error ->
                Log.d("updateInterestType", "${error.message}")
                _updateResult.value = Result.failure(error)
                // 에러 처리 로직 추가 가능
                error.printStackTrace()
            }
        }
    }
}