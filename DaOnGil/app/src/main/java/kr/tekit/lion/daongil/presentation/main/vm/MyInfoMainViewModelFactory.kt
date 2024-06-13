package kr.tekit.lion.daongil.presentation.main.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.myinfo.GetMyDefaultInfoUseCase
import java.lang.IllegalArgumentException

class MyInfoMainViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val authRepository = AuthRepository.create(context)
    private val memberRepository = MemberRepository.create()
    private val getMyDefaultInfoUseCase = GetMyDefaultInfoUseCase(memberRepository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyInfoMainViewModel::class.java)){
            return MyInfoMainViewModel(authRepository, getMyDefaultInfoUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}