package kr.tekit.lion.daongil.presentation.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.place.WritePlaceReviewDataUseCase

class WriteReviewViewModelFactory : ViewModelProvider.Factory {
    private val placeRepository = PlaceRepository.create()
    private val writePlaceReviewDataUseCase = WritePlaceReviewDataUseCase(placeRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteReviewViewModel::class.java)) {
            return WriteReviewViewModel(
                writePlaceReviewDataUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}