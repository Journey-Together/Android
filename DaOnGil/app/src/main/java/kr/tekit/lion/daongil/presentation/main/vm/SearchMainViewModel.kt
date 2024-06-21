package kr.tekit.lion.daongil.presentation.main.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.model.ListSearchOption
import kr.tekit.lion.daongil.domain.model.ListSearchResult
import kr.tekit.lion.daongil.domain.model.MapSearchOption
import kr.tekit.lion.daongil.domain.model.SigunguCode
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllAreaCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.areacode.GetAllSigunguCodeUseCase
import kr.tekit.lion.daongil.domain.usecase.base.onError
import kr.tekit.lion.daongil.domain.usecase.base.onSuccess
import kr.tekit.lion.daongil.domain.usecase.place.GetSearchPlaceByList
import kr.tekit.lion.daongil.domain.usecase.place.GetSearchPlaceResultForMap
import kr.tekit.lion.daongil.presentation.main.model.Category
import kr.tekit.lion.daongil.presentation.main.model.DisabilityType
import kr.tekit.lion.daongil.presentation.main.model.ScreenState
import java.util.TreeSet

class SearchMainViewModel(
    private val getAllAreaCodeUseCase: GetAllAreaCodeUseCase,
    private val getAllSigunguCodeUseCase: GetAllSigunguCodeUseCase,
    private val getSearchPlaceByList: GetSearchPlaceByList,
    private val getSearchPlaceResultForMap: GetSearchPlaceResultForMap
) : ViewModel() {

    init {
        viewModelScope.launch {
            _areaCode.value = getAllAreaCodeUseCase()

        }
    }

    private val _mapSearchResult =
        MutableStateFlow(MapSearchOption(Category.PLACE.name, 0.0, 0.0, 0.0, 0.0))

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val mapSearchResult = _mapSearchResult
        .debounce(800)
        .flatMapLatest { request ->
            getSearchPlaceResultForMap(request)
        }.flowOn(Dispatchers.IO)
        .catch { e: Throwable ->
            e.printStackTrace()
        }

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.List)
    val screenState: StateFlow<ScreenState> get() = _screenState.asStateFlow()

    private val _listSearchOption = MutableStateFlow(ListSearchOption(Category.PLACE.name, 0, 0))
    val listSearchOption get() = _listSearchOption.asStateFlow()

    private val _areaCode = MutableStateFlow<List<AreaCode>>(emptyList())
    val areaCode get() = _areaCode.asStateFlow()

    private val _sigunguCode = MutableStateFlow<List<SigunguCode>>(emptyList())
    val villageCode get() = _sigunguCode.asStateFlow()

    // BottomSheet 에서 선택된 항목을 들을 유지하기 위한 layout ID
    private val _physicalDisabilityOptions = MutableStateFlow<List<Int>>(emptyList())
    val physicalDisabilityOptions get() = _physicalDisabilityOptions.asStateFlow()

    private val _hearingImpairmentOptions = MutableStateFlow<List<Int>>(emptyList())
    val hearingImpairmentOptions get() = _hearingImpairmentOptions.asStateFlow()

    private val _visualImpairmentOptions = MutableStateFlow<List<Int>>(emptyList())
    val visualImpairmentOptions get() = _visualImpairmentOptions.asStateFlow()

    private val _infantFamilyOptions = MutableStateFlow<List<Int>>(emptyList())
    val infantFamilyOptions get() = _infantFamilyOptions.asStateFlow()

    private val _elderlyPersonOptions = MutableStateFlow<List<Int>>(emptyList())
    val elderlyPersonOptions get() = _elderlyPersonOptions.asStateFlow()

    // 리스트 검색 결과
    private val _listSearchResult = MutableStateFlow<List<ListSearchResult>>(emptyList())
    val listSearchResult get() = _listSearchResult.asStateFlow()

    fun changeScreenState(state: ScreenState) {
        _screenState.value = state
    }

    fun onSelectedTab(category: String) {
        _listSearchOption.value = _listSearchOption.value.copy(category = category)
    }

    fun onSelectedArea(areaName: String) {
        val areaCode = areaCode.value.find { it.name == areaName }?.code
        _listSearchOption.value = _listSearchOption.value.copy(areaCode = areaCode)
    }

    fun onSelectedSigungu(sigunguName: String) {
        val sigunguCode = _sigunguCode.value.find { it.sigunguName == sigunguName }?.sigunguCode
        _listSearchOption.value = _listSearchOption.value.copy(sigunguCode = sigunguCode)
    }

    fun onSelectOption(optionIds: List<Int>, optionNames: List<String>, type: DisabilityType) {
        val updatedTypes = _listSearchOption.value.disabilityType
        val updatedOptions = _listSearchOption.value.detailFilter?.toMutableSet()

        if (optionIds.isEmpty()) {
            when (type) {
                is DisabilityType.PhysicalDisability -> {
                    updatedTypes?.remove(DisabilityType.PhysicalDisability.type)
                }

                is DisabilityType.VisualImpairment -> {
                    updatedTypes?.remove(DisabilityType.VisualImpairment.type)
                }

                is DisabilityType.HearingImpairment -> {
                    updatedTypes?.remove(DisabilityType.HearingImpairment.type)
                }

                is DisabilityType.InfantFamily -> {
                    updatedTypes?.remove(DisabilityType.InfantFamily.type)
                }

                is DisabilityType.ElderlyPeople -> {
                    updatedTypes?.remove(DisabilityType.ElderlyPeople.type)
                }
            }
            optionNames.map { updatedOptions?.remove(it) }
            _physicalDisabilityOptions.value = optionIds

        } else {
            when (type) {
                is DisabilityType.PhysicalDisability -> {
                    updatedTypes?.add(DisabilityType.PhysicalDisability.type)
                }

                is DisabilityType.VisualImpairment -> {
                    updatedTypes?.add(DisabilityType.VisualImpairment.type)
                }

                is DisabilityType.HearingImpairment -> {
                    updatedTypes?.add(DisabilityType.HearingImpairment.type)
                }

                is DisabilityType.InfantFamily -> {
                    updatedTypes?.add(DisabilityType.InfantFamily.type)
                }

                is DisabilityType.ElderlyPeople -> {
                    updatedTypes?.add(DisabilityType.ElderlyPeople.type)
                }
            }
            optionNames.map { updatedOptions?.add(it) }
        }

        when (type) {
            is DisabilityType.PhysicalDisability -> {
                _physicalDisabilityOptions.value = optionIds
            }

            is DisabilityType.VisualImpairment -> {
                _visualImpairmentOptions.value = optionIds
            }

            is DisabilityType.HearingImpairment -> {
                _hearingImpairmentOptions.value = optionIds
            }

            is DisabilityType.InfantFamily -> {
                _infantFamilyOptions.value = optionIds
            }

            is DisabilityType.ElderlyPeople -> {
                _elderlyPersonOptions.value = optionIds
            }
        }
        _listSearchOption.value = _listSearchOption.value.copy(
            disabilityType = updatedTypes,
            detailFilter = updatedOptions
        )
    }

    fun onCompleteSelectArea(areaName: String) = viewModelScope.launch {
        val findAreaCode = areaCode.value.find { it.name == areaName }?.code
        if (findAreaCode != null) {
            _sigunguCode.value = getAllSigunguCodeUseCase(findAreaCode)
        }
    }

    fun onClickSearchButton() = viewModelScope.launch {
        listSearchOption.collect {
            getSearchPlaceByList(it).onSuccess { response ->
                _listSearchResult.value = response
            }.onError {
                Log.d("MyOkhttpResult", it.toString())
            }
        }
    }

    fun whenLastPageReached() = viewModelScope.launch {
        _listSearchOption.value = _listSearchOption.value.copy(
            page = _listSearchOption.value.page + 1
        )

        getSearchPlaceByList(listSearchOption.value).onSuccess { response ->
            _listSearchResult.value += response
        }.onError {
            Log.d("MyOkhttpResult", it.toString())
        }
    }


    fun onClickResetIcon() {
        _listSearchOption.value = _listSearchOption.value.copy(
            disabilityType = TreeSet(),
            detailFilter = TreeSet()
        )

        _physicalDisabilityOptions.value = emptyList()
        _hearingImpairmentOptions.value = emptyList()
        _visualImpairmentOptions.value = emptyList()
        _infantFamilyOptions.value = emptyList()
        _elderlyPersonOptions.value = emptyList()

    }


}
