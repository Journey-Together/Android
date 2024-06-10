package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.MyDefaultInfo
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetMyDefaultInfoUseCase(
    private val memberRepository: MemberRepository
): BaseUseCase() {
    suspend operator fun invoke(): Result<MyDefaultInfo> = execute {
        memberRepository.getMyDefaultInfo()
    }
}