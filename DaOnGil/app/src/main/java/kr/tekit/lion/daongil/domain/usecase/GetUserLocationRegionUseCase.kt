package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.ReverseGecodes
import kr.tekit.lion.daongil.domain.repository.NaverMapRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetUserLocationRegionUseCase(
    private val naverMapRepository: NaverMapRepository
) : BaseUseCase() {

    suspend operator fun invoke(coords: String): Result<ReverseGecodes> = execute {
        naverMapRepository.getReverseGeoCode(coords)
    }
}