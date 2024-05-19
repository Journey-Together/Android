package kr.tekit.lion.daongil.presentation.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.repository.searchkeyword.SearchKeywordRepository
import kr.tekit.lion.daongil.usecase.SearchByKeywordUseCase
import java.lang.IllegalArgumentException

class SearchViewModelFactory() : ViewModelProvider.Factory{
    private val searchByKeywordUseCase = SearchByKeywordUseCase(SearchKeywordRepository.create())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(searchByKeywordUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}