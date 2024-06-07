package kr.tekit.lion.daongil.presentation.login.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository
import kr.tekit.lion.daongil.domain.usecase.areacode.AddAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.AddVillageCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllDetailAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.InitAreaCodeInfoUseCase

class SplashViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val authRepositoryImpl = AuthRepository.create(context)
    private val areaCodeRepository = AreaCodeRepository.create(context)
    private val villageCodeRepository = VillageCodeRepository.create(context)

    private val getAllAreaCodeUseCase = GetAllAreaCodeUseCase(areaCodeRepository)
    private val addAreaCodeUseCase = AddAreaCodeUseCase(areaCodeRepository)
    private val getAllDetailAreaCodeUseCase = GetAllDetailAreaCodeUseCase(villageCodeRepository)
    private val addVillageCodeUseCase = AddVillageCodeUseCase(villageCodeRepository)

    private val initAreaCodeInfoUseCase = InitAreaCodeInfoUseCase(
        addAreaCodeUseCase,
        getAllDetailAreaCodeUseCase,
        getAllAreaCodeUseCase,
        addVillageCodeUseCase
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(authRepositoryImpl, initAreaCodeInfoUseCase) as T
        }
        return super.create(modelClass)
    }
}