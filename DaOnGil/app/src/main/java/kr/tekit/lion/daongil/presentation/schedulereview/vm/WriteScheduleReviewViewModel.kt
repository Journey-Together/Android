package kr.tekit.lion.daongil.presentation.schedulereview.vm

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.BriefScheduleInfo
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

    fun addNewReviewImage(imgUri: Uri){
        val currentUriList = _imageUriList.value?.toMutableList() ?: mutableListOf<Uri>()
        currentUriList.add(imgUri)
        currentUriList.let { _imageUriList.value = it }

        updateNumOfImages()
    }

    fun removeReviewImageFromList(position: Int){
        val currentUriList = _imageUriList.value?.toMutableList() ?: mutableListOf<Uri>()
        currentUriList.removeAt(position)
        currentUriList.let { _imageUriList.value = it }

        updateNumOfImages()

    }

    private fun updateNumOfImages(){
        _numOfImages.value = _imageUriList.value?.size ?: 0
    }

    fun isMoreImageAttachable(): Boolean{
        val currentValue = _numOfImages.value ?: 0
        return currentValue in 0..3
    }

}