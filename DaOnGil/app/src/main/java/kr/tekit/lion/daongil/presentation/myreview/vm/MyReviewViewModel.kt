package kr.tekit.lion.daongil.presentation.myreview.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.MyPlaceReview
import kr.tekit.lion.daongil.domain.model.MyPlaceReviewInfo
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.DeleteMyPlaceReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetMyPlaceReviewUseCase
import java.time.LocalDate

class MyReviewViewModel(
    private val getMyPlaceReviewUseCase: GetMyPlaceReviewUseCase,
    private val deleteMyPlaceReviewUseCase: DeleteMyPlaceReviewUseCase
) : ViewModel() {

    private val _myPlaceReview = MutableLiveData<MyPlaceReview>()
    val myPlaceReview: LiveData<MyPlaceReview> = _myPlaceReview

    private val _reviewData = MutableLiveData<MyPlaceReviewInfo>()
    val reviewData: LiveData<MyPlaceReviewInfo> = _reviewData

    private val _visitDate = MutableLiveData<LocalDate>()
    val visitDate: LiveData<LocalDate> = _visitDate

    private val _reviewImages = MutableLiveData<List<String>>()
    val reviewImages : LiveData<List<String>> = _reviewImages

    private val _numOfImages = MutableLiveData<Int>()
    val numOfImages: LiveData<Int> = _numOfImages

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

            if (it.pageNo == it.totalPages) {
                _isLastPage.value = true
            }
            Log.d("MyReviewViewModel", "getMyPlaceReview")
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

                if (newReviews.pageNo == newReviews.totalPages) {
                    _isLastPage.value = true
                }

                Log.d("MyReviewViewModel", "getNextMyPlaceReview")
            }.onError {
                Log.d("getNextMyPlaceReview", it.toString())
            }.also {
                isRequesting = false
            }
        }
    }

    fun deleteMyPlaceReview(reviewId: Long) {
        viewModelScope.launch {
            deleteMyPlaceReviewUseCase(reviewId).onSuccess {
                _myPlaceReview.value?.let { currentReviewData ->
                    val updatedReviews =
                        currentReviewData.myPlaceReviewInfoList.filter { it.reviewId != reviewId }
                    val updatedReviewData = currentReviewData.copy(
                        myPlaceReviewInfoList = updatedReviews,
                        reviewNum = currentReviewData.reviewNum - 1
                    )
                    _myPlaceReview.value = updatedReviewData
                }
            }.onError {
                Log.d("deleteMyPlaceReview", it.toString())
            }
        }
    }

    fun setReviewData(review: MyPlaceReviewInfo) {
        _reviewData.value = review
        _reviewImages.value = review.images.toMutableList()
        updateNumOfImages()
    }

    fun setVisitDate(startDate: LocalDate) {
        _visitDate.value = startDate
    }

    fun setReviewImages(image: String) {
        val currentImages = _reviewImages.value?.toMutableList() ?: mutableListOf()
        currentImages.add(image)
        _reviewImages.value = currentImages
        updateNumOfImages()
    }

    fun deleteImage(position: Int) {
        val currentImages = _reviewImages.value?.toMutableList() ?: mutableListOf()
        if (position in currentImages.indices) {
            currentImages.removeAt(position)
            _reviewImages.value = currentImages
            updateNumOfImages()
        }
    }

    private fun updateNumOfImages() {
        _numOfImages.value = _reviewImages.value?.size ?: 0
    }
}