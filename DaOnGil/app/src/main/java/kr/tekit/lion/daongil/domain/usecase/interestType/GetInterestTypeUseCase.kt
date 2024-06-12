package kr.tekit.lion.daongil.domain.usecase.interestType

import kr.tekit.lion.daongil.domain.model.InterestType
import kr.tekit.lion.daongil.domain.repository.InterestRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetInterestTypeUseCase(
    private val interestRepository: InterestRepository
) : BaseUseCase() {
    suspend operator fun invoke(): Result<List<InterestType>> = execute {
        interestRepository.getInterestType()
    }
}

