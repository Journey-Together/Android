package kr.tekit.lion.daongil.presentation.schedulereview.vm

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.ModifiedScheduleReview
import kr.tekit.lion.daongil.domain.model.ReviewImage
import kr.tekit.lion.daongil.domain.model.ScheduleReviewInfo
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleReviewInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.ModifyScheduleReviewUseCase

class ModifyScheduleReviewViewModel(
    private val getScheduleReviewUseCase: GetScheduleReviewInfoUseCase,
    private val modifyScheduleReviewUseCase: ModifyScheduleReviewUseCase
) : ViewModel() {

    // 이미지 url, uri, path
    private val _imageList = MutableLiveData<List<ReviewImage>>()
    val imageList: LiveData<List<ReviewImage>> get() = _imageList

    private val _numOfImages = MutableLiveData<Int>(0)
    val numOfImages: LiveData<Int> get() = _numOfImages

    private val _originalReview = MutableLiveData<ScheduleReviewInfo>()
    val originalReview: LiveData<ScheduleReviewInfo> get() = _originalReview

    fun addNewReviewImage(newImage: ReviewImage) {
        val currentImageList = _imageList.value?.toMutableList() ?: mutableListOf<ReviewImage>()
        currentImageList.add(newImage)
        currentImageList.let { _imageList.value = it }

        updateNumOfImages()
    }

    fun removeReviewImageFromList(position: Int) {
        val currentImageList = _imageList.value?.toMutableList() ?: mutableListOf<ReviewImage>()
        currentImageList.removeAt(position)
        currentImageList.let { _imageList.value = it }

        updateNumOfImages()
    }

    private fun updateNumOfImages() {
        _numOfImages.value = _imageList.value?.size ?: 0
    }

    fun isMoreImageAttachable(): Boolean {
        val currentValue = _numOfImages.value ?: 0
        return currentValue in 0..3
    }

    fun getScheduleReviewInfo(planId: Long) = viewModelScope.launch {
        getScheduleReviewUseCase(planId)
            .onSuccess {
                _originalReview.value = it
                initReviewImages()
            }.onError {
                Log.d("getScheduleReviewInfo", "${it.message}")
            }
    }

    private fun initReviewImages() {
        _originalReview.value?.imageList?.let {
            val reviewImages = it.map { imageUrl ->
                ReviewImage(
                    imageUrl = imageUrl,
                    imageUri = Uri.parse(imageUrl),
                    imagePath = null
                )
            }

            _imageList.value = reviewImages
            updateNumOfImages()
        }
    }

    fun updateScheduleReview(
        grade: Float,
        content: String,
        callback: (Boolean, Boolean) -> Unit
    ) {
        val newGrade = if (isGradeSame(grade)) null else grade
        val newContent = if (isContentSame(content)) null else content

        val modifiedReview = ModifiedScheduleReview(newGrade, newContent) // , newIsPublic

        val images = if(isImagesChanged()) _imageList.value else listOf<ReviewImage>()
        Log.d("modifyScheduleReview", "images: $images")

        _originalReview.value?.reviewId?.let { reviewId ->
            viewModelScope.launch {
                modifyScheduleReviewUseCase.invoke(reviewId, modifiedReview, images)
                    .onSuccess {
                        Log.d("updateScheduleReview", "onSuc")
                    }.onError {
                        Log.e("updateScheduleReview", "onError : ${it.message}")
                    }
            }
        }

        // TO DO - call back 처리
    }

    private fun isContentSame(newContent: String) : Boolean {
        return _originalReview.value?.content?.let {
            it == newContent
        } ?: true
    }

    private fun isGradeSame(newGrade: Float) : Boolean {
        return _originalReview.value?.grade?.let {
            it == newGrade
        } ?: true
    }

    private fun isImagesChanged() : Boolean {
        val originalImageSize = originalReview.value?.imageList?.size ?: 0
        if((originalImageSize > 0) && (originalImageSize==_numOfImages.value)){
            originalReview.value?.imageList?.forEachIndexed {  index, imageUrl ->
                val currentImageUrl = _imageList.value?.get(index)?.imageUrl
                // 화면에 있는 index 번째 이미지와 서버에 저장돤 index 번째 이미지가 일치하지 않는다면 -> 이미지 변경된 상태 (true)
                if(currentImageUrl.isNullOrEmpty()) return true
                else if (imageUrl != currentImageUrl){
                    return true
                }
            }
            // 모든 이미지가 일치하는 경우 -> 이미지가 변경되지 않음 (false)
            return false
        }else {
            val currentImageSize = _numOfImages.value ?: 0
            if(currentImageSize != originalImageSize) return true
        }

        return false
    }
}