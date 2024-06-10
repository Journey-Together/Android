package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AedRepository
import kr.tekit.lion.daongil.domain.repository.EmergencyRepository
import kr.tekit.lion.daongil.domain.repository.NaverMapRepository
import kr.tekit.lion.daongil.domain.usecase.GetAedMapInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.GetEmergencyMapInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.GetHospitalMapInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.GetUserLocationRegionUseCase
import java.lang.IllegalArgumentException

class EmergencyMapViewModelFactory(): ViewModelProvider.Factory {

    private val getUserLocationRegionUseCase = GetUserLocationRegionUseCase(
        NaverMapRepository.create()
    )

    private val getHospitalMapInfoUseCase = GetHospitalMapInfoUseCase(
        EmergencyRepository.create()
    )

    private val getAedMapInfoUseCase = GetAedMapInfoUseCase(
        AedRepository.create()
    )

    private val getEmergencyMapInfoUseCase = GetEmergencyMapInfoUseCase(
        getHospitalMapInfoUseCase,
        getAedMapInfoUseCase
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmergencyMapViewModel::class.java)){
            return EmergencyMapViewModel(getUserLocationRegionUseCase, getEmergencyMapInfoUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}