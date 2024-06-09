package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.NaverMapRepository
import kr.tekit.lion.daongil.domain.usecase.GetUserLocationRegionUseCase
import java.lang.IllegalArgumentException

class PharmacyMapViewModelFactory(): ViewModelProvider.Factory {

    private val getUserLocationRegionUseCase = GetUserLocationRegionUseCase(
        NaverMapRepository.create()
    )
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PharmacyMapViewModel::class.java)){
            return PharmacyMapViewModel(getUserLocationRegionUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}