package kr.tekit.lion.daongil.presentation.myinfo.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.GetMyInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.ModifyMyIceInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.ModifyMyPersonalInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.ModifyMyProfileImageUseCase

class MyInfoViewModelFactory : ViewModelProvider.Factory {

    private val memberRepository = MemberRepository.create()
    private val getMyInfoUseCase = GetMyInfoUseCase(memberRepository)
    private val modifyMyIceInfoUseCase = ModifyMyIceInfoUseCase(memberRepository)
    private val modifyMyPersonalInfoUseCase = ModifyMyPersonalInfoUseCase(memberRepository)
    private val modifyMyProfileImageUseCase = ModifyMyProfileImageUseCase(memberRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyInfoViewModel::class.java)) {
            return MyInfoViewModel(
                getMyInfoUseCase,
                modifyMyIceInfoUseCase,
                modifyMyPersonalInfoUseCase,
                modifyMyProfileImageUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}