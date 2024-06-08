package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.MyIceInfo
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class ModifyMyIceInfoUseCase(
    private val memberRepository: MemberRepository
): BaseUseCase() {
    suspend operator fun invoke(modifiedData: MyIceInfo): Result<Unit> = execute {
        memberRepository.modifyMyIceInfo(modifiedData)
    }
}