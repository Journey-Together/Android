package kr.tekit.lion.daongil.domain.usecase.interestType

import kr.tekit.lion.daongil.data.dto.remote.response.Interest.InterestTypeRes
import kr.tekit.lion.daongil.domain.repository.InterestRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class UpdateInterestTypeUseCase(
    private val interestRepository: InterestRepository
) : BaseUseCase() {
    suspend operator fun invoke(interestType: InterestTypeRes) : Result<Unit> = execute {
        interestRepository.updateInterestType(interestType)
    }
}