package kr.tekit.lion.daongil.domain.usecase.recent_search_keyword

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.domain.model.RecentSearchKeyword
import kr.tekit.lion.daongil.domain.repository.RecentSearchKeywordRepository

class GetAllRecentSearchKeywordUseCase(
    private val recentSearchKeywordRepository: RecentSearchKeywordRepository
) {
    operator fun invoke(): Flow<List<RecentSearchKeyword>>{
        return recentSearchKeywordRepository.getAllRecentSearchKeyword()
    }
}