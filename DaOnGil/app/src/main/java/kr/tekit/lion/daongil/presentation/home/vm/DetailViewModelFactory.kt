package kr.tekit.lion.daongil.presentation.home.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.repository.PlaceDetailInfoGuestRepository
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.GetPlaceDetailInfoGuestUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.UpdatePlaceBookmarkUseCase

class DetailViewModelFactory(context: Context) : ViewModelProvider.Factory{
    private val authRepository = AuthRepository.create(context)
    private val placeDetailInfoRepository = PlaceRepository.crate()
    private val placeDetailInfoGuestRepository = PlaceDetailInfoGuestRepository.create()
    private val bookmarkRepository = BookmarkRepository.create()

    private val getPlaceDetailInfoUseCase = GetPlaceDetailInfoUseCase(placeDetailInfoRepository)
    private val getPlaceDetailInfoGuestUseCase = GetPlaceDetailInfoGuestUseCase(placeDetailInfoGuestRepository)
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