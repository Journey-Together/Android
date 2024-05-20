package kr.tekit.lion.daongil.presentation.search.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.usecase.SearchByKeywordUseCase
import kr.tekit.lion.daongil.usecase.base.onError
import kr.tekit.lion.daongil.usecase.base.onSuccess
import kr.tekit.lion.daongil.usecase.recent_search_keyword.AddRecentSearchKeywordUseCase

class SearchViewModel(
    private val searchByKeywordUseCase: SearchByKeywordUseCase,
    private val addRecentSearchKeywordUseCase: AddRecentSearchKeywordUseCase
): ViewModel() {
    private val _searchKeyword = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult = _searchKeyword
        .debounce(800)
        .flatMapLatest { query ->
            if (query.isNotBlank()) {
                searchByKeywordUseCase(query)
            } else {
                flowOf()
            }
        }
        .flowOn(Dispatchers.IO)
        .catch { e: Throwable ->
            e.printStackTrace()
        }

    fun onCompleteTextChanged(searchKeyword: String){
        _searchKeyword.value = searchKeyword
        viewModelScope.launch {
            addRecentSearchKeywordUseCase(searchKeyword)
        }

    }
}