package kr.tekit.lion.daongil.presentation.main.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.AroundPlace
import kr.tekit.lion.daongil.domain.model.RecommendPlace
import kr.tekit.lion.daongil.domain.usecase.GetPlaceMainInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAreaCodeByNameUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetVillageCodeByNameUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class HomeViewModel(
    private val getAreaCodeByNameUseCase: GetAreaCodeByNameUseCase,
    private val getPlaceMainInfoUseCase: GetPlaceMainInfoUseCase,
    private val getVillageCodeByNameUseCase: GetVillageCodeByNameUseCase
) : ViewModel() {

    init {
        getAreaCode()
        getVillageCode()
    }

    private val _aroundPlaceInfo = MutableLiveData<List<AroundPlace>>()
    val aroundPlaceInfo : LiveData<List<AroundPlace>> = _aroundPlaceInfo

    private val _recommendPlaceInfo = MutableLiveData<List<RecommendPlace>>()
    val recommendPlaceInfo : LiveData<List<RecommendPlace>> = _recommendPlaceInfo

    fun getAreaCode(area: String = "대전") = viewModelScope.launch {
        val result = getAreaCodeByNameUseCase(area)
        Log.d("getAreaCodeResult", result.toString())
    }

    fun getVillageCode(village: String = "중구") = viewModelScope.launch {
        val result = getVillageCodeByNameUseCase(village)
        Log.d("getAreaCodeResult", result.toString())
    }

    fun getPlaceMain(areaCode: String, sigunguCode: String) = viewModelScope.launch {
        getPlaceMainInfoUseCase(areaCode, sigunguCode).onSuccess {
            Log.d("getPlaceMain", it.toString())
            _aroundPlaceInfo.value = it.aroundPlaceList
            _recommendPlaceInfo.value = it.recommendPlaceList

        }.onError {
            Log.d("getPlaceMain", it.toString())
        }
    }

}