package kr.tekit.lion.daongil.presentation.main.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository
import kr.tekit.lion.daongil.domain.repository.PlaceMainInfoRepository
import kr.tekit.lion.daongil.domain.usecase.GetPlaceMainInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAreaCodeInfoUseCase

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val areaCodeRepository = AreaCodeRepository.create(context)
    private val placeMainInfoRepository = PlaceMainInfoRepository.create()

    private val getAreaCodeInfoUseCase = GetAreaCodeInfoUseCase(areaCodeRepository)
    private val getPlaceMainInfoUseCase = GetPlaceMainInfoUseCase(placeMainInfoRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                getAreaCodeInfoUseCase,
                getPlaceMainInfoUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}