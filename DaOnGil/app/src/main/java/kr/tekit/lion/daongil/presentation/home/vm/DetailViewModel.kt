package kr.tekit.lion.daongil.presentation.home.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfoGuest
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.usecase.GetPlaceDetailInfoGuestUseCase
import kr.tekit.lion.daongil.domain.usecase.GetPlaceDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.UpdatePlaceBookmarkUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.presentation.login.LogInState

class DetailViewModel(
    private val authRepository: AuthRepository,
    private val getPlaceDetailInfoUseCase: GetPlaceDetailInfoUseCase,
    private val getPlaceDetailInfoGuestUseCase : GetPlaceDetailInfoGuestUseCase,
    private val updateBookmarkUseCase: UpdatePlaceBookmarkUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<LogInState>(LogInState.Checking)
    val loginState = _loginState.asStateFlow()

    private val _detailPlaceInfo = MutableLiveData<PlaceDetailInfo>()
    val detailPlaceInfo : LiveData<PlaceDetailInfo> = _detailPlaceInfo

    private val _detailPlaceInfoGuest = MutableLiveData<PlaceDetailInfoGuest>()
    val detailPlaceInfoGuest : LiveData<PlaceDetailInfoGuest> = _detailPlaceInfoGuest

    init {
        viewModelScope.launch {
            checkLoginState()
        }
    }

    private suspend fun checkLoginState(){
        authRepository.loggedIn.collect{ isLoggedIn ->
            if (isLoggedIn) _loginState.value = LogInState.LoggedIn
            else _loginState.value = LogInState.LoginRequired
        }
    }

    fun getDetailPlace(placeId: Long) = viewModelScope.launch {
        getPlaceDetailInfoUseCase(placeId).onSuccess {
            Log.d("getDetailPlace", it.toString())
            _detailPlaceInfo.value = it
        }.onError {
            Log.d("getDetailPlace", it.toString())
        }
    }

    fun getDetailPlaceGuest(placeId: Long) = viewModelScope.launch {
        getPlaceDetailInfoGuestUseCase(placeId).onSuccess {
            Log.d("getDetailPlaceGuest", it.toString())
            _detailPlaceInfoGuest.value = it
        }.onError {
            Log.d("getDetailPlaceGuest", it.toString())
        }
    }

    fun updateDetailPlaceBookmark(placeId: Long) = viewModelScope.launch {
        updateBookmarkUseCase(placeId).onSuccess {
            Log.d("updateDetailPlaceBookmark", it.toString())
        }.onError {
            Log.d("updateDetailPlaceBookmark", it.toString())
        }
    }
}