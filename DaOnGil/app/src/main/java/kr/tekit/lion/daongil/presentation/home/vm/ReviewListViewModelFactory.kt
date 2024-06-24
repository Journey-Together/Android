package kr.tekit.lion.daongil.presentation.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceReviewListUseCase

class ReviewListViewModelFactory(): ViewModelProvider.Factory {
    private val placeRepository = PlaceRepository.create()

    private val getPlaceReviewListUseCase = GetPlaceReviewListUseCase(placeRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewListViewModel::class.java)) {
            return ReviewListViewModel(
                getPlaceReviewListUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }

}