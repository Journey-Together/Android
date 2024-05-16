package kr.tekit.lion.daongil.presentation.main.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository
import kr.tekit.lion.daongil.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.areacode.GetAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.areacode.InitAreaCodeUseCase
import kr.tekit.lion.daongil.usecase.areacode.SetAreaCodeUseCase
import java.lang.IllegalArgumentException

class MainViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val areaCodeRepository = AreaCodeRepository.create(context)
    private val initAreaCodeUseCase = InitAreaCodeUseCase(areaCodeRepository, SetAreaCodeUseCase(areaCodeRepository))
    private val getAllAreaCodeUseCase = GetAllAreaCodeUseCase(areaCodeRepository)
    private val getAreaCodeUseCase = GetAreaCodeUseCase(areaCodeRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(initAreaCodeUseCase, getAllAreaCodeUseCase, getAreaCodeUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}