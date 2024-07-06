package kr.tekit.lion.daongil.presentation.myreview.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.MyPlaceReview
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.GetMyPlaceReviewUseCase

class MyReviewViewModel(
    private val getMyPlaceReviewUseCase: GetMyPlaceReviewUseCase
) : ViewModel() {

    private val _myPlaceReview = MutableLiveData<MyPlaceReview>()
    val myPlaceReview: LiveData<MyPlaceReview> = _myPlaceReview

    var isLastPage = false

    init {
        getMyPlaceReview(5, 0)
    }

    private fun getMyPlaceReview(size: Int, page: Int) = viewModelScope.launch {
        getMyPlaceReviewUseCase(size, page).onSuccess {
            _myPlaceReview.value = it
        }.onError {
            Log.d("getMyPlaceReview", it.toString())
        }
    }

    fun getNextMyPlaceReview(size: Int) = viewModelScope.launch {
        val page = _myPlaceReview.value?.pageNo

        if (page != null) {
            getMyPlaceReviewUseCase(size, page+1).onSuccess { newReviews ->
                if (newReviews.myPlaceReviewInfoList.isEmpty()) {
                    isLastPage = true
                } else {
                    val currentReviews = _myPlaceReview.value?.myPlaceReviewInfoList ?: emptyList()
                    val updatedReviews = currentReviews + newReviews.myPlaceReviewInfoList
                    val updatedReviewData = newReviews.copy(myPlaceReviewInfoList = updatedReviews)
                    _myPlaceReview.value = updatedReviewData
                }
            }.onError {
                Log.d("getNextMyPlaceReview", it.toString())
            }
        }
    }
}