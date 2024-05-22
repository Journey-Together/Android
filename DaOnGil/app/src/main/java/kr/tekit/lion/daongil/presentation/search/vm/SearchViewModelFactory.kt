package kr.tekit.lion.daongil.presentation.search.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.RecentSearchKeywordRepository
import kr.tekit.lion.daongil.domain.repository.SearchKeywordRepository
import kr.tekit.lion.daongil.domain.usecase.SearchByKeywordUseCase
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.AddRecentSearchKeywordUseCase
import java.lang.IllegalArgumentException

class SearchViewModelFactory(context: Context) : ViewModelProvider.Factory{

    private val searchByKeywordUseCase = SearchByKeywordUseCase(SearchKeywordRepository.create())

    private val addRecentSearchKeywordUseCase = AddRecentSearchKeywordUseCase(
        RecentSearchKeywordRepository.create(context)
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(searchByKeywordUseCase, addRecentSearchKeywordUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}