package kr.tekit.lion.daongil.domain.usecase.recent_search_keyword

import kr.tekit.lion.daongil.domain.repository.RecentSearchKeywordRepository

class RemoveRecentSearchKeywordUseCase(
    private val recentSearchKeywordRepository: RecentSearchKeywordRepository
) {
    suspend operator fun invoke(id: Long) {
        recentSearchKeywordRepository.removeRecentSearchKeyword(id)
    }
}