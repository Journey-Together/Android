package kr.tekit.lion.daongil.presentation.main.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentSearchMainBinding
import kr.tekit.lion.daongil.domain.model.FakeAroundPlace
import kr.tekit.lion.daongil.presentation.ext.Permissions.LOCATION_PERMISSION_REQUEST_CODE
import kr.tekit.lion.daongil.presentation.ext.Permissions.REQUEST_LOCATION_PERMISSIONS
import kr.tekit.lion.daongil.presentation.ext.repeatOnViewStarted
import kr.tekit.lion.daongil.presentation.ext.setClickEvent
import kr.tekit.lion.daongil.presentation.ext.showPermissionSnackBar
import kr.tekit.lion.daongil.presentation.main.adapter.FakeHomeLocationRVAdapter
import kr.tekit.lion.daongil.presentation.main.customview.CategoryBottomSheet
import kr.tekit.lion.daongil.presentation.main.customview.FakeBottomSheet
import kr.tekit.lion.daongil.presentation.main.model.Category
import kr.tekit.lion.daongil.presentation.main.model.DisabilityType
import kr.tekit.lion.daongil.presentation.main.model.ScreenState
import kr.tekit.lion.daongil.presentation.main.vm.SearchMainViewModel
import kr.tekit.lion.daongil.presentation.main.vm.SearchMainViewModelFactory

class SearchMainFragment : Fragment(R.layout.fragment_search_main), OnMapReadyCallback {
    private val viewModel: SearchMainViewModel by viewModels {
        SearchMainViewModelFactory(
            requireContext()
        )
    }
    private lateinit var launcherForPermission: ActivityResultLauncher<Array<String>>
    lateinit var binding: FragmentSearchMainBinding

    // 내장 위치 추적 기능 사용
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(binding.pharamcyBottomSheet.emergencyBottomSheetLayout)
    }

    val placePos = ArrayList<LatLng>()
    val resPos = ArrayList<LatLng>()
    val roomPos = ArrayList<LatLng>()
    val placeDataList = ArrayList<FakeAroundPlace>()
    val resPlaceDataList = ArrayList<FakeAroundPlace>()
    val roomPlaceDataList = ArrayList<FakeAroundPlace>()

    private var selectedMarker: Marker? = null
    val markers = mutableListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatOnViewStarted {
            viewModel.listSearchOption.collect {
                Log.d("CurrentOption", it.toString())
            }
        }

        placeDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 정조로 833",
                listOf("1", "2", "3"),
                R.drawable.test_saerch_sima,
                "수원시립미술관",
                12
            )
        )
        placeDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 화서동 26-35",
                listOf("1", "2", "3", "4"),
                R.drawable.test_search_hwaseomoon,
                "화서공원",
                12
            )
        )
        placeDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 정조로 910 (장안동)",
                listOf("1", "2"),
                R.drawable.test_search_janganmoon,
                "장안문(長安門)",
                12
            )
        )
        placeDataList.add(
            FakeAroundPlace(
                "경기도 수원시 장안구 영화동 320-2",
                listOf("1", "2", "3"),
                R.drawable.test_search_hwasung,
                "수원 화성 [유네스코 세계유산]",
                12
            )
        )
        placeDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 동수원로 335 (인계동)",
                listOf("3", "4"),
                R.drawable.test_search_music,
                "수원야외음악당",
                12
            )
        )
        placeDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 화서문로72번길 9-6 (북수동)",
                listOf("1", "3", "4"),
                R.drawable.test_search_byuckwha,
                "행궁동 벽화마을",
                12
            )
        )
        placeDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 화서2동 264",
                listOf("1", "3", "4"),
                R.drawable.test_search_sookji,
                "숙지공원",
                12
            )
        )

        placeDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 화서동 436-1 (화서동)",
                listOf("1", "3", "4"),
                R.drawable.test_search_seoho,
                "서호공원",
                12
            )
        )


        resPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 영통구 센트럴파크로127번길 147 1층",
                listOf("1", "2", "3"),
                R.drawable.test_dear,
                "디어스윗랩",
                10
            )
        )
        resPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 영통구 센트럴타운로 85 ",
                listOf("1", "2", "3"),
                R.drawable.test_335,
                "335키친광교아브뉴프랑점",
                10
            )
        )
        resPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 장다리로 282",
                listOf("1"),
                R.drawable.test_search_gabojoung,
                "가보정갈비",
                10
            )
        )
        resPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 영통구 센트럴파크로127번길 18 (이의동) 1층",
                listOf("1", "2", "3"),
                R.drawable.test_ocho,
                "오늘의초밥",
                10
            )
        )
        resPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 장안구 경수대로 1196-3 (파장동)",
                listOf("2", "3"),
                R.drawable.test_zzambbong,
                "백세짬뽕북수원본점",
                10
            )
        )
        resPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 영통구 대학로 56",
                listOf("1", "2", "3"),
                R.drawable.test_jogae,
                "조개창고 수원점",
                10
            )
        )
        resPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 권선구 매송고색로 634-17 (고색동)",
                listOf("1", "3"),
                R.drawable.test_middle,
                "외식중학교",
                10
            )
        )
        resPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 영통구 광교호수공원로 80 (원천동,광교아이파크)",
                listOf("1"),
                R.drawable.test_book,
                "책발전소광교",
                10
            )
        )

        roomPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 영통구 광교호수공원로 320 (하동)",
                listOf("1", "2"),
                R.drawable.test_kot,
                "코트야드바이메리어트수원",
                10
            )
        )
        roomPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 권광로 132",
                listOf("1", "2", "3"),
                R.drawable.test_ibis,
                "이비스 앰배서더 수원",
                10
            )
        )
        roomPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 권선구 서부로1934번길 42",
                listOf("1", "2", "3"),
                R.drawable.test_patiz,
                "파티즈호텔",
                10
            )
        )
        roomPlaceDataList.add(
            FakeAroundPlace(
                "경기도 수원시 팔달구 중부대로 150",
                listOf("1", "3"),
                R.drawable.test_ramada,
                "라마다프라자 수원호텔",
                10
            )
        )

        placePos.add(LatLng(37.2826875, 127.0158125))
        placePos.add(LatLng(37.2859119, 127.0093633))
        placePos.add(LatLng(37.2888038, 127.0142055))
        placePos.add(LatLng(37.2826875, 127.0158125))
        placePos.add(LatLng(37.2819666, 127.013727))
        placePos.add(LatLng(37.2592956, 127.0363614))
        placePos.add(LatLng(37.2855639, 127.0165658))
        placePos.add(LatLng(37.2850278, 126.9981023))

        resPos.add(LatLng(37.2941323, 127.0554551))
        resPos.add(LatLng(37.2904195, 127.0497343))
        resPos.add(LatLng(37.2742409, 127.0286522))
        resPos.add(LatLng(37.2953257, 127.0552286))
        resPos.add(LatLng(37.300621, 127.0451996))
        resPos.add(LatLng(37.2480779, 126.9740915))
        resPos.add(LatLng(37.2742444, 127.0617536))
        resPos.add(LatLng(37.3061904, 127.000654))

        roomPos.add(LatLng(37.285056, 127.057861))
        roomPos.add(LatLng(37.2591458, 127.031397))
        roomPos.add(LatLng(37.2817201, 126.9716314))
        roomPos.add(LatLng(37.2775048, 127.032461))

        with(binding) {
            btnSearch.setOnClickListener {
                rvSearchResult.visibility = View.VISIBLE
                totalCnt.text = "7"
            }

            val placeAdapter = FakeHomeLocationRVAdapter(placeDataList.toList()) {}
            val resAdapter = FakeHomeLocationRVAdapter(resPlaceDataList.toList()) {}
            val roomAdapter = FakeHomeLocationRVAdapter(roomPlaceDataList.toList()) {}

            totalCnt.text = "0"
            rvSearchResult.adapter = placeAdapter
            rvSearchResult.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)

            listTabContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    // 탭이 선택되었을 때 수행할 작업
                    when (tab.position) {
                        0 -> {
                            viewModel.onSelectedTab(Category.PLACE.name)
                            rvSearchResult.adapter = placeAdapter
                            totalCnt.text = "7"
                        }

                        1 -> {
                            viewModel.onSelectedTab(Category.RESTAURANT.name)
                            rvSearchResult.adapter = resAdapter
                            totalCnt.text = "9"
                        }

                        2 -> {
                            viewModel.onSelectedTab(Category.ROOM.name)
                            rvSearchResult.adapter = roomAdapter
                            totalCnt.text = "4"
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            mapTabContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    // 탭이 선택되었을 때 수행할 작업
                    when (tab.position) {
                        0 -> {
                            markers.map {
                                it.map = null
                            }
                            markers.clear()
                            viewModel.onSelectedTab(Category.PLACE.name)
                            rvSearchResult.adapter = placeAdapter
                            totalCnt.text = "7"
                            if (viewModel.screenState.value == ScreenState.Map) {
                                placePos.mapIndexed { i, v ->
                                    addMaker(placeDataList[i], v)
                                }
                            }
                        }

                        1 -> {
                            markers.map {
                                it.map = null
                            }
                            viewModel.onSelectedTab(Category.RESTAURANT.name)
                            rvSearchResult.adapter = resAdapter
                            totalCnt.text = "9"
                            if (viewModel.screenState.value == ScreenState.Map) {
                                resPos.mapIndexed { i, v ->
                                    addResMaker(resPlaceDataList[i], v)
                                }
                            }
                        }

                        2 -> {
                            markers.map {
                                it.map = null
                            }
                            viewModel.onSelectedTab(Category.ROOM.name)
                            rvSearchResult.adapter = roomAdapter
                            totalCnt.text = "4"
                            if (viewModel.screenState.value == ScreenState.Map) {
                                roomPos.mapIndexed { i, v ->
                                    addRoomMaker(roomPlaceDataList[i], v)
                                }
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })


            selectedArea.doAfterTextChanged {
                if (it != null) {
                    detailAreaSelectLayout.visibility = View.VISIBLE
                    detailSelectedArea.text = null
                    viewModel.onCompleteSelectArea(it.toString())

                    categoryContainer.post {
                        categoryContainer.scrollTo(0, rvSearchResult.top)
                    }
                }
            }

            this@SearchMainFragment.repeatOnViewStarted {
                viewModel.areaCode.collect {
                    val areaList = it.map { area -> area.name }.toTypedArray()
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        areaList
                    )
                    selectedArea.setAdapter(adapter)
                }
            }

            selectedArea.doAfterTextChanged { viewModel.onSelectedArea(it.toString()) }

            this@SearchMainFragment.repeatOnViewStarted {
                viewModel.villageCode.collect {
                    val villageCodeList = it.map { area -> area.sigunguName }.toTypedArray()
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        villageCodeList
                    )
                    binding.detailSelectedArea.setAdapter(adapter)
                }
            }
            detailSelectedArea.doAfterTextChanged { viewModel.onSelectedSigungu(it.toString()) }

            this@SearchMainFragment.repeatOnViewStarted {
                viewModel.physicalDisabilityOptions.collect { options ->
                    btnPhysicalDisability.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.PhysicalDisability)
                    }

                    chipPhysicalDisability.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.PhysicalDisability)
                    }

                    val text = if (options.isNotEmpty()) {
                        imgPhysicalDisability.setImageResource(R.drawable.sv_selected_physical_disability_icon)
                        "${getString(R.string.text_physical_disability)}(${options.size})"
                    } else {
                        imgPhysicalDisability.setImageResource(R.drawable.sv_unselected_physical_disability_icon)
                        getString(R.string.text_physical_disability)
                    }
                    chipPhysicalDisability.text = text
                }
            }

            this@SearchMainFragment.repeatOnViewStarted {
                viewModel.visualImpairmentOptions.collect { options ->
                    btnVisualImpairment.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.VisualImpairment)
                    }

                    chipVisualImpairment.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.VisualImpairment)
                    }

                    val text = if (options.isNotEmpty()) {
                        imgVisualImpairment.setImageResource(R.drawable.sv_selected_visual_impairment_icon)
                        "${getString(R.string.text_visual_impairmnet)}(${options.size})"
                    } else {
                        imgVisualImpairment.setImageResource(R.drawable.sv_unselected_visual_impairment_icon)
                        getString(R.string.text_visual_impairmnet)
                    }
                    chipVisualImpairment.text = text

                }
            }

            this@SearchMainFragment.repeatOnViewStarted {
                viewModel.hearingImpairmentOptions.collect { options ->
                    btnHearingImpairment.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.HearingImpairment)
                    }

                    chipHearingImpairment.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.HearingImpairment)
                    }

                    val text = if (options.isNotEmpty()) {
                        imgHearingImpairment.setImageResource(R.drawable.sv_selected_hearing_impairment_icon)
                        "${getString(R.string.text_hearing_impairment)}(${options.size})"
                    } else {
                        imgHearingImpairment.setImageResource(R.drawable.sv_unselected_hearing_impairment_icon)
                        getString(R.string.text_hearing_impairment)
                    }
                    chipHearingImpairment.text = text
                }
            }

            this@SearchMainFragment.repeatOnViewStarted {
                viewModel.infantFamilyOptions.collect { options ->
                    btnInfantFamily.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.InfantFamily)
                    }

                    chipInfantFamilly.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.InfantFamily)
                    }

                    val text = if (options.isNotEmpty()) {
                        imgInfantFamily.setImageResource(R.drawable.sv_selected_infant_family_icon)
                        "${getString(R.string.text_infant_family)}(${options.size})"
                    } else {
                        imgInfantFamily.setImageResource(R.drawable.sv_unselected_infant_family_icon)
                        getString(R.string.text_infant_family)
                    }
                    chipInfantFamilly.text = text
                }
            }

            this@SearchMainFragment.repeatOnViewStarted {
                viewModel.elderlyPersonOptions.collect { options ->
                    btnElderlyPeople.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.ElderlyPeople)
                    }

                    chipElderlyPeople.setClickEvent(this) {
                        showBottomSheet(options, DisabilityType.ElderlyPeople)
                    }

                    val text = if (options.isNotEmpty()) {
                        imgElderlyPeople.setImageResource(R.drawable.sv_selected_elderly_people_icon)
                        "${getString(R.string.text_elderly_person)}(${options.size})"
                    } else {
                        imgElderlyPeople.setImageResource(R.drawable.sv_unselected_elderly_people_icon)
                        getString(R.string.text_elderly_person)
                    }
                    chipElderlyPeople.text = text
                }
            }

            this@SearchMainFragment.repeatOnViewStarted {
                viewModel.screenState.collect {
                    when (it) {
                        ScreenState.Map -> {
                            categoryContainer.visibility = View.GONE
                            mapContainer.visibility = View.VISIBLE
                            modeSwitchBtn.setText(R.string.watching_list)
                            modeSwitchBtn.setIconResource(R.drawable.list_icon)
                        }

                        ScreenState.List -> {
                            categoryContainer.visibility = View.VISIBLE
                            mapContainer.visibility = View.GONE
                            modeSwitchBtn.setText(R.string.watching_map)
                            modeSwitchBtn.setIconResource(R.drawable.map_icon)
                        }
                    }
                }
            }

            modeSwitchBtn.setOnClickListener {
                when (viewModel.screenState.value) {
                    ScreenState.List -> viewModel.changeScreenState(ScreenState.Map)
                    ScreenState.Map -> viewModel.changeScreenState(ScreenState.List)
                }
            }

            btnReset.setOnClickListener { viewModel.onClickResetIcon() }
        }

        val contracts = ActivityResultContracts.RequestMultiplePermissions()
        launcherForPermission = registerForActivityResult(contracts) { permissions ->
            if (permissions.any { it.value }) {
                permissionGrantedMapUiSetting()
            } else {
                // 하나 이상의 권한이 거부된 경우 처리할 작업
                permissions.forEach { (permission, isGranted) ->
                    when {
                        !isGranted -> {
                            if (!shouldShowRequestPermissionRationale(permission)) {
                                permissionDeniedMapUiSetting()
                                requireContext().showPermissionSnackBar(binding.root)
                            }
                        }

                        else -> {
                            permissionDeniedMapUiSetting()
                            requireContext().showPermissionSnackBar(binding.root)
                        }
                    }
                }

            }
        }

        initMap()
    }

    private fun showBottomSheet(selectedOptions: List<Int>, disabilityType: DisabilityType) {
        CategoryBottomSheet(selectedOptions, disabilityType) { optionIds, optionNames ->
            when (disabilityType) {
                is DisabilityType.PhysicalDisability -> {
                    viewModel.onSelectOption(
                        optionIds,
                        optionNames,
                        DisabilityType.PhysicalDisability
                    )
                }

                is DisabilityType.HearingImpairment -> {
                    viewModel.onSelectOption(
                        optionIds,
                        optionNames,
                        DisabilityType.HearingImpairment
                    )
                }

                is DisabilityType.VisualImpairment -> {
                    viewModel.onSelectOption(
                        optionIds,
                        optionNames,
                        DisabilityType.VisualImpairment
                    )
                }

                is DisabilityType.InfantFamily -> {
                    viewModel.onSelectOption(optionIds, optionNames, DisabilityType.InfantFamily)
                }

                is DisabilityType.ElderlyPeople -> {
                    viewModel.onSelectOption(optionIds, optionNames, DisabilityType.ElderlyPeople)
                }
            }
        }.show(parentFragmentManager, "bottomSheet")
    }

    private fun initMap() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // LocationSource 구현체를 지정
        // 네이버 지도 SDK에 위치를 제공하는 인터페이스
        mLocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        // 네이버맵 동적으로 불러오기
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    // 네이버맵 불러오기가 완료되면 콜백
    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcherForPermission.launch(REQUEST_LOCATION_PERMISSIONS)
        } else {
            permissionGrantedMapUiSetting()
            naverMap.addOnCameraChangeListener { reason: Int, animated: Boolean ->
                val bounds = naverMap.contentBounds
                val northEast = bounds.northEast
                val southWest = bounds.southWest

                val northWest = LatLng(northEast.latitude, southWest.longitude)
                val southEast = LatLng(southWest.latitude, northEast.longitude)

                val maxX = northEast.latitude
                val maxY = northEast.longitude
                val minX = southWest.latitude
                val southWestLng = southWest.longitude
                val northWestLat = northWest.latitude
                val northWestLng = northWest.longitude
                val southEastLat = southEast.latitude
                val minY = southEast.longitude

            }
            naverMap.minZoom = 10.0
            naverMap.maxZoom = 15.0

            // 사용자 현재 위치 받아오기
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                val latitude = location?.latitude ?: 35.1798159
                val longitude = location?.longitude ?: 129.0750222

                // 위치 오버레이의 가시성은 기본적으로 false로 지정되어 있습니다. 가시성을 true로 변경하면 지도에 위치 오버레이가 나타납니다.
                // 파랑색 점, 현재 위치 표시
                with(naverMap.locationOverlay) {
                    isVisible = true
                    position = LatLng(latitude, longitude)
                }

                // 카메라 현재 위치로 이동
                val cameraUpdate = CameraUpdate.scrollTo(
                    LatLng(
                        latitude,
                        longitude
                    )
                )
                naverMap.moveCamera(cameraUpdate)
            }

            viewModel.listSearchOption
        }

        placePos.mapIndexed { i, v -> addMaker(placeDataList[i], v) }

    }

    private fun permissionGrantedMapUiSetting() {
        with(naverMap) {
            setMapType(this)

            // 내장 위치 추적 기능 사용
            locationSource = mLocationSource

            naverMap.addOnOptionChangeListener {
                val mode = naverMap.locationTrackingMode.name
                val currentLocation = mLocationSource.lastLocation

                when (mode) {
                    "None" -> locationTrackingMode = LocationTrackingMode.Follow
                    "Follow", "NoFollow" -> {
                        // 현재 위치 버튼을 눌렀을 때 카메라가 줌이 너무 작아지는걸 방지
                        if (currentLocation != null) {
                            val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                            val cameraPosition = CameraPosition(latLng, 14.0)

                            naverMap.moveCamera(
                                CameraUpdate.toCameraPosition(cameraPosition)
                                    .animate(
                                        com.naver.maps.map.CameraAnimation.Easing
                                    )
                            )
                        }
                    }
                }
            }

            with(naverMap.locationOverlay) {
                val color = getColor(requireContext(), R.color.maker_overlay)
                circleRadius = 200
                // setAlphaComponent : 투명도 지정
                // 0(완전 투명) ~ 255(완전 불투명)
                circleColor = androidx.core.graphics.ColorUtils.setAlphaComponent(color, 90)
            }


            locationTrackingMode = LocationTrackingMode.Follow

            // 건물 내부 표시
            isIndoorEnabled = true

            with(uiSettings) {
                // 줌버튼
                isZoomControlEnabled = false

                // 실내지도 층 피커
                isIndoorLevelPickerEnabled = true

                // 축적바
                isScaleBarEnabled = true

                // 현위치 버튼
                isLocationButtonEnabled = true
            }
        }
    }

    private fun permissionDeniedMapUiSetting() {
        with(naverMap) {
            setMapType(this)

            // 건물 내부 표시
            isIndoorEnabled = true

            with(uiSettings) {
                // 줌버튼
                isZoomControlEnabled = false

                // 실내지도 층 피커
                isIndoorLevelPickerEnabled = true

                // 축적바
                isScaleBarEnabled = true
            }
        }
    }

    private fun setBottomRecylcerView(pharmacyBottomList: List<FakeAroundPlace>) {
        val pharmacyBottomSheet = FakeBottomSheet(binding.pharamcyBottomSheet, pharmacyBottomList)
        pharmacyBottomSheet.setRecyclerView()
        pharmacyBottomSheet.recyclerViewTopButton()
    }

    private fun addMaker(info: FakeAroundPlace, pos: LatLng) {
        val marker = Marker()
        with(marker) {
            icon = OverlayImage.fromResource(R.drawable.maker_unselected_tourist_spot_icon)
            position = LatLng(
                pos.latitude,
                pos.longitude

            )
            map = naverMap
            width = 86
            height = 90
            this.isHideCollidedMarkers = true
            this.isForceShowIcon = false

            setOnClickListener {
                this@SearchMainFragment.selectedMarker?.let { previousMarker ->
                    previousMarker.icon =
                        OverlayImage.fromResource(R.drawable.maker_unselected_tourist_spot_icon)
                    previousMarker.isHideCollidedMarkers = true
                    previousMarker.isForceShowIcon = false
                    previousMarker.width = 86
                    previousMarker.height = 86
                    previousMarker.zIndex = 0
                }

                selectedMarker = this

                // 클릭된 마커를 선택 상태로 변경
                this.icon = OverlayImage.fromResource(R.drawable.maker_selected_tourist_spot_icon)
                this.isHideCollidedMarkers = true
                this.isForceShowIcon = true
                this.width = 100
                this.height = 130
                this.zIndex = 10

                setBottomRecylcerView(listOf(info))
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                true
            }

            markers.add(marker)
        }
    }

    private fun addResMaker(info: FakeAroundPlace, pos: LatLng) {
        val marker = Marker()
        with(marker) {
            icon = OverlayImage.fromResource(R.drawable.maker_unselected_restaurant_icon)
            position = LatLng(
                pos.latitude,
                pos.longitude

            )
            map = naverMap
            width = 86
            height = 90
            this.isHideCollidedMarkers = true
            this.isForceShowIcon = false

            setOnClickListener {
                this@SearchMainFragment.selectedMarker?.let { previousMarker ->
                    previousMarker.icon =
                        OverlayImage.fromResource(R.drawable.maker_unselected_restaurant_icon)
                    previousMarker.isHideCollidedMarkers = true
                    previousMarker.isForceShowIcon = false
                    previousMarker.width = 86
                    previousMarker.height = 86
                    previousMarker.zIndex = 0
                }

                selectedMarker = this

                // 클릭된 마커를 선택 상태로 변경
                this.icon = OverlayImage.fromResource(R.drawable.maker_selected_restauraunt_icon)
                this.isHideCollidedMarkers = true
                this.isForceShowIcon = true
                this.width = 100
                this.height = 130
                this.zIndex = 10

                setBottomRecylcerView(listOf(info))
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                true
            }
            markers.add(marker)

        }
    }

    private fun addRoomMaker(info: FakeAroundPlace, pos: LatLng) {
        val marker = Marker()
        with(marker) {
            icon = OverlayImage.fromResource(R.drawable.maker_unselected_lodging_icon)
            position = LatLng(
                pos.latitude,
                pos.longitude

            )
            map = naverMap
            width = 86
            height = 90
            this.isHideCollidedMarkers = true
            this.isForceShowIcon = false

            setOnClickListener {
                this@SearchMainFragment.selectedMarker?.let { previousMarker ->
                    previousMarker.icon =
                        OverlayImage.fromResource(R.drawable.maker_unselected_lodging_icon)
                    previousMarker.isHideCollidedMarkers = true
                    previousMarker.isForceShowIcon = false
                    previousMarker.width = 86
                    previousMarker.height = 86
                    previousMarker.zIndex = 0
                }

                selectedMarker = this

                // 클릭된 마커를 선택 상태로 변경
                this.icon = OverlayImage.fromResource(R.drawable.maker_selected_lodging_icon)
                this.isHideCollidedMarkers = true
                this.isForceShowIcon = true
                this.width = 100
                this.height = 130
                this.zIndex = 10

                setBottomRecylcerView(listOf(info))
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                true
            }
            markers.add(marker)

        }
    }

    private fun isNightMode(): Boolean {
        val currentNightMode =
            this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun setMapType(naverMap: NaverMap) {
        if (isNightMode()) {
            // 야간 모드 허용
            naverMap.mapType = NaverMap.MapType.Navi
            naverMap.isNightModeEnabled = true
        } else {
            naverMap.mapType = NaverMap.MapType.Basic
            naverMap.isNightModeEnabled = false
        }
    }
}
