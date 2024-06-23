package kr.tekit.lion.daongil.domain.usecase.myinfo


import kr.tekit.lion.daongil.domain.model.ConcernType
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class UpdateConcernTypeUseCase(
    private val memberRepository: MemberRepository
) : BaseUseCase() {
    suspend operator fun invoke(requestBody: ConcernType) : Result<Unit> = execute {
        memberRepository.updateConcernType(requestBody)
    }
}