package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.ConcernType
import kr.tekit.lion.daongil.domain.repository.MemberRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetConcernTypeUseCase(
    private val memberRepository: MemberRepository
) : BaseUseCase() {
    suspend operator fun invoke() : Result<ConcernType> = execute {
        memberRepository.getConcernType()
    }
}