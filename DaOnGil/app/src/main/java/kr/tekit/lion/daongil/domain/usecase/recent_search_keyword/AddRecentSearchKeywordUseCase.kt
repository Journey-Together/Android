package kr.tekit.lion.daongil.domain.usecase.recent_search_keyword

import kr.tekit.lion.daongil.domain.repository.RecentSearchKeywordRepository

class AddRecentSearchKeywordUseCase(
    private val recentSearchKeywordRepository: RecentSearchKeywordRepository
) {
    suspend operator fun invoke(keyword: String){
        recentSearchKeywordRepository.addRecentSearchKeyword(keyword)
    }
}