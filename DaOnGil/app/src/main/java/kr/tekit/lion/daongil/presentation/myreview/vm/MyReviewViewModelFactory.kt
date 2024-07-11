package kr.tekit.lion.daongil.presentation.myreview.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.place.DeleteMyPlaceReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetMyPlaceReviewUseCase

class MyReviewViewModelFactory : ViewModelProvider.Factory {

    private val placeRepository = PlaceRepository.create()
    private val getMyPlaceReviewUseCase = GetMyPlaceReviewUseCase(placeRepository)
    private val deleteMyPlaceReviewUseCase = DeleteMyPlaceReviewUseCase(placeRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyReviewViewModel::class.java)) {
            return MyReviewViewModel(
                getMyPlaceReviewUseCase,
                deleteMyPlaceReviewUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}