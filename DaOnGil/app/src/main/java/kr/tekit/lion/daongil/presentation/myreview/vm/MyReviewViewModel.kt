package kr.tekit.lion.daongil.presentation.myreview.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.MyPlaceReview
import kr.tekit.lion.daongil.domain.model.MyPlaceReviewImages
import kr.tekit.lion.daongil.domain.model.MyPlaceReviewInfo
import kr.tekit.lion.daongil.domain.model.UpdateMyPlaceReview
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.DeleteMyPlaceReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetMyPlaceReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.place.UpdateMyPlaceReviewUseCase
import java.time.LocalDate

class MyReviewViewModel(
    private val getMyPlaceReviewUseCase: GetMyPlaceReviewUseCase,
    private val deleteMyPlaceReviewUseCase: DeleteMyPlaceReviewUseCase,
    private val updateMyPlaceReviewUseCase: UpdateMyPlaceReviewUseCase,
) : ViewModel() {

    private val _myPlaceReview = MutableLiveData<MyPlaceReview>()
    val myPlaceReview: LiveData<MyPlaceReview> = _myPlaceReview

    private val _reviewData = MutableLiveData<MyPlaceReviewInfo>()
    val reviewData: LiveData<MyPlaceReviewInfo> = _reviewData

    private val _reviewImages = MutableLiveData<List<String>>()

    private val _newImages = MutableLiveData<List<String>>()

    private val _deletedImages = MutableLiveData<List<String>>()

    private val _visitDate = MutableLiveData<LocalDate>()
    val visitDate: LiveData<LocalDate> = _visitDate

    private val _numOfImages = MutableLiveData<Int>()
    val numOfImages: LiveData<Int> = _numOfImages

    private val _isLastPage = MutableLiveData(false)
    val isLastPage: LiveData<Boolean> = _isLastPage

    private val _isReviewDelete = MutableLiveData(false)
    val isReviewDelete: LiveData<Boolean> = _isReviewDelete

    private var isRequesting = false

    init {
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
                    if(_isReviewDelete.value == false) _isReviewDelete.value = true
                }
            }.onError {
                Log.d("deleteMyPlaceReview", it.toString())
            }
        }
    }

    fun updateMyPlaceReview(reviewId: Long, grade: Float, date: LocalDate, content: String) {
        val deleteImages = _deletedImages.value ?: listOf()
        val newImages = _newImages.value ?: listOf()
        val currentImages = _reviewImages.value ?: listOf()
        val updateImages = currentImages.plus(newImages)

        _reviewData.value = _reviewData.value?.copy(
            grade = grade,
            date = date,
            content = content,
            images = updateImages
        )

        viewModelScope.launch {
            updateMyPlaceReviewUseCase(
                reviewId,
                UpdateMyPlaceReview(grade, date, content, deleteImages),
                MyPlaceReviewImages(newImages)
            ).onSuccess {
                _myPlaceReview.value?.let { currentReviewData ->
                    val updatedReviews = currentReviewData.myPlaceReviewInfoList.map {
                        if (it.reviewId == reviewId) {
                            _reviewData.value!!
                        } else {
                            it
                        }
                    }
                    _myPlaceReview.value = currentReviewData.copy(myPlaceReviewInfoList = updatedReviews)
                }
                Log.d("updateMyPlaceReview", it.toString())
            }.onError {
                Log.d("updateMyPlaceReview", it.toString())
            }
        }
    }

    fun setReviewData(review: MyPlaceReviewInfo) {
        _reviewData.value = review
        _reviewImages.value = review.images.toMutableList()
        _newImages.value = listOf()
        _deletedImages.value = listOf()
        _visitDate.value = review.date

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
        val currentNewImages = _newImages.value?.toMutableList() ?: mutableListOf()

        if (position in currentImages.indices) {
            val deletedImage = currentImages.removeAt(position)
            _reviewImages.value = currentImages

            val deletedImageList = _deletedImages.value?.toMutableList() ?: mutableListOf()
            deletedImageList.add(deletedImage)
            _deletedImages.value = deletedImageList
        } else {
            val newPosition = position - currentImages.size
            if (newPosition in currentNewImages.indices) {
                currentNewImages.removeAt(newPosition)
                _newImages.value = currentNewImages
            }
        }

        updateNumOfImages()
    }


    fun addNewImage(image: String) {
        val currentNewImages = _newImages.value?.toMutableList() ?: mutableListOf()
        currentNewImages.add(image)
        _newImages.value = currentNewImages

        updateNumOfImages()
    }

    private fun updateNumOfImages() {
        val reviewImageCount = _reviewImages.value?.size ?: 0
        val newImageCount = _newImages.value?.size ?: 0
        _numOfImages.value = reviewImageCount + newImageCount
    }

    fun isMoreImageAttachable(): Boolean{
        val currentValue = _numOfImages.value ?: 0
        return currentValue in 0..3
    }
}