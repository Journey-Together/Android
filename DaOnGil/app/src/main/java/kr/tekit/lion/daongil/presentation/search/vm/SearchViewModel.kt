package kr.tekit.lion.daongil.presentation.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.RecentSearchKeyword
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.AddRecentSearchKeywordUseCase
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.GetAllRecentSearchKeywordUseCase
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.RemoveAllRecentSearchKeywordUseCase
import kr.tekit.lion.daongil.domain.usecase.recent_search_keyword.RemoveRecentSearchKeywordUseCase

class SearchViewModel(
    private val getAllRecentSearchKeywordUseCase: GetAllRecentSearchKeywordUseCase,
    private val addRecentSearchKeywordUseCase: AddRecentSearchKeywordUseCase,
    private val removeAllRecentSearchKeywordUseCase : RemoveAllRecentSearchKeywordUseCase,
    private val removeRecentSearchKeywordUseCase : RemoveRecentSearchKeywordUseCase
) : ViewModel() {

    private val _searchKeyword = MutableStateFlow("")

    val recentSearchKeyword: StateFlow<List<RecentSearchKeyword>> = getAllRecentSearchKeywordUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

//    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
//    val searchResult = _searchKeyword
//        .debounce(800)
//        .flatMapLatest { query ->
//            if (query.isNotBlank()) {
//                searchByKeywordUseCase(query)
//            } else {
//                flowOf()
//            }
//        }
//        .flowOn(Dispatchers.IO)
//        .catch { e: Throwable ->
//            e.printStackTrace()
//        }


    fun onCompleteTextChanged(searchKeyword: String) {
        _searchKeyword.value = searchKeyword
        viewModelScope.launch {
            //addRecentSearchKeywordUseCase(searchKeyword)
        }
    }

    fun onClickAllItemRemoveButton() = viewModelScope.launch{
        removeAllRecentSearchKeywordUseCase()
    }

    fun onClickItemRemoveButton(id: Long) = viewModelScope.launch{
        removeRecentSearchKeywordUseCase(id)
    }

}