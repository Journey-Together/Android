package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository

class AddAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository
) {
    suspend operator fun invoke(){
        areaCodeRepository.addAreaCodeInfo()
    }
}