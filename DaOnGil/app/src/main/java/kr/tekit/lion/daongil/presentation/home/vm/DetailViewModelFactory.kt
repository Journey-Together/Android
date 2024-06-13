package kr.tekit.lion.daongil.presentation.home.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoGuestUseCase
import kr.tekit.lion.daongil.domain.usecase.place.UpdatePlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase

class DetailViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val authRepository = AuthRepository.create(context)
    private val placeRepository = PlaceRepository.create()
    private val bookmarkRepository = BookmarkRepository.create()

    private val getPlaceDetailInfoUseCase = GetPlaceDetailInfoUseCase(placeRepository)
    private val getPlaceDetailInfoGuestUseCase = GetPlaceDetailInfoGuestUseCase(placeRepository)
    private val updatePlaceBookmarkUseCase = UpdatePlaceBookmarkUseCase(bookmarkRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                authRepository,
                getPlaceDetailInfoUseCase,
                getPlaceDetailInfoGuestUseCase,
                updatePlaceBookmarkUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}