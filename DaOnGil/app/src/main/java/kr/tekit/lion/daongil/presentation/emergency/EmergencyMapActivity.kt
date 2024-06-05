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
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
import kr.tekit.lion.daongil.databinding.ActivityEmergencyMapBinding
import kr.tekit.lion.daongil.domain.model.EmergencyBottom
import kr.tekit.lion.daongil.presentation.emergency.fragment.EmergencyBottomSheet
import kr.tekit.lion.daongil.presentation.ext.showPermissionSnackBar

class EmergencyMapActivity : AppCompatActivity(), OnMapReadyCallback {

    // 더미 data
    val emergencyBottomList: List<EmergencyBottom> = listOf(
        EmergencyBottom(null, "서울아산병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test1" ),
        EmergencyBottom(null, "연세세브란스","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test2" ),
        EmergencyBottom(null, "성모병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test3" ),
        EmergencyBottom(null, "서울대병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test4" ),
        EmergencyBottom(null, "서울아산병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test1" ),
        EmergencyBottom(null, "연세세브란스","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test2" ),
        EmergencyBottom(null, "성모병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test3" ),
        EmergencyBottom(null, "서울대병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test4" ),
        EmergencyBottom(null, "서울아산병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test1" ),
        EmergencyBottom(null, "연세세브란스","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test2" ),
        EmergencyBottom(null, "성모병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test3" ),
        EmergencyBottom(null, "서울대병원","2.1km", "서울 송파구 올림픽로43길 88 서울아산병원", "1588-5647","응급실 병상 21 / 25", "emergency", "test4" )
    )

    private val binding: ActivityEmergencyMapBinding by lazy {
        ActivityEmergencyMapBinding.inflate(layoutInflater)
    }

    private lateinit var launcherForPermission: ActivityResultLauncher<Array<String>>

    // 내장 위치 추적 기능 사용
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(binding.emergencyBottomSheet.emergencyBottomSheetLayout)
    }

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
        actionBottomSheet()
        setBottomRecylcerView(emergencyBottomList)
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

    private fun actionBottomSheet() {

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

                // BottomSheetBehavior state에 따른 이벤트
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.d("MainActivity", "state: hidden")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.d("MainActivity", "state: expanded")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.d("MainActivity", "state: collapsed")
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.d("MainActivity", "state: dragging")
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        Log.d("MainActivity", "state: settling")
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        Log.d("MainActivity", "state: half expanded")
                    }
                }

            }



            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })

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

        naverMap.setOnMapClickListener { pointF, latLng ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            setBottomRecylcerView(emergencyBottomList)
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
        val mapType = intent.getStringExtra("mapType").toString()
        addMaker(mapType)
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
                val color = getColor(R.color.maker_overlay)
                circleRadius = 200
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

    private var selectedMarker: Marker? = null

    private fun addMaker(type: String) {

        val marker = Marker()
        with(marker) {
            if (type == "Emergency") {
                icon = OverlayImage.fromResource(R.drawable.marker_unselected_emergency_icon)
                position = LatLng(36.6673002, 127.5010533)
                map = naverMap
                width = 56
                height = 60

                setOnClickListener {
                    // If there is a previously selected marker, restore its icon
                    selectedMarker?.let {
                        it.icon =
                            OverlayImage.fromResource(R.drawable.marker_unselected_emergency_icon)
                        it.width = 56
                        it.height = 60
                    }
                    // Set the current marker as the selected marker and change its icon
                    selectedMarker = this
                    icon = OverlayImage.fromResource(R.drawable.marker_selected_emergency_icon)
                    width = 100
                    height = 130
                    setBottomRecylcerView(
                        listOf(
                            EmergencyBottom(
                                null,
                                "서울아산병원",
                                "2.1km",
                                "서울 송파구 올림픽로43길 88 서울아산병원",
                                "1588-5647",
                                "응급실 병상 21 / 25",
                                "emergency",
                                "test emergency"
                            )
                        )
                    )
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    true
                }
            }

            if (type == "Pharmacy") {
                icon = OverlayImage.fromResource(R.drawable.marker_unselected_pharmacy_icon)
                position = LatLng(36.6673002, 127.5010533)
                map = naverMap
                width = 56
                height = 60

                setOnClickListener {
                    // If there is a previously selected marker, restore its icon
                    selectedMarker?.let {
                        it.icon =
                            OverlayImage.fromResource(R.drawable.marker_unselected_pharmacy_icon)
                        it.width = 56
                        it.height = 60
                    }
                    // Set the current marker as the selected marker and change its icon
                    selectedMarker = this
                    icon = OverlayImage.fromResource(R.drawable.marker_selected_pharmacy_icon)
                    width = 100
                    height = 130
                    setBottomRecylcerView(
                        listOf(
                            EmergencyBottom(
                                null,
                                "서울아산병원약국",
                                "2.1km",
                                "서울 송파구 올림픽로43길 88 서울아산병원",
                                "1588-5647",
                                "응급실 병상 21 / 25",
                                "emergency",
                                "test pharmacy"
                            )
                        )
                    )
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
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

