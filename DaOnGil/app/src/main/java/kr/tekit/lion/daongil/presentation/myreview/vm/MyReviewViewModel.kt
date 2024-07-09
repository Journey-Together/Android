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
import kr.tekit.lion.daongil.domain.usecase.place.DeleteMyPlaceReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetMyPlaceReviewUseCase

class MyReviewViewModel(
    private val getMyPlaceReviewUseCase: GetMyPlaceReviewUseCase
) : ViewModel() {

    private val _myPlaceReview = MutableLiveData<MyPlaceReview>()
    val myPlaceReview: LiveData<MyPlaceReview> = _myPlaceReview

    private val _isLastPage = MutableLiveData<Boolean>()
    val isLastPage: LiveData<Boolean> = _isLastPage

    private var isRequesting = false

    init {
        _isLastPage.value = false
        getMyPlaceReview(5, 0)
    }

    private fun getMyPlaceReview(size: Int, page: Int) = viewModelScope.launch {
        getMyPlaceReviewUseCase(size, page).onSuccess {
            _myPlaceReview.value = it
            _isLastPage.value = it.myPlaceReviewInfoList.size < size
        }.onError {
            Log.d("getMyPlaceReview", it.toString())
        }
    }

    fun getNextMyPlaceReview(size: Int) = viewModelScope.launch {
        val page = _myPlaceReview.value?.pageNo ?:0

        if (_isLastPage.value == false && !isRequesting) {
            isRequesting = true

            getMyPlaceReviewUseCase(size, page + 1).onSuccess { newReviews ->
                val currentReviews = _myPlaceReview.value?.myPlaceReviewInfoList ?: emptyList()
                val updatedReviews = currentReviews + newReviews.myPlaceReviewInfoList
                val updatedReviewData = newReviews.copy(myPlaceReviewInfoList = updatedReviews)
                _myPlaceReview.value = updatedReviewData

                if (newReviews.myPlaceReviewInfoList.size < size) {
                    _isLastPage.value = true
                }

                Log.d("MyReviewViewModel", "currentReviews : ${currentReviews}")
                Log.d("MyReviewViewModel", "updatedReviews : ${updatedReviews}")
                Log.d("MyReviewViewModel", "updatedReviewData : ${updatedReviewData}")
                Log.d("MyReviewViewModel", _isLastPage.value.toString())
            }.onError {
                Log.d("getNextMyPlaceReview", it.toString())
            }.also {
                isRequesting = false
            }
        }
    }
}