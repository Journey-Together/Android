package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.MyPersonalInfo
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result


class ModifyMyPersonalInfoUseCase(
    private val memberRepository: MemberRepository
): BaseUseCase() {
    suspend operator fun invoke(modifiedData: MyPersonalInfo): Result<Unit> = execute {
        memberRepository.modifyMyPersonalInfo(modifiedData)
    }
}