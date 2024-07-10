package kr.tekit.lion.daongil.presentation.schedulereview.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.tekit.lion.daongil.domain.model.ReviewImage

class ModifyScheduleReviewViewModel : ViewModel() {

    private val _imageList = MutableLiveData<List<ReviewImage>>()
    val imageList: LiveData<List<ReviewImage>> get() = _imageList

    private val _numOfImages = MutableLiveData<Int>(0)
    val numOfImages: LiveData<Int> get() = _numOfImages

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
}