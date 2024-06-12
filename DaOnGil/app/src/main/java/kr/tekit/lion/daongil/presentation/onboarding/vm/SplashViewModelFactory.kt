package kr.tekit.lion.daongil.presentation.onboarding.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository
import kr.tekit.lion.daongil.domain.usecase.areacode.InitAreaCodeInfoUseCase

class SplashViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val authRepositoryImpl = AuthRepository.create(context)
    private val areaCodeRepository = AreaCodeRepository.create(context)
    private val villageCodeRepository = VillageCodeRepository.create(context)

    private val initAreaCodeInfoUseCase = InitAreaCodeInfoUseCase(
        areaCodeRepository,
        villageCodeRepository,
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(authRepositoryImpl, initAreaCodeInfoUseCase) as T
        }
        return super.create(modelClass)
    }
}