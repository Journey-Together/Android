package kr.tekit.lion.daongil.presentation.bookmark.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.GetPlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.GetPlanBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.UpdatePlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.UpdatePlanBookmarkUseCase

class BookmarkViewModelFactory : ViewModelProvider.Factory {
    val bookmarkRepository = BookmarkRepository.create()

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
            return BookmarkViewModel(getPlaceBookmarkUseCase, getPlanBookmarkUseCase, updatePlaceBookmarkUseCase, updatePlanBookmarkUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}