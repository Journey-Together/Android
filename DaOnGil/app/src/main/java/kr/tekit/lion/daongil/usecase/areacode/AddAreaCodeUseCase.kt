package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository

class AddAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository
) {
    suspend operator fun invoke(){
        areaCodeRepository.addAreaCodeInfo()
    }
}