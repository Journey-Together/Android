package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.NaverMapRepository
import kr.tekit.lion.daongil.domain.repository.PharmacyRepository
import kr.tekit.lion.daongil.domain.usecase.GetPharmacyMapInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.GetUserLocationRegionUseCase
import java.lang.IllegalArgumentException

class PharmacyMapViewModelFactory(): ViewModelProvider.Factory {

    private val getUserLocationRegionUseCase = GetUserLocationRegionUseCase(
        NaverMapRepository.create()
    )

    private val getPharmacyMapInfoUseCase = GetPharmacyMapInfoUseCase(
        PharmacyRepository.create()
    )
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PharmacyMapViewModel::class.java)){
            return PharmacyMapViewModel(getUserLocationRegionUseCase, getPharmacyMapInfoUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}