package kr.tekit.lion.daongil.presentation.home.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.PlaceReviewInfo
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceReviewListUseCase

class ReviewListViewModel(
    private val getPlaceReviewListUseCase: GetPlaceReviewListUseCase
) : ViewModel() {
    private val _placeReviewInfo = MutableLiveData<PlaceReviewInfo>()
    val placeReviewInfo : LiveData<PlaceReviewInfo> = _placeReviewInfo

    private val _isLastPage = MutableLiveData<Boolean>()
    val isLastPage : LiveData<Boolean> = _isLastPage

    private var placeId : Long = 0

    init {
        _isLastPage.value = false
    }

    fun setPlaceId(placeId: Long) {
        this.placeId = placeId
        getPlaceReview(placeId, 5, 0)
    }

    private fun getPlaceReview(placeId: Long, size: Int, page: Int) = viewModelScope.launch {
        getPlaceReviewListUseCase(placeId, size, page).onSuccess {
            Log.d("getPlaceReview", it.toString())
            _placeReviewInfo.value = it
        }.onError {
            Log.d("getPlaceReview", it.toString())
        }
    }

    fun getNewPlaceReview(placeId: Long, size: Int) = viewModelScope.launch {
        val page = _placeReviewInfo.value?.pageNo

        if (page != null) {
            getPlaceReviewListUseCase(placeId, size = size, page = page + 1).onSuccess {
                if (it.pageNo == it.totalPages) {
                    _isLastPage.value = true
                } else {
                    val reviews = _placeReviewInfo.value?.placeReviewList ?: emptyList()
                    val newReviews = reviews + it.placeReviewList
                    val newReviewData = it.copy(placeReviewList = newReviews)

                    _placeReviewInfo.value = newReviewData
                }
            }.onError {
                Log.d("getNewPlaceReview", it.toString())
            }
        }
    }
}