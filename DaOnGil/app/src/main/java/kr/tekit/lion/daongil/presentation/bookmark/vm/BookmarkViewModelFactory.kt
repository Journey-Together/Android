package kr.tekit.lion.daongil.presentation.bookmark.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetPlanBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.place.UpdatePlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.UpdatePlanBookmarkUseCase

class BookmarkViewModelFactory : ViewModelProvider.Factory {
    private val bookmarkRepository = BookmarkRepository.create()

    private val getPlaceBookmarkUseCase = GetPlaceBookmarkUseCase(
        bookmarkRepository
    )

    private val getPlanBookmarkUseCase = GetPlanBookmarkUseCase(
        bookmarkRepository
    )

    private val updatePlaceBookmarkUseCase = UpdatePlaceBookmarkUseCase(
        bookmarkRepository
    )

    private val updatePlanBookmarkUseCase = UpdatePlanBookmarkUseCase(
        bookmarkRepository
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(
                getPlaceBookmarkUseCase,
                getPlanBookmarkUseCase,
                updatePlaceBookmarkUseCase,
                updatePlanBookmarkUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}