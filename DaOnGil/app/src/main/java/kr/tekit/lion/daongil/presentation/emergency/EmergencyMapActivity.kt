package kr.tekit.lion.daongil.presentation.emergency

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityEmergencyMapBinding
import kr.tekit.lion.daongil.domain.model.EmergencyBottom
import kr.tekit.lion.daongil.domain.model.EmergencyMapInfo
import kr.tekit.lion.daongil.presentation.emergency.fragment.EmergencyAreaDialog
import kr.tekit.lion.daongil.presentation.emergency.fragment.EmergencyBottomSheet
import kr.tekit.lion.daongil.presentation.emergency.vm.EmergencyMapViewModel
import kr.tekit.lion.daongil.presentation.emergency.vm.EmergencyMapViewModelFactory
import kr.tekit.lion.daongil.presentation.ext.showPermissionSnackBar

class EmergencyMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding: ActivityEmergencyMapBinding by lazy {
        ActivityEmergencyMapBinding.inflate(layoutInflater)
    }

    private val viewModel: EmergencyMapViewModel by viewModels{ EmergencyMapViewModelFactory() }

    private lateinit var launcherForPermission: ActivityResultLauncher<Array<String>>

    // 내장 위치 추적 기능 사용
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(binding.emergencyBottomSheet.emergencyBottomSheetLayout)
    }

    private var emergencySelectedMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mapType = intent.getStringExtra("mapType").toString()
        setToolbar(mapType)

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
                                showPermissionSnackBar(binding.root)
                            }
                        }

                        else -> {
                            permissionDeniedMapUiSetting()
                            showPermissionSnackBar(binding.root)
                        }
                    }
                }

            }
        }

        initMap()

        initBottomSheet()
        settingDialog()
        setAreaButton()
    }

    private fun setAreaButton(){
        viewModel.combinedArea.observe(this@EmergencyMapActivity) { combinedArea ->
            binding.emergencyMapArea.text = combinedArea
        }
    }

    private fun settingDialog() {
        val dialog = EmergencyAreaDialog()

        binding.emergencyMapAreaButton.setOnClickListener {
            dialog.show(this.supportFragmentManager, "EmergencyAreaDialog")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    private fun setBottomRecylcerView(emergencyBottomList: List<EmergencyBottom>){
        val emergencyBottomSheet = EmergencyBottomSheet(binding.emergencyBottomSheet, emergencyBottomList)
        emergencyBottomSheet.setRecyclerView()
    }

    private fun initBottomSheet() {

        val observer = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val metrics = resources.displayMetrics
                val dp = 40f
                val px = (dp * (metrics.densityDpi / 160f)).toInt()

                val buttonHeight = binding.emergencyMapAreaButton.height
                val toolbarHeight = binding.toolbarEmergencyMap.height
                val deviceHeight = metrics.heightPixels - buttonHeight - toolbarHeight - px

                if (deviceHeight > 0) {
                    bottomSheetBehavior.maxHeight = deviceHeight
                    Log.d("device", "Calculated deviceHeight: $deviceHeight")
                } else {
                    Log.e("device", "Failed to calculate deviceHeight")
                }

                // 레이아웃 변경을 추적하는 리스너는 더 이상 필요 없으므로 제거
                binding.emergencyMapAreaButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }

        binding.emergencyMapAreaButton.viewTreeObserver.addOnGlobalLayoutListener(observer)
    }

    private fun initMap() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // LocationSource 구현체를 지정
        // 네이버 지도 SDK에 위치를 제공하는 인터페이스
        mLocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        // 네이버맵 동적으로 불러오기
        val fm = supportFragmentManager
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

        naverMap.minZoom = 10.0
        naverMap.maxZoom = 15.0

        naverMap.setOnMapClickListener { _, _ ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            val emergencyList = mutableListOf<EmergencyBottom>()

            viewModel.emergencyMapInfo.value?.map {
                emergencyList.add(EmergencyBottom(it, "emergency", it.hospitalId.toString()))
            }

            setBottomRecylcerView(emergencyList.toList())

            // 선택된 마커가 있는 경우 unselected 상태로 변경
            emergencySelectedMarker?.let { marker ->
                marker.icon = OverlayImage.fromResource(R.drawable.marker_unselected_emergency_icon)
                marker.isHideCollidedMarkers = true
                marker.isForceShowIcon = false
                marker.width = 56
                marker.height = 60
                emergencySelectedMarker = null
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcherForPermission.launch(REQUEST_LOCATION_PERMISSIONS)
        } else {
            permissionGrantedMapUiSetting()
        }
    }
    private fun permissionGrantedMapUiSetting() {
        with(naverMap) {
            setMapType(this)

            // 내장 위치 추적 기능 사용
            locationSource = mLocationSource

            naverMap.addOnOptionChangeListener {
                val mode = naverMap.locationTrackingMode.name

                when (mode) {
                    "None" -> locationTrackingMode = LocationTrackingMode.Follow
                    "Follow", "NoFollow" -> {
                        val mapType = intent.getStringExtra("mapType").toString()
                        if(mapType.equals("Emergency")){
                            getEmergencyMapInfo()
                            setEmergencyLocation()
                        }
                    }
                }
            }

            with(naverMap.locationOverlay) {
                val color = getColor(R.color.maker_overlay)
                circleRadius = 150
                // setAlphaComponent : 투명도 지정
                // 0(완전 투명) ~ 255(완전 불투명)
                circleColor = androidx.core.graphics.ColorUtils.setAlphaComponent(color, 90)
            }

            /**
             * 사용자의 위치를 지도에서 추적하는 모드
             *
             * Follow : 위치를 추적하면서 카메라도 따라 움직이는 모드.
             * LocationOverlay와 카메라의 좌표가 사용자의 위치를 따라 움직입니다.
             * API나 제스처를 사용해 지도를 임의로 움직일 경우 모드가 NoFollow로 바뀝니다.
             *
             * NoFollow : 위치는 추적하지만 지도는 움직이지 않는 모드.
             * LocationOverlay가 사용자의 위치를 따라 움직이나 지도는 움직이지 않습니다.
             *
             * Face : 위치를 추적하면서 카메라의 좌표와 베어링도 따라 움직이는 모드.
             * LocationOverlay와 카메라의 좌표,
             * 베어링이 사용자의 위치, 사용자가 바라보고 있는 방향을 따라 움직입니다.
             * API나 제스처를 사용해 지도를 임의로 움직일 경우 모드가 NoFollow로 바뀝니다.
             *
             * */
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

    private fun setEmergencyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@EmergencyMapActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@EmergencyMapActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcherForPermission.launch(REQUEST_LOCATION_PERMISSIONS)
        } else {

            // 사용자 현재 위치 받아오기
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                val latitude = location?.latitude ?: 35.1798159
                val longitude = location?.longitude ?: 129.0750222

                val coords = "$longitude" + "," + "$latitude"

                viewModel.getuserLocationRegion(coords)

                viewModel.emergencyMapInfo.observe(this@EmergencyMapActivity) { emergencyMapInfo ->

                    lifecycleScope.launch {
                        with(binding){
                            progressBar.setProgressCompat(20, false)
                            delay(700)
                            progressBar.setProgressCompat(100, true)
                        }
                    }

                    if (emergencyMapInfo.isNotEmpty()) {

                        val emergencyList = mutableListOf<EmergencyBottom>()
                        emergencyMapInfo.map {
                            addEmergencyMarker(it, latitude, longitude)
                            emergencyList.add(EmergencyBottom(it, "emergency", it.hospitalId.toString()))
                        }

                        setBottomRecylcerView(emergencyList.toList())

                        val boundsBuilder = LatLngBounds.Builder()

                        // 모든 병원 위치 추가
                        emergencyMapInfo.forEach {
                            boundsBuilder.include(LatLng(it.hospitalLat ?: latitude, it.hospitalLon ?: longitude))
                        }


                        // LatLngBounds 객체 생성
                        val bounds = boundsBuilder.build()


                        // 카메라 업데이트
                        val cameraUpdate = CameraUpdate.fitBounds(bounds, 20) // 패딩 값으로 100을 사용

                        naverMap.moveCamera(cameraUpdate)

                        // 위치 오버레이 설정
                        with(naverMap.locationOverlay) {
                            isVisible = true
                            position = LatLng(latitude, longitude)
                        }
                    }
                }

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

    private fun getEmergencyMapInfo(){

        viewModel.combinedArea.observe(this@EmergencyMapActivity) { combinedArea ->
            val parts = combinedArea.split(" ")
            if (parts.size >= 2) {
                val (STAGE1, STAGE2) = parts
                viewModel.getEmergencyMapInfo(STAGE1, STAGE2)
            } else {
                viewModel.getEmergencyMapInfo(combinedArea, null)
            }
        }
    }

    private fun addEmergencyMarker(
        emergencyMapInfo: EmergencyMapInfo,
        latitude: Double,
        longitude: Double
    ) {
        val marker = Marker().apply {
            icon = OverlayImage.fromResource(R.drawable.marker_unselected_emergency_icon)
            position = LatLng(
                emergencyMapInfo.hospitalLat ?: latitude,
                emergencyMapInfo.hospitalLon ?: longitude
            )
            map = naverMap
            width = 56
            height = 60

            setOnClickListener {
                // 이전에 선택된 마커가 있다면 초기 상태로 되돌림
                emergencySelectedMarker?.let { previousMarker ->
                    previousMarker.icon = OverlayImage.fromResource(R.drawable.marker_unselected_emergency_icon)
                    previousMarker.isHideCollidedMarkers = true
                    previousMarker.isForceShowIcon = false
                    previousMarker.width = 56
                    previousMarker.height = 60
                }

                // 현재 선택된 마커 업데이트
                emergencySelectedMarker = this

                // 클릭된 마커를 선택 상태로 변경
                this.icon = OverlayImage.fromResource(R.drawable.marker_selected_emergency_icon)
                this.isHideCollidedMarkers = true
                this.isForceShowIcon = true
                this.width = 100
                this.height = 130

                setBottomRecylcerView(
                    listOf(
                        EmergencyBottom(
                            emergencyMapInfo,
                            "emergency",
                            emergencyMapInfo.hospitalId.toString()
                        )
                    )
                )
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                true
            }
        }

        marker.map = naverMap
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


    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 100
        val REQUEST_LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }

    private fun setToolbar(type: String) {
        with(binding) {

            toolbarEmergencyMap.setNavigationOnClickListener {
                finish()
            }

            if (type.equals("Emergency")) {
                titleEmergencyMap.setText(R.string.text_emergency_aed)
            }

            if (type.equals("Pharmacy")) {
                titleEmergencyMap.setText(R.string.text_emergency_pharmacy)
            }
        }
    }

}

