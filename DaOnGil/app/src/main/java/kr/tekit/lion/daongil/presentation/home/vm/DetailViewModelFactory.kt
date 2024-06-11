package kr.tekit.lion.daongil.presentation.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlaceDetailInfoRepository
import kr.tekit.lion.daongil.domain.usecase.GetPlaceDetailInfoUseCase

class DetailViewModelFactory : ViewModelProvider.Factory{
    private val placeDetailInfoRepository = PlaceDetailInfoRepository.crate()

    private val getPlaceDetailInfoUseCase = GetPlaceDetailInfoUseCase(placeDetailInfoRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                getPlaceDetailInfoUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}