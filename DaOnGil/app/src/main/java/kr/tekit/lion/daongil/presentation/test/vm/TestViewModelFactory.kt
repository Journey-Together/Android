package kr.tekit.lion.daongil.presentation.test.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase

class TestViewModelFactory : ViewModelProvider.Factory{
    private val placeDetailInfoUseCase = GetPlaceDetailInfoUseCase(
        PlaceRepository.crate()
    )
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)){
            return TestViewModel(placeDetailInfoUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}