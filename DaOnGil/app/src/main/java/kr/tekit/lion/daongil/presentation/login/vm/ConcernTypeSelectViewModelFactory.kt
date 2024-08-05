package kr.tekit.lion.daongil.presentation.login.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.myinfo.UpdateConcernTypeUseCase

class ConcernTypeSelectViewModelFactory : ViewModelProvider.Factory {

    private val memberRepository = MemberRepository.create()
    private val updateConcernTypeUseCase = UpdateConcernTypeUseCase(memberRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConcernTypeSelectViewModel::class.java)) {
            return ConcernTypeSelectViewModel(updateConcernTypeUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}