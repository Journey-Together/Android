package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.EmergencyRepository
import kr.tekit.lion.daongil.domain.usecase.GetEmergencyMessageUseCase
import java.lang.IllegalArgumentException

class EmergencyInfoViewModelFactory(private val hospitalId: String): ViewModelProvider.Factory {

    private val emergencyMessageUseCase = GetEmergencyMessageUseCase(
        EmergencyRepository.create()
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmergencyInfoViewModel::class.java)){
            return EmergencyInfoViewModel(emergencyMessageUseCase, hospitalId) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}