package kr.tekit.lion.daongil.presentation.schedulereview.vm

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.ReviewImage
import kr.tekit.lion.daongil.domain.model.ScheduleReviewInfo
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleReviewInfoUseCase

class ModifyScheduleReviewViewModel(
    private val  getScheduleReviewUseCase: GetScheduleReviewInfoUseCase,
    ) : ViewModel() {

    private val _imageList = MutableLiveData<List<ReviewImage>>()
    val imageList: LiveData<List<ReviewImage>> get() = _imageList

    private val _numOfImages = MutableLiveData<Int>(0)
    val numOfImages: LiveData<Int> get() = _numOfImages

    private val _originalReview = MutableLiveData<ScheduleReviewInfo>()
    val originalReview: LiveData<ScheduleReviewInfo> get() = _originalReview

    fun addNewReviewImage(newImage: ReviewImage){
        val currentImageList = _imageList.value?.toMutableList() ?: mutableListOf<ReviewImage>()
        currentImageList.add(newImage)
        currentImageList.let { _imageList.value = it }

        updateNumOfImages()
    }

    fun removeReviewImageFromList(position: Int){
        val currentImageList = _imageList.value?.toMutableList() ?: mutableListOf<ReviewImage>()
        currentImageList.removeAt(position)
        currentImageList.let { _imageList.value = it }

        updateNumOfImages()
    }

    private fun updateNumOfImages(){
        _numOfImages.value = _imageList.value?.size ?: 0
    }

    fun isMoreImageAttachable(): Boolean{
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

    private fun initReviewImages(){
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
}