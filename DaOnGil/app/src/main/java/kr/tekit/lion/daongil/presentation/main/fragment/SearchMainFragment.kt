package kr.tekit.lion.daongil.presentation.main.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentSearchMainBinding
import kr.tekit.lion.daongil.presentation.ext.showPermissionSnackBar
import kr.tekit.lion.daongil.presentation.main.Category
import kr.tekit.lion.daongil.presentation.main.CategoryBottomSheet

class SearchMainFragment : Fragment(R.layout.fragment_search_main), OnMapReadyCallback {

    private lateinit var launcherForPermission: ActivityResultLauncher<Array<String>>
    // 내장 위치 추적 기능 사용
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchMainBinding.bind(view)
        var uiState = Mode.CATEGORY.mode

        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                progressBar.setProgressCompat(60, true)
                delay(700)
                progressBar.setProgressCompat(100, true)
                delay(1200)
                progressBar.visibility = View.INVISIBLE
            }

            modeSwitchBtn.setOnClickListener {
                when(uiState){
                    Mode.CATEGORY.mode ->{
                        uiState = Mode.MAP.mode
                        categoryContainer.visibility = View.GONE
                        mapContainer.visibility = View.VISIBLE
                        modeSwitchBtn.setText(R.string.watching_list)
                        modeSwitchBtn.setIconResource(R.drawable.list_icon)
                    }
                    Mode.MAP.mode -> {
                        uiState = Mode.CATEGORY.mode
                        categoryContainer.visibility = View.VISIBLE
                        mapContainer.visibility = View.GONE
                        modeSwitchBtn.setText(R.string.watching_map)
                        modeSwitchBtn.setIconResource(R.drawable.map_icon)
                    }
                }
            }

            btnReset.setOnClickListener {

            }

            chipPhysicalDisability.setOnClickListener {
                showBottomSheet(Category.PHYSICAL_DISABILITY.type)
            }

            chipVisualImpairment.setOnClickListener {
                showBottomSheet(Category.VISUAL_IMPAIRMENT.type)
            }

            chipHearingImpairment.setOnClickListener {
                showBottomSheet(Category.HEARING_IMPAIRMENT.type)
            }

            chipInfantFamilly.setOnClickListener {
                showBottomSheet(Category.INFANT_FAMILY.type)
            }

            chipElderlyPeople.setOnClickListener {
                showBottomSheet(Category.PHYSICAL_DISABILITY.type)
            }

            btnReset.setOnClickListener {
                showBottomSheet(Category.CLEAR.type)
            }
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
    private fun showBottomSheet(category: String) {
        CategoryBottomSheet(category){
            // ViewModel에 저장하기~

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
        //addMaker()
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
                val color = androidx.core.content.ContextCompat.getColor(
                    requireContext(),
                    R.color.maker_overlay
                )
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

    //    private fun addMaker() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.allStoreData.value?.forEach { store ->
//                    val marker = Marker()
//                    with(marker){
//                        icon = OverlayImage.fromResource(R.drawable.ic_marker)
//                        position = LatLng(
//                            store.storeInfo.latitude.toDouble(),
//                            store.storeInfo.longitude.toDouble()
//                        )
//                        map = naverMap
//                        width = 86
//                        height = 90
//
//                        setOnClickListener {
//                            true
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun isNightMode(): Boolean {
        val currentNightMode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun setMapType(naverMap: NaverMap){
        if (isNightMode()) {
            // 야간 모드 허용
            naverMap.mapType = NaverMap.MapType.Navi
            naverMap.isNightModeEnabled = true
        }else{
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
}

enum class Mode(val mode: String){
    CATEGORY("category"),
    MAP("map")
}