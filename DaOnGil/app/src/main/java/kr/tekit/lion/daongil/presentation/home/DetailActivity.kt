package kr.tekit.lion.daongil.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityDetailBinding
import kr.tekit.lion.daongil.domain.model.DetailInfo
import kr.tekit.lion.daongil.domain.model.Review
import kr.tekit.lion.daongil.presentation.emergency.EmergencyMapActivity
import kr.tekit.lion.daongil.presentation.home.adapter.DetailDisabilityRVAdapter
import kr.tekit.lion.daongil.presentation.home.adapter.DetailInfoRVAdapter
import kr.tekit.lion.daongil.presentation.home.adapter.DetailReviewRVAdapter
import kr.tekit.lion.daongil.presentation.home.vm.DetailViewModel
import kr.tekit.lion.daongil.presentation.home.vm.DetailViewModelFactory

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel : DetailViewModel by viewModels { DetailViewModelFactory() }
    private val binding : ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private lateinit var naverMap: NaverMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingToolbar()
        settingReviewBtn()
        initMap()
        settingDetailInfoRVAdapter()
    }

    private fun settingDetailInfoRVAdapter() {
        val detailInfo = listOf(
            DetailInfo("주차 여부", "장애인 주차장 있음 (관광 안내소 앞)"),
            DetailInfo("핵심 동선", "출입구까지 경사로가 설치되어 있음 (완만함)")
        )
        val detailInfoRVAdapter = DetailInfoRVAdapter(detailInfo)
        binding.detailDisabilityInfoRv.adapter = detailInfoRVAdapter
        binding.detailDisabilityInfoRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun settingReviewRVAdapter(reviewList : List<Review>) {
        val detailReviewRVAdapter = DetailReviewRVAdapter(reviewList)
        binding.detailReviewRv.adapter = detailReviewRVAdapter
        binding.detailReviewRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun settingDisabilityRVAdapter(disabilityList : List<Int>) {
        val disabilityRVAdapter = DetailDisabilityRVAdapter(disabilityList)
        binding.detailDisabilityIvRv.adapter = disabilityRVAdapter
        binding.detailDisabilityIvRv.layoutManager = GridLayoutManager(applicationContext, 3)
    }

    private fun settingToolbar() {
        binding.detailToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingReviewBtn() {
        binding.detailMoreReviewBtn.setOnClickListener {
            val intent = Intent(this, ReviewListActivity::class.java)
            startActivity(intent)
        }

        binding.detailReviewBtn.setOnClickListener {
            val intent = Intent(this, WriteReviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDetailPlaceInfo(placeId : Long) {
        viewModel.getDetailPlace(placeId)

        viewModel.detailPlaceInfo.observe(this@DetailActivity) {detailPlaceInfo ->
            detailPlaceInfo.reviewList?.let { settingReviewRVAdapter(it) }
            // settingDetailInfoRVAdapter(detailPlaceInfo.subDisability)
            settingDisabilityRVAdapter(detailPlaceInfo.disability)

            //binding.detailToolbarTitleTv.text = detailPlaceInfo.category
            binding.detailTitleTv.text = detailPlaceInfo.name
            binding.detailAddressTv.text = detailPlaceInfo.address
            binding.detailBasicContentTv.text = detailPlaceInfo.overview
            //binding.detailRouteTv.text = detailPlaceInfo.category

            if (detailPlaceInfo.image != null) {
                Glide.with(binding.detailThumbnailIv.context)
                    .load(detailPlaceInfo.image)
                    .error(R.drawable.empty_view_small)
                    .into(binding.detailThumbnailIv)
            }

            updateBookmarkState(detailPlaceInfo.isMark)
            binding.detailBookmarkBtn.setOnClickListener {
                val newMarkState = !detailPlaceInfo.isMark
                updateBookmarkState(newMarkState)
            }

            val cameraUpdate = CameraUpdate.scrollTo(LatLng(detailPlaceInfo.longitude.toDouble(), detailPlaceInfo.latitude.toDouble()))
            naverMap.moveCamera(cameraUpdate)

            addMapMarker(detailPlaceInfo.longitude.toDouble(), detailPlaceInfo.latitude.toDouble())
        }
    }

    private fun updateBookmarkState(isMark: Boolean) {
        val bookmarkDrawable = if (isMark) {
            R.drawable.bookmark_filled_icon
        } else {
            R.drawable.bookmark_icon
        }
        binding.detailBookmarkBtn.setImageResource(bookmarkDrawable)
    }

    private fun initMap() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // 네이버 지도 SDK에 위치를 제공하는 인터페이스
        mLocationSource = FusedLocationSource(this, EmergencyMapActivity.LOCATION_PERMISSION_REQUEST_CODE)
        // 네이버맵 동적으로 불러오기
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.detail_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.detail_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.minZoom = 10.0
        naverMap.maxZoom = 15.0

        val recommendPlaceId = intent.getIntExtra("detailPlaceId", -1)
        getDetailPlaceInfo(recommendPlaceId.toLong())
    }

    private fun addMapMarker(mapX: Double, mapY: Double) {
        val marker = Marker()
        with(marker) {
            icon = OverlayImage.fromResource(R.drawable.maker_selected_lodging_icon)
            position = LatLng(mapX, mapY)
            zIndex = 0
            map = naverMap
            width = 86
            height = 90
        }
    }
}