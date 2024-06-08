package kr.tekit.lion.daongil.presentation.myinfo.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.MyIceInfo
import kr.tekit.lion.daongil.domain.model.MyPersonalInfo
import kr.tekit.lion.daongil.domain.usecase.GetMyInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.ModifyMyIceInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.ModifyMyPersonalInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.ModifyMyProfileImageUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.presentation.myinfo.ModifyState
import java.io.File

class MyInfoViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val modifyMyIceInfoUseCase: ModifyMyIceInfoUseCase,
    private val modifyMyPersonalInfoUseCase: ModifyMyPersonalInfoUseCase,
    private val modifyMyProfileImageUseCase: ModifyMyProfileImageUseCase
) : ViewModel() {

    private val _modifyState = MutableStateFlow<ModifyState>(ModifyState.ImgUnSelected)
    val modifyState = _modifyState.asStateFlow()

    private val _myPersonalInfo = MutableStateFlow(MyPersonalInfo())
    val myPersonalInfo = _myPersonalInfo.asStateFlow()

    private val _profileImg = MutableStateFlow("")
    val profileImg = _profileImg.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _myIceInfo = MutableStateFlow(MyIceInfo())
    val myIceInfo = _myIceInfo.asStateFlow()

    init {
        initViewData()
    }

    private fun initViewData() = viewModelScope.launch {
        getMyInfoUseCase().onSuccess {
            _myPersonalInfo.value = MyPersonalInfo(
                nickname = it.nickname ?: "",
                phone = it.phone ?: ""
            )

            _profileImg.value = it.profileImage ?: ""

            _myIceInfo.value = MyIceInfo(
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

    fun onSelectProfileImage(imgUrl: String?) {
        Log.d("dasdas", imgUrl.toString())
        imgUrl?.let { _profileImg.value = it }
    }

    fun onCompleteModifyPersonal(myPersonalInfo: MyPersonalInfo) {
        _myPersonalInfo.value = myPersonalInfo
        viewModelScope.launch {
            modifyMyPersonalInfoUseCase(myPersonalInfo)
        }
    }

    fun onCompleteModifyPersonalWithImg(myPersonalInfo: MyPersonalInfo) {
        _myPersonalInfo.value = myPersonalInfo

        viewModelScope.launch {
            modifyMyPersonalInfoUseCase(myPersonalInfo)
            modifyMyProfileImageUseCase(profileImg.value).onSuccess {
                Log.d("MyOkHttpResult", it.toString())

            }.onError {
                Log.d("MyOkHttpResult", it.toString())
            }
        }
    }

    fun onCompleteModifyIce(myIceInfo: MyIceInfo) {
        _myIceInfo.value = myIceInfo
        viewModelScope.launch {
            modifyMyIceInfoUseCase(myIceInfo).onSuccess {
                Log.d("MyOkHttpResult", "ModifyIce Complete")
            }.onError {
                Log.d("MyOkHttpResult", "ModifyIce Failed $it")
            }
        }
    }

    fun modifyStateChange() {
        _modifyState.value = ModifyState.ImgSelected
    }

    fun whenGetStringExtra(name: String) {
        _name.value = name
    }
}