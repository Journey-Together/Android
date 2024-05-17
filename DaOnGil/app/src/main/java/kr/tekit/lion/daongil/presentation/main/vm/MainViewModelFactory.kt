package kr.tekit.lion.daongil.presentation.main.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository
import kr.tekit.lion.daongil.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.areacode.GetAreaCodeInfoUseCase
import kr.tekit.lion.daongil.usecase.areacode.InitAreaCodeInfoUseCase
import java.lang.IllegalArgumentException

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val areaCodeRepository = AreaCodeRepository.create(context)
    private val getAllAreaCodeUseCase = GetAllAreaCodeUseCase(areaCodeRepository)
    private val getAreaCodeInfoUseCase = GetAreaCodeInfoUseCase(areaCodeRepository)
    private val initAreaCodeInfoUseCase = InitAreaCodeInfoUseCase(areaCodeRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                getAllAreaCodeUseCase,
                getAreaCodeInfoUseCase,
                initAreaCodeInfoUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}