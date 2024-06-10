package kr.tekit.lion.daongil.presentation.main.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository
import kr.tekit.lion.daongil.domain.repository.PlaceMainInfoRepository
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository
import kr.tekit.lion.daongil.domain.usecase.GetPlaceMainInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAreaCodeByNameUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetSigunguCodeByNameUseCase

class HomeViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val areaCodeRepository = AreaCodeRepository.create(context)
    private val placeMainInfoRepository = PlaceMainInfoRepository.create()
    private val villageCodeRepository = VillageCodeRepository.create(context)

    private val getAreaCodeByNameUseCase = GetAreaCodeByNameUseCase(areaCodeRepository)
    private val getPlaceMainInfoUseCase = GetPlaceMainInfoUseCase(placeMainInfoRepository)
    private val getSigunguCodeByNameUseCase = GetSigunguCodeByNameUseCase(
        villageCodeRepository
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                getAreaCodeByNameUseCase,
                getPlaceMainInfoUseCase,
                getSigunguCodeByNameUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}