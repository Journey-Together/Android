package kr.tekit.lion.daongil.presentation.bookmark.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlaceBookmarkRepository
import kr.tekit.lion.daongil.domain.repository.PlanBookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.GetPlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.GetPlanBookmarkUseCase

class BookmarkViewModelFactory : ViewModelProvider.Factory {
    private val getPlaceBookmarkUseCase = GetPlaceBookmarkUseCase(
        PlaceBookmarkRepository.create()
    )
    private val getPlanBookmarkUseCase = GetPlanBookmarkUseCase(
        PlanBookmarkRepository.create()
    )
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(getPlaceBookmarkUseCase, getPlanBookmarkUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}