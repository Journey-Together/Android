package kr.tekit.lion.daongil.domain.usecase.emergency

import kr.tekit.lion.daongil.domain.model.EmergencyMessageInfo
import kr.tekit.lion.daongil.domain.repository.EmergencyRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetEmergencyMessageUseCase(
    private val emergencyRepository: EmergencyRepository
) : BaseUseCase() {
    suspend operator fun invoke(HPID: String?): Result<List<EmergencyMessageInfo>> =
        execute {
            emergencyRepository.getEmergencyMessage(HPID)
        }
}