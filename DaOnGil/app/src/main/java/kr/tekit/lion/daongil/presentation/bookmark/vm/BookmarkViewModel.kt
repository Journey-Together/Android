package kr.tekit.lion.daongil.presentation.bookmark.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.PlaceBookmark
import kr.tekit.lion.daongil.domain.model.PlanBookmark
import kr.tekit.lion.daongil.domain.usecase.GetPlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.GetPlanBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class BookmarkViewModel(
    private val getPlaceBookmarkUseCase: GetPlaceBookmarkUseCase,
    private val getPlanBookmarkUseCase: GetPlanBookmarkUseCase
) : ViewModel() {

    private val _placeBookmarkList = MutableLiveData<List<PlaceBookmark>>()
    val placeBookmarkList: LiveData<List<PlaceBookmark>> = _placeBookmarkList

    private val _planBookmarkList = MutableLiveData<List<PlanBookmark>>()
    val planBookmarkList: LiveData<List<PlanBookmark>> = _planBookmarkList

    init {
        getPlaceBookmark()
        getPlanBookmark()
    }

    fun getPlaceBookmark() {
        viewModelScope.launch {
            getPlaceBookmarkUseCase().onSuccess {
                _placeBookmarkList.value = it
                Log.d("getPlaceBookmark", it.toString())
            }.onError {
                Log.d("getPlaceBookmark", it.toString())
            }
        }
    }

    fun getPlanBookmark() {
        viewModelScope.launch {
            getPlanBookmarkUseCase().onSuccess {
                _planBookmarkList.value = it
                Log.d("getPlaceBookmark", it.toString())
            }.onError {
                Log.d("getPlanBookmark", it.toString())
            }
        }
    }
}