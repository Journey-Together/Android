package kr.tekit.lion.daongil.presentation.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.InterestRepository
import kr.tekit.lion.daongil.domain.usecase.interestType.GetInterestTypeUseCase
import kr.tekit.lion.daongil.domain.usecase.interestType.UpdateInterestTypeUseCase

class SelectInterestViewModelFactory : ViewModelProvider.Factory {
    val interestRepository = InterestRepository.create()

    private val getInterestListUseCase = GetInterestTypeUseCase(interestRepository)

    private val updateInterestListUseCase = UpdateInterestTypeUseCase(interestRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectInterestViewModel::class.java)) {
            return SelectInterestViewModel(getInterestListUseCase, updateInterestListUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}