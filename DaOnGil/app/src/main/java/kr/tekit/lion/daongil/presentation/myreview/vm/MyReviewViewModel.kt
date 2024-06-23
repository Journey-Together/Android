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

    private val _myPlaceReviewList = MutableLiveData<List<MyPlaceReview>>()
    val myPlaceReviewList: LiveData<List<MyPlaceReview>> = _myPlaceReviewList

    init {
        getMyPlaceReview(5, 0)
    }

    private fun getMyPlaceReview(size: Int, page: Int) = viewModelScope.launch {
        getMyPlaceReviewUseCase(size, page).onSuccess {
            _myPlaceReviewList.value = it
        }.onError {
            Log.d("getMyPlaceReview", it.toString())
        }
    }
}