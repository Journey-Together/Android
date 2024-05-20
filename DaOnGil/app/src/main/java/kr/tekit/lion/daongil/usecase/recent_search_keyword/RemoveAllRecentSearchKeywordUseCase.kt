package kr.tekit.lion.daongil.usecase.recent_search_keyword

import kr.tekit.lion.daongil.repository.recent_search_keyword.RecentSearchKeywordRepository

class RemoveAllRecentSearchKeywordUseCase(
    private val recentSearchKeywordRepository: RecentSearchKeywordRepository
) {
    suspend operator fun invoke(){
        recentSearchKeywordRepository.removeAllRecentSearchKeyword()
    }
}