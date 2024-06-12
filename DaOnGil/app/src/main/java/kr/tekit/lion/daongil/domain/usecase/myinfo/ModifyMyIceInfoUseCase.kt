package kr.tekit.lion.daongil.domain.usecase.myinfo

import kr.tekit.lion.daongil.domain.model.IceInfo
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class ModifyMyIceInfoUseCase(
    private val memberRepository: MemberRepository
): BaseUseCase() {
    suspend operator fun invoke(modifiedData: IceInfo): Result<Unit> = execute {
        memberRepository.modifyMyIceInfo(modifiedData)
    }
}