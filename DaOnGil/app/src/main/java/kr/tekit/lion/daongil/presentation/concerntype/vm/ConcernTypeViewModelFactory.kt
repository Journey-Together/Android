package kr.tekit.lion.daongil.presentation.concerntype.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.GetConcernTypeUseCase

class ConcernTypeViewModelFactory : ViewModelProvider.Factory {

    private val memberRepository = MemberRepository.create()
    private val getConcernTypeUseCase = GetConcernTypeUseCase(memberRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConcernTypeViewModel::class.java)) {
            return ConcernTypeViewModel(getConcernTypeUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}