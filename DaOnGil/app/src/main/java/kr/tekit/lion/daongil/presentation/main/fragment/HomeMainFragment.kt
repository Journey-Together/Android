package kr.tekit.lion.daongil.presentation.main.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.HighThemeApp
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentHomeMainBinding
import kr.tekit.lion.daongil.databinding.ItemTouristRecommendBinding
import kr.tekit.lion.daongil.domain.model.AroundPlace
import kr.tekit.lion.daongil.domain.model.RecommendPlace
import kr.tekit.lion.daongil.presentation.ext.showPermissionSnackBar
import kr.tekit.lion.daongil.presentation.home.DetailActivity
import kr.tekit.lion.daongil.presentation.main.ItemDeco.ItemOffsetDecoration
import kr.tekit.lion.daongil.presentation.main.adapter.HomeLocationRVAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.HomeRecommendRVAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.HomeVPAdapter
import kr.tekit.lion.daongil.presentation.main.customview.CustomPageIndicator
import kr.tekit.lion.daongil.presentation.main.dialog.ModeSettingDialog
import kr.tekit.lion.daongil.presentation.main.vm.HomeViewModel
import kr.tekit.lion.daongil.presentation.main.vm.HomeViewModelFactory
import java.io.IOException
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.math.abs

class HomeMainFragment : Fragment(R.layout.fragment_home_main) {
    private val app = HighThemeApp.getInstance()
    private val viewModel: HomeViewModel by viewModels { HomeViewModelFactory(requireContext()) }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val retryDelayMillis = 5000L

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                initLocationClient(FragmentHomeMainBinding.bind(requireView()))
            } else {
                requireContext().showPermissionSnackBar(FragmentHomeMainBinding.bind(requireView()).root)
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

    private fun startAutoSlide(adpater: HomeVPAdapter, binding: FragmentHomeMainBinding) {
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

    private fun settingRecommendRVAdapter(
        binding: FragmentHomeMainBinding,
        recommendPlaceList: List<RecommendPlace>
    ) {
        val homeRecommendRVAdapter = HomeRecommendRVAdapter(recommendPlaceList,
            onClick = { position ->
                val context: Context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("detailPlaceId", recommendPlaceList[position].placeId)
                startActivity(intent)
            })
        binding.homeRecommendRv.adapter = homeRecommendRVAdapter
        binding.homeRecommendRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val itemCount = recommendPlaceList.size
        val customPageIndicator = CustomPageIndicator(requireActivity(), binding.homeRecommendRvIndicator, itemCount)

        // 스냅 효과를 적용
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.homeRecommendRv)

        binding.homeRecommendRv.addItemDecoration(ItemOffsetDecoration(70, 70))

        // 스크롤에 따라 아이템의 크기와 알파를 조정
        binding.homeRecommendRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val centerX = recyclerView.width / 2

                // 스냅된 뷰를 찾기
                val snapView = snapHelper.findSnapView(recyclerView.layoutManager)
                val position = recyclerView.getChildAdapterPosition(snapView ?: return)

                // 인디케이터 업데이트
                customPageIndicator.onPageSelected(position)

                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i)
                    val itemBinding = ItemTouristRecommendBinding.bind(child)
                    val childCenterX = (child.left + child.right) / 2
                    val distanceFromCenter = abs(centerX - childCenterX)
                    val scaleFactor = 1 - (distanceFromCenter.toFloat() / centerX)

                    // 아이템의 크기 및 알파값 조정
                    child.scaleX = 1.0f + 0.2f * scaleFactor
                    child.scaleY = 1.0f + 0.2f * scaleFactor

                    // Z축 이동량 조정 - 겹쳐 보이도록 음수 값으로 조정
                    child.translationZ = (-distanceFromCenter / centerX).toFloat()

                    // 좌우 이동 조정 - 중앙에 가까운 아이템이 앞으로 나오는 효과
                    val translationXFactor = 0.25f * distanceFromCenter / centerX
                    child.translationX = (if (childCenterX < centerX) translationXFactor else -translationXFactor) * child.width

                    // 어두운 오버레이 투명도 조정
                    if (distanceFromCenter < recyclerView.width / 2) {
                        itemBinding.touristRecommendDark.visibility = View.GONE
                    } else {
                        itemBinding.touristRecommendDark.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun settingLocationRVAdapter(
        binding: FragmentHomeMainBinding,
        aroundPlaceList: List<AroundPlace>
    ) {
        val homeLocationRVAdapter = HomeLocationRVAdapter(aroundPlaceList,
            onClick = {position ->
                val context: Context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("detailPlaceId", aroundPlaceList[position].placeId)
                startActivity(intent)
            })
        binding.homeMyLocationRv.adapter = homeLocationRVAdapter
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
                startActivity(Intent.makeRestartActivityTask(activity?.intent?.component))
            } else {
                app.setThemePrefs("basic")
                startActivity(Intent.makeRestartActivityTask(activity?.intent?.component))
            }
        }
    }

    private fun checkLocationPermission(binding: FragmentHomeMainBinding) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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
            if (isAdded) {
                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                startLocationUpdates(binding)
            }
        }.addOnFailureListener { // 위치 설정 확인 실패 시 로그 기록
            Log.d(TAG, "location client setting failure")
            retryLocationPermissionCheck(binding)
        }
    }

    private fun retryLocationPermissionCheck(binding: FragmentHomeMainBinding) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            delay(retryDelayMillis) // 5초 지연
            initLocationClient(binding)
        }
    }

    private fun startLocationUpdates(binding: FragmentHomeMainBinding) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3600000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(3600000)
            .setMaxUpdateDelayMillis(3600000)
            .build()

        locationCallback = object : LocationCallback() {
            @SuppressLint("SetTextI18n")
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())

                    for(i in 1..3) {
                        try {
                            val addresses =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            val address = addresses?.get(0)?.getAddressLine(0)

                            val (area, sigungu) = splitAddress(address!!)
                            binding.homeMyLocationTv.text = "$area $sigungu"

                            getAroundPlaceInfo(binding, area, sigungu)
                            return
                        } catch (e: IOException) {
                            if (i == 4) {
                                Log.e("HomeMainFragment", "Geocoder 위치 가져오기 실패", e)
                            }
                        }
                        // 재시도 전 대기 시간
                        Thread.sleep(1000)
                    }
                }
            }
        }

        // 위치 권한이 부여되었는지 확인 후 위치 업데이트 요청
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
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
            val city = parts[1]
            val district = parts[2]
            return Pair(city, district)
        } else {
            throw IllegalArgumentException("주소 형식이 잘못되었습니다")
        }
    }

    private fun hideLocationRv(binding: FragmentHomeMainBinding) {
        binding.homeMyLocationRv.visibility = View.GONE
        binding.homeMyLocationTv.text = "위치 권한을 허용해주세요"
    }

    private fun getAroundPlaceInfo(
        binding: FragmentHomeMainBinding,
        areaCode: String,
        sigunguCode: String
    ) {
        viewModel.getPlaceMain(areaCode, sigunguCode)

        viewModel.aroundPlaceInfo.observe(requireActivity()) { aroundPlaceInfo ->
            if (aroundPlaceInfo.isNotEmpty()) {
                val aroundPlaceList = aroundPlaceInfo.map {
                    AroundPlace(it.address, it.disability, it.image, it.name, it.placeId)
                }
                settingLocationRVAdapter(binding, aroundPlaceList)
            }
        }
    }

    private fun getRecommendPlaceInfo(binding: FragmentHomeMainBinding) {
        viewModel.getPlaceMain("1", "1")

        viewModel.recommendPlaceInfo.observe(requireActivity()) { recommendPlaceInfo ->
            if (recommendPlaceInfo.isNotEmpty()) {
                val recommendPlaceList = recommendPlaceInfo.map {
                    RecommendPlace(it.address, it.disability, it.image, it.name, it.placeId)
                }
                settingRecommendRVAdapter(binding, recommendPlaceList)
            }
        }
    }
}