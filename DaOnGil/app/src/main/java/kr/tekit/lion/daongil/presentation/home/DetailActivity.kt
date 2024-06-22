package kr.tekit.lion.daongil.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import kr.tekit.lion.daongil.domain.model.Review
import kr.tekit.lion.daongil.domain.model.SubDisability
import kr.tekit.lion.daongil.presentation.emergency.EmergencyMapActivity
import kr.tekit.lion.daongil.presentation.ext.repeatOnStarted
import kr.tekit.lion.daongil.presentation.home.adapter.DetailDisabilityRVAdapter
import kr.tekit.lion.daongil.presentation.home.adapter.DetailInfoRVAdapter
import kr.tekit.lion.daongil.presentation.home.adapter.DetailReviewRVAdapter
import kr.tekit.lion.daongil.presentation.home.vm.DetailViewModel
import kr.tekit.lion.daongil.presentation.home.vm.DetailViewModelFactory
import kr.tekit.lion.daongil.presentation.login.LogInState

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel : DetailViewModel by viewModels { DetailViewModelFactory(this) }
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
        initMap()
    }

    private fun settingDetailInfoRVAdapter(detailInfo: List<SubDisability>) {
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

    private fun settingReviewBtn(placeId: Long) {
        binding.detailMoreReviewBtn.setOnClickListener {
            val intent = Intent(this, ReviewListActivity::class.java)
            intent.putExtra("reviewPlaceId", placeId)
            startActivity(intent)
        }

        binding.detailReviewBtn.setOnClickListener {
            val intent = Intent(this, WriteReviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleCommonDetailPlaceInfo(
        placeId: Long,
        reviewList: List<Review>?,
        disability: List<Int>,
        name: String,
        address: String,
        overview: String,
        image: String?,
        longitude: Double,
        latitude: Double,
        category: String,
        subDisability: List<SubDisability>?
    ) {
        reviewList?.let { settingReviewRVAdapter(it) }
        settingDisabilityRVAdapter(disability)
        if (subDisability != null) {
            settingDetailInfoRVAdapter(subDisability)
        }

        settingReviewBtn(placeId)

        binding.detailTitleTv.text = name
        binding.detailAddressTv.text = address
        binding.detailBasicContentTv.text = overview
        binding.detailToolbarTitleTv.text = category
        binding.detailRouteTv.text = category

        if (image != null) {
            Glide.with(binding.detailThumbnailIv.context)
                .load(image)
                .error(R.drawable.empty_view)
                .into(binding.detailThumbnailIv)
        }

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(longitude, latitude))
        naverMap.moveCamera(cameraUpdate)

        addMapMarker(longitude, latitude)
    }

    private fun getDetailPlaceInfo(placeId : Long) {
        viewModel.getDetailPlace(placeId)

        viewModel.detailPlaceInfo.observe(this@DetailActivity) {detailPlaceInfo ->
            handleCommonDetailPlaceInfo(
                detailPlaceInfo.placeId,
                detailPlaceInfo.reviewList,
                detailPlaceInfo.disability,
                detailPlaceInfo.name,
                detailPlaceInfo.address,
                detailPlaceInfo.overview,
                detailPlaceInfo.image,
                detailPlaceInfo.longitude.toDouble(),
                detailPlaceInfo.latitude.toDouble(),
                detailPlaceInfo.category,
                detailPlaceInfo.subDisability
            )

            updateBookmarkState(detailPlaceInfo.isMark)

            binding.detailBookmarkBtn.setOnClickListener {
                val newMarkState = !detailPlaceInfo.isMark

                detailPlaceInfo.isMark = newMarkState
                viewModel.updateDetailPlaceBookmark(placeId)
                updateBookmarkState(newMarkState)

                // 북마크 상태에 따라 bookmarkNum 업데이트
                if (newMarkState) {
                    detailPlaceInfo.bookmarkNum++
                } else {
                    detailPlaceInfo.bookmarkNum--
                }
                binding.detailBookmarkCount.text = detailPlaceInfo.bookmarkNum.toString()
            }

            binding.detailBookmarkCount.text = detailPlaceInfo.bookmarkNum.toString()
        }
    }

    private fun getDetailPlaceInfoGuest(placeId : Long) {
        viewModel.getDetailPlaceGuest(placeId)

        viewModel.detailPlaceInfoGuest.observe(this@DetailActivity) {detailPlaceInfoGuest ->
            handleCommonDetailPlaceInfo(
                detailPlaceInfoGuest.placeId,
                detailPlaceInfoGuest.reviewList,
                detailPlaceInfoGuest.disability,
                detailPlaceInfoGuest.name,
                detailPlaceInfoGuest.address,
                detailPlaceInfoGuest.overview,
                detailPlaceInfoGuest.image,
                detailPlaceInfoGuest.longitude.toDouble(),
                detailPlaceInfoGuest.latitude.toDouble(),
                detailPlaceInfoGuest.category,
                detailPlaceInfoGuest.subDisability
            )
            binding.detailBookmarkBtn.visibility = View.GONE
            binding.detailBookmarkCount.visibility = View.GONE
        }
    }

    private fun updateBookmarkState(isMark: Boolean) {
        val bookmarkDrawable = if (isMark) {
            R.drawable.bookmark_fill_icon
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

        repeatOnStarted {
            viewModel.loginState.collect { uiState ->
                when (uiState) {
                    is LogInState.Checking -> {
                        return@collect
                    }
                    is LogInState.LoggedIn -> {
                        getDetailPlaceInfo(recommendPlaceId.toLong())
                    }
                    is LogInState.LoginRequired -> {
                        getDetailPlaceInfoGuest(recommendPlaceId.toLong())
                    }
                }
            }
        }
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