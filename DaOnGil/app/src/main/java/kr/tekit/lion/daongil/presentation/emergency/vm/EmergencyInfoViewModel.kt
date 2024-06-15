package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.EmergencyMessageInfo
import kr.tekit.lion.daongil.domain.usecase.emergency.GetEmergencyMessageUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class EmergencyInfoViewModel(
    private val emergencyMessageUseCase: GetEmergencyMessageUseCase,
) : ViewModel(){
    private val _messageList = MutableLiveData<List<EmergencyMessageInfo>>()
    val messageList: LiveData<List<EmergencyMessageInfo>> = _messageList


    fun getEmergencyMessage(hospitalId: String) =
        viewModelScope.launch {
            emergencyMessageUseCase(hospitalId).onSuccess {
                _messageList.value = it
            }
        }
    }
}
