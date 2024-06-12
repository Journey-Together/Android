package kr.tekit.lion.daongil.presentation.search.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.RecentSearchKeywordRepository
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.AddRecentSearchKeywordUseCase
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.GetAllRecentSearchKeywordUseCase
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.RemoveAllRecentSearchKeywordUseCase
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.RemoveRecentSearchKeywordUseCase

class SearchViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val recentSearchKeywordRepository = RecentSearchKeywordRepository.create(context)

    private val getAllRecentSearchKeywordUseCase = GetAllRecentSearchKeywordUseCase(
        recentSearchKeywordRepository
    )

    private val addRecentSearchKeywordUseCase = AddRecentSearchKeywordUseCase(
        recentSearchKeywordRepository
    )
    private val removeAllRecentSearchKeywordUseCase = RemoveAllRecentSearchKeywordUseCase(
        recentSearchKeywordRepository
    )
    private val removeRecentSearchKeywordUseCase = RemoveRecentSearchKeywordUseCase(
        recentSearchKeywordRepository
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                getAllRecentSearchKeywordUseCase,
                addRecentSearchKeywordUseCase,
                removeAllRecentSearchKeywordUseCase,
                removeRecentSearchKeywordUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}