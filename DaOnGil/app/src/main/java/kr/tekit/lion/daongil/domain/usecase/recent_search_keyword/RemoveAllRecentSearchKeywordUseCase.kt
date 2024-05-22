package kr.tekit.lion.daongil.domain.usecase.recent_search_keyword

import kr.tekit.lion.daongil.domain.repository.RecentSearchKeywordRepository

class RemoveAllRecentSearchKeywordUseCase(
    private val recentSearchKeywordRepository: RecentSearchKeywordRepository
) {
    suspend operator fun invoke(){
        recentSearchKeywordRepository.removeAllRecentSearchKeyword()
    }
}