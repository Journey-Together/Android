package kr.tekit.lion.daongil.presentation.main.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository
import kr.tekit.lion.daongil.domain.usecase.areacode.AddAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.AddVillageCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllDetailAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAreaCodeInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.InitAreaCodeInfoUseCase
import java.lang.IllegalArgumentException

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val areaCodeRepository = AreaCodeRepository.create(context)
    private val villageCodeRepository = VillageCodeRepository.create(context)

    private val getAllAreaCodeUseCase = GetAllAreaCodeUseCase(areaCodeRepository)
    private val getAreaCodeInfoUseCase = GetAreaCodeInfoUseCase(areaCodeRepository)
    private val addAreaCodeUseCase = AddAreaCodeUseCase(areaCodeRepository)
    private val getAllDetailAreaCodeUseCase = GetAllDetailAreaCodeUseCase(areaCodeRepository)
    private val addVillageCodeUseCase = AddVillageCodeUseCase(villageCodeRepository)

    private val initAreaCodeInfoUseCase = InitAreaCodeInfoUseCase(
        addAreaCodeUseCase,
        getAllDetailAreaCodeUseCase,
        getAllAreaCodeUseCase,
        addVillageCodeUseCase
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                initAreaCodeInfoUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}