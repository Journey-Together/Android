package kr.tekit.lion.daongil.presentation.myinfo.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.IceInfo
import kr.tekit.lion.daongil.domain.model.PersonalInfo
import kr.tekit.lion.daongil.domain.model.ProfileImg
import kr.tekit.lion.daongil.domain.usecase.myinfo.GetMyInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.myinfo.ModifyMyIceInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.myinfo.ModifyMyPersonalInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.myinfo.ModifyMyProfileImageUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.presentation.myinfo.ModifyState

class MyInfoViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val modifyMyIceInfoUseCase: ModifyMyIceInfoUseCase,
    private val modifyMyPersonalInfoUseCase: ModifyMyPersonalInfoUseCase,
    private val modifyMyProfileImageUseCase: ModifyMyProfileImageUseCase
) : ViewModel() {

    private val _modifyState = MutableStateFlow<ModifyState>(ModifyState.ImgUnSelected)
    val modifyState = _modifyState.asStateFlow()

    private val _myPersonalInfo = MutableStateFlow(PersonalInfo())
    val myPersonalInfo = _myPersonalInfo.asStateFlow()

    private val _profileImg = MutableStateFlow("")
    val profileImg = _profileImg.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _IceInfo = MutableStateFlow(IceInfo())
    val myIceInfo = _IceInfo.asStateFlow()

    init {
        initUiData()
    }

    private fun initUiData() = viewModelScope.launch {
        getMyInfoUseCase().onSuccess {
            _name.value = it.name ?: ""

            _myPersonalInfo.value = PersonalInfo(
                nickname = it.nickname ?: "",
                phone = it.phone ?: ""
            )

            _profileImg.value = it.profileImage ?: ""

            _IceInfo.value = IceInfo(
                bloodType = it.bloodType ?: "",
                birth = it.birth ?: "",
                disease = it.disease ?: "",
                allergy = it.allergy ?: "",
                medication = it.medication ?: "",
                part1Rel = it.part1Rel ?: "",
                part1Phone = it.part1Phone ?: "",
                part2Rel = it.part2Rel ?: "",
                part2Phone = it.part2Phone ?: ""
            )
        }.onError {
            Log.d("MyOkHttpResult", it.toString())
        }
    }

    fun onCompleteModifyPersonal(nickname: String, phone: String) {
        _myPersonalInfo.value = myPersonalInfo.value.copy(
            nickname = nickname,
            phone = phone
        )
        viewModelScope.launch {
            modifyMyPersonalInfoUseCase(PersonalInfo(nickname, phone))
        }
    }

    fun onCompleteModifyPersonalWithImg(nickname: String, phone: String) {
        viewModelScope.launch {
            onCompleteModifyPersonal(nickname, phone)
            modifyMyProfileImageUseCase(ProfileImg(profileImg.value))
                .onSuccess {
                    Log.d("MyOkHttpResult", it.toString())
                }.onError {
                    Log.d("MyOkHttpResult", it.toString())

                }
        }
    }

    fun onCompleteModifyIce(iceInfo: IceInfo) {
        _IceInfo.value = iceInfo
        viewModelScope.launch { modifyMyIceInfoUseCase(iceInfo) }
    }

    fun onSelectProfileImage(imgUrl: String?) {
        imgUrl?.let { _profileImg.value = it }
    }

    fun modifyStateChange() {
        _modifyState.value = ModifyState.ImgSelected
    }
}