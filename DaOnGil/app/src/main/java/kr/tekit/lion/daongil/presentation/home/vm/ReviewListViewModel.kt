package kr.tekit.lion.daongil.presentation.home.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.PlaceReviewInfo
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceReviewListUseCase

class ReviewListViewModel(
    private val getPlaceReviewListUseCase: GetPlaceReviewListUseCase
) : ViewModel() {
    private val _placeReviewInfo = MutableLiveData<PlaceReviewInfo>()
    val placeReviewInfo : LiveData<PlaceReviewInfo> = _placeReviewInfo

    fun getPlaceReview(placeId: Long, size: Int, page: Int) = viewModelScope.launch {
        getPlaceReviewListUseCase(placeId, size, page).onSuccess {
            Log.d("getPlaceReview", it.toString())
            _placeReviewInfo.value = it
        }.onError {
            Log.d("getPlaceReview", it.toString())
        }
    }
}