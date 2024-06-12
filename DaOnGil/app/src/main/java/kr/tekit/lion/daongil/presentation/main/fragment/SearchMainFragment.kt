package kr.tekit.lion.daongil.presentation.main.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import kr.tekit.lion.daongil.presentation.ext.Permissions.LOCATION_PERMISSION_REQUEST_CODE
import kr.tekit.lion.daongil.presentation.ext.Permissions.REQUEST_LOCATION_PERMISSIONS
import kr.tekit.lion.daongil.presentation.ext.repeatOnViewStarted
import kr.tekit.lion.daongil.presentation.ext.setClickEvent
import kr.tekit.lion.daongil.presentation.ext.showPermissionSnackBar
import kr.tekit.lion.daongil.presentation.main.customview.CategoryBottomSheet
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

    // 내장 위치 추적 기능 사용
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchMainBinding.bind(view)

        repeatOnViewStarted {
            viewModel.searchOption.collect{
                Log.d("CurrentOption", it.toString())
            }
        }

        with(binding) {

            tabContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    // 탭이 선택되었을 때 수행할 작업
                    when (tab.position) {
                        0 -> viewModel.onSelectedTab(Category.PLACE.name)
                        1 -> viewModel.onSelectedTab(Category.RESTAURANT.name)
                        2 -> viewModel.onSelectedTab(Category.ROOM.name)
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
                viewModel.screenState.collect{
                    when(it){
                        ScreenState.Map -> {
                            categoryContainer.visibility = View.GONE
                            mapContainer.visibility = View.VISIBLE
                            modeSwitchBtn.setText(R.string.watching_list)
                            modeSwitchBtn.setIconResource(R.drawable.list_icon)
                        }
                        ScreenState.List ->{
                            categoryContainer.visibility = View.VISIBLE
                            mapContainer.visibility = View.GONE
                            modeSwitchBtn.setText(R.string.watching_map)
                            modeSwitchBtn.setIconResource(R.drawable.map_icon)
                        }
                    }
                }
            }

            modeSwitchBtn.setOnClickListener {
                when(viewModel.screenState.value){
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
                    viewModel.onSelectOption(optionIds, optionNames, DisabilityType.PhysicalDisability)
                }
                is DisabilityType.HearingImpairment -> {
                    viewModel.onSelectOption(optionIds, optionNames, DisabilityType.HearingImpairment)
                }
                is DisabilityType.VisualImpairment -> {
                    viewModel.onSelectOption(optionIds, optionNames, DisabilityType.VisualImpairment)
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
        }
        addMaker()
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

    private fun addMaker() {
        this.repeatOnViewStarted {

            val marker = Marker()
            with(marker) {
                icon = OverlayImage.fromResource(R.drawable.maker_unselected_restaurant_icon)
                position = LatLng(
                    37.2792385,
                    127.0346949
                )
                map = naverMap
                width = 86
                height = 90

                setOnClickListener {
                    true
                }
            }
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
