package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.PlaceSearchResult
import kr.tekit.lion.daongil.domain.repository.PlaceSearchResultRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

// UseCase : 비지니스 로직 캡슐화
class GetPlaceSearchResultUseCase(
    private val placeSearchResultRepository: PlaceSearchResultRepository
) : BaseUseCase() {
    // Result : usecase.base에 정의된 클래스
    // execute : BaseUseCase 에서 구현된 메서드
    suspend operator fun invoke(word: String, page: Int, size: Int): Result<PlaceSearchResult> =
        execute {
            // 리포지토리를 통해 북마크된 장소 목록을 가져온다. (람다함수)
            placeSearchResultRepository.getPlaceSearchResult(word, page, size)
        }
}