package kr.tekit.lion.daongil.presentation.main.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllSigunguCodeUseCase
import java.lang.IllegalArgumentException

class SearchMainViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val areaCodeRepository = AreaCodeRepository.create(context)
    private val villageCodeRepository = VillageCodeRepository.create(context)

    private val getAllAreaCodeUseCase = GetAllAreaCodeUseCase(areaCodeRepository)
    private val getAllSigunguCodeUseCase = GetAllSigunguCodeUseCase(villageCodeRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchMainViewModel::class.java)) {
            return SearchMainViewModel(
                getAllAreaCodeUseCase,
                getAllSigunguCodeUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}