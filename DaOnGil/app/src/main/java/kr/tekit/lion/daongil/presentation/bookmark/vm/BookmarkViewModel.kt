package kr.tekit.lion.daongil.presentation.bookmark.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.PlaceBookmark
import kr.tekit.lion.daongil.domain.model.PlanBookmark
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetPlanBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.place.UpdatePlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.UpdatePlanBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class BookmarkViewModel(
    private val getPlaceBookmarkUseCase: GetPlaceBookmarkUseCase,
    private val getPlanBookmarkUseCase: GetPlanBookmarkUseCase,
    private val updatePlaceBookmarkUseCase: UpdatePlaceBookmarkUseCase,
    private val updatePlanBookmarkUseCase: UpdatePlanBookmarkUseCase
) : ViewModel() {

    private val _placeBookmarkList = MutableLiveData<List<PlaceBookmark>>()
    val placeBookmarkList: LiveData<List<PlaceBookmark>> = _placeBookmarkList

    private val _planBookmarkList = MutableLiveData<List<PlanBookmark>>()
    val planBookmarkList: LiveData<List<PlanBookmark>> = _planBookmarkList

    init {
        getPlaceBookmark()
        getPlanBookmark()
    }

    private fun getPlaceBookmark() {
        viewModelScope.launch {
            getPlaceBookmarkUseCase().onSuccess {
                _placeBookmarkList.value = it
            }.onError {
                Log.d("getPlaceBookmark", it.toString())
            }
        }
    }

    private fun getPlanBookmark() {
        viewModelScope.launch {
            getPlanBookmarkUseCase().onSuccess {
                _planBookmarkList.value = it
            }.onError {
                Log.d("getPlanBookmark", it.toString())
            }
        }
    }

    fun updatePlaceBookmark(placeId: Long) {
        viewModelScope.launch {
            updatePlaceBookmarkUseCase(placeId).onSuccess {
                // 리사이클러뷰에서 해당 항목 삭제
                val updatedList = _placeBookmarkList.value.orEmpty().toMutableList()
                val index = updatedList.indexOfFirst { it.placeId == placeId }
                if (index != -1) {
                    updatedList.removeAt(index)
                    _placeBookmarkList.postValue(updatedList)
                }
            }.onError {
                Log.d("updatePlaceBookmark", it.toString())
            }
        }
    }

    fun updatePlanBookmark(planId: Long) {
        viewModelScope.launch {
            updatePlanBookmarkUseCase(planId).onSuccess {
                // 리사이클러뷰에서 해당 항목 삭제
                val updatedList = _planBookmarkList.value.orEmpty().toMutableList()
                val index = updatedList.indexOfFirst { it.planId == planId }
                if (index != -1) {
                    updatedList.removeAt(index)
                    _planBookmarkList.postValue(updatedList)
                }
            }.onError {
                Log.d("updatePlanBookmark", it.toString())
            }
        }
    }
}