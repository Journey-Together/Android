package kr.tekit.lion.daongil.domain.usecase.myinfo

import kr.tekit.lion.daongil.domain.model.MyInfo
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetMyInfoUseCase(private val memberRepository: MemberRepository): BaseUseCase() {

    suspend operator fun invoke(): Result<MyInfo> = execute{
        memberRepository.getMyIfo()
    }
}