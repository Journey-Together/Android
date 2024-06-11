package kr.tekit.lion.daongil.presentation.home.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.usecase.GetPlaceDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class DetailViewModel(
    private val getPlaceDetailInfoUseCase: GetPlaceDetailInfoUseCase
) : ViewModel() {
    private val _detailPlaceInfo = MutableLiveData<PlaceDetailInfo>()
    val detailPlaceInfo : LiveData<PlaceDetailInfo> = _detailPlaceInfo

    fun getDetailPlace(placeId: Long) = viewModelScope.launch {
        getPlaceDetailInfoUseCase(placeId).onSuccess {
            Log.d("getDetailPlace", it.toString())
            _detailPlaceInfo.value = it
        }.onError {
            Log.d("getDetailPlace", it.toString())
        }
    }
}