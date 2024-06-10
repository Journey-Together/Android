package kr.tekit.lion.daongil.presentation.main.fragment

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import kr.tekit.lion.daongil.HighThemeApp
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentHomeMainBinding
import kr.tekit.lion.daongil.domain.model.AroundPlace
import kr.tekit.lion.daongil.domain.model.RecommendPlace
import kr.tekit.lion.daongil.presentation.home.DetailActivity
import kr.tekit.lion.daongil.presentation.main.adapter.HomeLocationRVAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.HomeRecommendRVAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.HomeVPAdapter
import kr.tekit.lion.daongil.presentation.main.dialog.ModeSettingDialog
import kr.tekit.lion.daongil.presentation.main.vm.HomeViewModel
import kr.tekit.lion.daongil.presentation.main.vm.HomeViewModelFactory
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

class HomeMainFragment : Fragment(R.layout.fragment_home_main), HomeRecommendRVAdapter.OnRecommendClickListener {
    private val app = HighThemeApp.getInstance()
    private val viewModel : HomeViewModel by viewModels { HomeViewModelFactory(requireContext()) }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            initLocationClient(FragmentHomeMainBinding.bind(requireView()))
        } else {
            Toast.makeText(requireContext(), "위치 권한 거부 시 근처 관광지 추천을 받을 수 없습니다", Toast.LENGTH_LONG).show()
            hideLocationRv(FragmentHomeMainBinding.bind(requireView()))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val theme = HighThemeApp.getInstance().getThemePrefs()

        when (theme) {
            "night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "basic" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeMainBinding.bind(view)

        if (app.isFirstLogin()) {
            settingDialog()
        }

        checkLocationPermission(binding)
        settingVPAdapter(binding)
        settingHighcontrastBtn(binding)
        getRecommendPlaceInfo(binding)
    }

    private fun settingVPAdapter(binding: FragmentHomeMainBinding) {
        val images = listOfNotNull(
            ContextCompat.getDrawable(requireContext(), R.drawable.item_home_vp1),
            ContextCompat.getDrawable(requireContext(), R.drawable.item_home_vp2),
            ContextCompat.getDrawable(requireContext(), R.drawable.item_home_vp3)
        )

        val homeVPAdapter = HomeVPAdapter(images)
        binding.homeVp.adapter = homeVPAdapter
        binding.homeVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homeVpIndicator.setViewPager(binding.homeVp)
        startAutoSlide(homeVPAdapter, binding)
    }

    private fun startAutoSlide(adpater : HomeVPAdapter, binding: FragmentHomeMainBinding) {
        val timer = Timer()
        val handler = Handler(Looper.getMainLooper())

        // 일정 간격으로 슬라이드 변경 (4초마다)
        timer.scheduleAtFixedRate(3000, 4000) {
            handler.post {
                val nextItem = binding.homeVp.currentItem + 1
                if (nextItem < adpater.itemCount) {
                    binding.homeVp.currentItem = nextItem
                } else {
                    binding.homeVp.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }

    private fun settingRecommendRVAdapter(binding: FragmentHomeMainBinding, recommendPlaceList: List<RecommendPlace>) {
        val homeRecommendRVAdapter = HomeRecommendRVAdapter(recommendPlaceList, this)
        binding.homeRecommendRv.adapter = homeRecommendRVAdapter
        binding.homeRecommendRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun settingLocationRVAdapter(binding: FragmentHomeMainBinding, aroundPlaceList: List<AroundPlace>) {
        val homeLocationRVAdapter = HomeLocationRVAdapter(aroundPlaceList)
        binding.homeMyLocationRv.adapter = homeLocationRVAdapter
        binding.homeMyLocationRv.layoutManager = LinearLayoutManager(context)
    }

    private fun settingDialog() {
        val dialog = ModeSettingDialog()
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!, "ModeSettingDialog")
    }

    private fun settingHighcontrastBtn(binding: FragmentHomeMainBinding) {
        binding.homeHighcontrastBtn.setOnClickListener {
            if (app.getThemePrefs() == "basic") {
                app.setThemePrefs("night")
                requireActivity().recreate()
            } else {
                app.setThemePrefs("basic")
                requireActivity().recreate()
            }
        }
    }

    override fun onRecommendClicked(recommendPlace: RecommendPlace) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("recommendPlaceId", recommendPlace.placeId)
        startActivity(intent)
    }

    private fun checkLocationPermission(binding: FragmentHomeMainBinding) {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            initLocationClient(binding)
        }
    }

    private fun initLocationClient(binding: FragmentHomeMainBinding) {
        // 위치 요청 설정
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3600000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(3600000)
            .setMaxUpdateDelayMillis(3600000)
            .build()

        // 위치 설정 요청 빌더 구성
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(requireContext())
        val task = client.checkLocationSettings(builder.build())

        // 위치 설정이 성공적으로 확인된 경우 위치 업데이트 시작
        task.addOnSuccessListener {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            startLocationUpdates(binding)
        }

        // 위치 설정 확인 실패 시 로그 기록
        task.addOnFailureListener {
            Log.d(TAG, "location client setting failure")
        }
    }

    private fun startLocationUpdates(binding: FragmentHomeMainBinding) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3600000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(3600000)
            .setMaxUpdateDelayMillis(3600000)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    // 위치 정보 사용
                    //Toast.makeText(requireContext(), "Lat: ${location.latitude}, Lng: ${location.longitude}", Toast.LENGTH_LONG).show()

                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val address = addresses?.get(0)?.getAddressLine(0)
                    //Toast.makeText(requireContext(), "사용자 주소 : $address", Toast.LENGTH_LONG).show()

                    binding.homeMyLocationTv.text = address

                    // 주소 분리
                    val (area, sigungu) = splitAddress(address!!)
                    // val areaCode = viewModel.getAreaCode(area).toString()
                    // val sigunguCode = viewModel

                    val areaCode = "1"
                    val sigunguCode = "1"

                    Log.e("areaCode", areaCode)

                    getAroundPlaceInfo(binding, areaCode, sigunguCode)
                }
            }
        }

        // 위치 권한이 부여되었는지 확인 후 위치 업데이트 요청
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun onPause() {
        super.onPause()
        // locationCallback이 초기화되었는지 확인 후 초기화 된 경우에만 removeLocationUpdates()를 호출
        if (::locationCallback.isInitialized) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private fun splitAddress(address: String): Pair<String, String> {
        val parts = address.split(" ")

        if (parts.size >= 2) {
            val city = parts[0]
            val district = parts[1]
            return Pair(city, district)
        } else {
            throw IllegalArgumentException("주소 형식이 잘못되었습니다")
        }
    }

    private fun hideLocationRv(binding: FragmentHomeMainBinding) {
        binding.homeMyLocationRv.visibility = View.GONE
        binding.homeMyLocationTv.text = "위치 권한을 허용해주세요"
    }

    private fun getAroundPlaceInfo(binding: FragmentHomeMainBinding, areaCode: String, sigunguCode: String) {
        viewModel.getPlaceMain(areaCode, sigunguCode)

        viewModel.aroundPlaceInfo.observe(requireActivity()) { aroundPlaceInfo ->
            if (aroundPlaceInfo.isNotEmpty()) {
                val aroundPlaceList = aroundPlaceInfo.map {
                    AroundPlace(it.address, it.disability, it.image, it.name, it.placeId)
                }
                settingLocationRVAdapter(binding , aroundPlaceList)
            }
        }
    }

    private fun getRecommendPlaceInfo(binding: FragmentHomeMainBinding) {
        viewModel.getPlaceMain("1", "1")

        viewModel.recommendPlaceInfo.observe(requireActivity()) {recommendPlaceInfo ->
            if (recommendPlaceInfo.isNotEmpty()) {
                val recommendPlaceList = recommendPlaceInfo.map {
                    RecommendPlace(it.address, it.disability, it.image, it.name, it.placeId)
                }
                settingRecommendRVAdapter(binding, recommendPlaceList)
            }
        }
    }
}