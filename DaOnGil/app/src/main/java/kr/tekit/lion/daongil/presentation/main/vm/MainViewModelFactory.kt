package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository
import kr.tekit.lion.daongil.usecase.GetAreaCodeUseCase
import java.lang.IllegalArgumentException

class MainViewModelFactory: ViewModelProvider.Factory {
    private val getAreaCodeUseCase = GetAreaCodeUseCase(AreaCodeRepository.create())
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(getAreaCodeUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}