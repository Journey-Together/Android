package kr.tekit.lion.daongil.presentation.emergency.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.usecase.GetUserLocationRegionUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess

class PharmacyMapViewModel(
    private val getUserLocationRegionUseCase: GetUserLocationRegionUseCase
) : ViewModel() {

    private val _area = MutableLiveData<String?>()
    val area : LiveData<String?> = _area

    private val _areaUpdate = MutableLiveData<Unit>()
    val areaUpdate : LiveData<Unit> = _areaUpdate

    fun getUserLocationRegion(coords: String) =
        viewModelScope.launch {
            getUserLocationRegionUseCase(coords).onSuccess {
                if (it.code == 0) {
                    _area.value = "${it.results[0].area} ${it.results[0].areaDetail}"
                    _areaUpdate.value = Unit
                }
            }
        }

    fun setArea(area: String?, areaDetail: String?) {
        _area.value = "$area $areaDetail"
        _areaUpdate.value = Unit
    }

}