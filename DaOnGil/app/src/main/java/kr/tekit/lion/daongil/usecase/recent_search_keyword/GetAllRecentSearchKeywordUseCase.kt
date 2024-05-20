package kr.tekit.lion.daongil.usecase.recent_search_keyword

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.model.RecentSearchKeyword
import kr.tekit.lion.daongil.repository.recent_search_keyword.RecentSearchKeywordRepository

class GetAllRecentSearchKeywordUseCase(
    private val recentSearchKeywordRepository: RecentSearchKeywordRepository
) {
    suspend operator fun invoke(): Flow<List<RecentSearchKeyword>>{
        return recentSearchKeywordRepository.getAllRecentSearchKeyword()
    }
}