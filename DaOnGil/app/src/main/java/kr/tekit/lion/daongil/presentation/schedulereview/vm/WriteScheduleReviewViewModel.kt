package kr.tekit.lion.daongil.presentation.schedulereview.vm

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.BriefScheduleInfo
import kr.tekit.lion.daongil.domain.model.NewScheduleReview
import kr.tekit.lion.daongil.domain.model.NewScheduleReviewDetail
import kr.tekit.lion.daongil.domain.model.ReviewImg
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.plan.AddNewScheduleReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetBriefScheduleInfoUseCase

class WriteScheduleReviewViewModel(
    private val getBriefScheduleInfoUseCase: GetBriefScheduleInfoUseCase,
    private val addNewScheduleReviewUseCase: AddNewScheduleReviewUseCase
) : ViewModel() {

    private val _briefSchedule = MutableLiveData<BriefScheduleInfo?>()
    val briefSchedule: LiveData<BriefScheduleInfo?> get() = _briefSchedule

    private val _imageUriList = MutableLiveData<List<Uri>>()
    val imageUriList: LiveData<List<Uri>> get() = _imageUriList

    private val _imagePaths = MutableLiveData<List<ReviewImg>>()
    val imagePaths: LiveData<List<ReviewImg>> get() = _imagePaths

    private val _numOfImages = MutableLiveData<Int>(0)
    val numOfImages: LiveData<Int> get() = _numOfImages


    fun getBriefScheduleInfo(planId: Long){
        viewModelScope.launch {
            getBriefScheduleInfoUseCase(planId).onSuccess {
                _briefSchedule.postValue(it)
            }.onError {
                Log.e("getBriefScheduleInfo", "onError: $it")
            }
        }
    }

    fun addNewReviewImage(imgUri: Uri, imagePath: String){
        val currentUriList = _imageUriList.value?.toMutableList() ?: mutableListOf<Uri>()
        currentUriList.add(imgUri)
        currentUriList.let { _imageUriList.value = it }

        val currentPaths = _imagePaths.value?.toMutableList() ?: mutableListOf<ReviewImg>()
        currentPaths.add(ReviewImg(imagePath))
        currentPaths.let { _imagePaths.value = it }

        updateNumOfImages()
    }

    fun removeReviewImageFromList(position: Int){
        val currentUriList = _imageUriList.value?.toMutableList() ?: mutableListOf<Uri>()
        currentUriList.removeAt(position)
        currentUriList.let { _imageUriList.value = it }

        val currentPaths = _imagePaths.value?.toMutableList() ?: mutableListOf<ReviewImg>()
        currentPaths.removeAt(position)
        currentPaths.let { _imagePaths.value = it }

        updateNumOfImages()
    }

    private fun updateNumOfImages(){
        _numOfImages.value = _imageUriList.value?.size ?: 0
    }

    fun isMoreImageAttachable(): Boolean{
        val currentValue = _numOfImages.value ?: 0
        return currentValue in 0..3
    }

    fun submitScheduleReview(planId: Long, reviewDetail: NewScheduleReviewDetail, callback: (Boolean, Boolean) -> Unit){
        val images = _imagePaths.value?.toMutableList() ?: mutableListOf<ReviewImg>()
        viewModelScope.launch {
            var requestFlag = false
            val success = try {


                addNewScheduleReviewUseCase.invoke(planId, NewScheduleReview(reviewDetail), images)
                    .onSuccess {
                        Log.d("submitScheduleReview", "onSuccess ${it.toString()}")
                        requestFlag = true
                    }
                    .onError {
                        Log.d("submitScheduleReview", "onError ${it.toString()}")
                    }
                true
            } catch (e: Exception) {
                Log.d("submitScheduleReview", "Error: ${e.message}")
                false
            }
            callback(success, requestFlag)
        }

    }
}