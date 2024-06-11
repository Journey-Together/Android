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
import kr.tekit.lion.daongil.domain.usecase.areacode.GetSigunguCodeByNameUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class HomeViewModel(
    private val getAreaCodeByNameUseCase: GetAreaCodeByNameUseCase,
    private val getPlaceMainInfoUseCase: GetPlaceMainInfoUseCase,
    private val getSigunguCodeByNameUseCase: GetSigunguCodeByNameUseCase
) : ViewModel() {

    private val _aroundPlaceInfo = MutableLiveData<List<AroundPlace>>()
    val aroundPlaceInfo : LiveData<List<AroundPlace>> = _aroundPlaceInfo

    private val _recommendPlaceInfo = MutableLiveData<List<RecommendPlace>>()
    val recommendPlaceInfo : LiveData<List<RecommendPlace>> = _recommendPlaceInfo

    fun getPlaceMain(area: String, sigungu: String) = viewModelScope.launch {
        val areaCode = getAreaCodeByNameUseCase(area)
        val sigunguCode = getSigunguCodeByNameUseCase(sigungu)
        Log.e("area viewModel", areaCode.toString())
        Log.e("sigungu viewModel", sigunguCode.toString())
        if (areaCode != null && sigunguCode != null) {
            getPlaceMainInfoUseCase(areaCode, sigunguCode).onSuccess {
                Log.d("getPlaceMain", it.toString())
                _aroundPlaceInfo.value = it.aroundPlaceList
                _recommendPlaceInfo.value = it.recommendPlaceList

            }.onError {
                Log.d("getPlaceMain", it.toString())
            }
        }
    }
}