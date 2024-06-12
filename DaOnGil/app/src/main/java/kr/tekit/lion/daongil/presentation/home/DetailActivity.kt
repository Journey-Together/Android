package kr.tekit.lion.daongil.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kr.tekit.lion.daongil.domain.model.DetailInfo
import kr.tekit.lion.daongil.domain.model.Review
import kr.tekit.lion.daongil.presentation.emergency.EmergencyMapActivity
import kr.tekit.lion.daongil.presentation.ext.repeatOnStarted
import kr.tekit.lion.daongil.presentation.home.adapter.DetailDisabilityRVAdapter
import kr.tekit.lion.daongil.presentation.home.adapter.DetailInfoRVAdapter
import kr.tekit.lion.daongil.presentation.home.adapter.DetailReviewRVAdapter
import kr.tekit.lion.daongil.presentation.home.vm.DetailViewModel
import kr.tekit.lion.daongil.presentation.home.vm.DetailViewModelFactory
import kr.tekit.lion.daongil.presentation.login.LogInState
import java.time.LocalDateTime

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(this) }
    private val binding: ActivityDetailBinding by lazy {
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
        settingDetailInfoRVAdapter()

        val reviewList = listOf(
            Review(
                1L,
                "재롱맘",
                "https://i.namu.wiki/i/sLpl_9SaPt63LS9uKn7ptjw1GtopAOeL-fVSbFHsfwm2ZKwywO-4rd91q_MPds0-pXHkGqRyAj6u366J2-SygA.webp",
                "다양한 편의시설 덕분에 편하게 여행할 수 있었어요! 가족 중에 몸이 불편한 분이 계셔서 휠체어가 필요했는데 휠체어 대여도 해줘서 넘 좋았습니다 굿굿 주위에 식당도 많고 바람 쐬러 가기 좋아요 주말에 좋은 시간 보내고 왔습니다~",
                "https://a.cdn-hotels.com/gdcs/production3/d844/2981861a-8dc3-44c1-a38d-4f8257914a02.jpg?impolicy=fcrop&w=800&h=533&q=medium",
                4.5f,
                LocalDateTime.of(2024, 6, 11, 14, 45)
            ),
            Review(
                1L,
                "락스타",
                "https://pbs.twimg.com/media/F8oSaScbwAAWB45.png",
                "평소에도 자주 가는 곳인데 무장애 편의시설이 다양하고 배려가 많은 곳이라 편하게 쉬다 오기 좋은 것 같아요 주말에 놀러갈 곳을 찾고 계신다면 이 곳을 추천 드려요!!",
                "https://cdn.3hoursahead.com/v2/content/image-comp/f159e0e5-0570-4c3f-b963-234339ada50f.webp",
                5.0f,
                LocalDateTime.of(2024, 6, 11, 14, 45)
            ),
        )
        settingReviewRVAdapter(reviewList)
    }

    private fun settingDetailInfoRVAdapter() {
        val detailInfo = listOf(
            DetailInfo("주차 여부", "장애인 주차장 있음 (관광 안내소 앞)"),
            DetailInfo("핵심 동선", "출입구까지 경사로가 설치되어 있음 (완만함)"),
            DetailInfo("홍보물", "다온길 리플렛 있음"),
            DetailInfo("화장실", "장애인 화장실 및 샤워실 있음"),
            DetailInfo("휠체어대여","대여 가능 (2대/안내데스크/신분증보관)"),
            DetailInfo("점자블록", "점자블록 있음 (주요시설 앞)_시각장애인 편의시설"),
            DetailInfo("안내요원", "안내요원 있음 (입구 사무실)")
        )
        val detailInfoRVAdapter = DetailInfoRVAdapter(detailInfo)
        binding.detailDisabilityInfoRv.adapter = detailInfoRVAdapter
        binding.detailDisabilityInfoRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun settingReviewRVAdapter(reviewList: List<Review>) {
        val detailReviewRVAdapter = DetailReviewRVAdapter(reviewList)
        binding.detailReviewRv.adapter = detailReviewRVAdapter
        binding.detailReviewRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun settingDisabilityRVAdapter(disabilityList: List<Int>) {
        val disabilityRVAdapter = DetailDisabilityRVAdapter(disabilityList)
        binding.detailDisabilityIvRv.adapter = disabilityRVAdapter
        binding.detailDisabilityIvRv.layoutManager = GridLayoutManager(applicationContext, 3)
    }

    private fun settingToolbar() {
        binding.detailToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingReviewBtn(placeName: String, placeAddress: String, placeImg: String?) {
        binding.detailMoreReviewBtn.setOnClickListener {
            val intent = Intent(this, ReviewListActivity::class.java)
            intent.putExtra("placeName", placeName)
            intent.putExtra("placeAddress", placeAddress)
            startActivity(intent)
        }

        binding.detailReviewBtn.setOnClickListener {
            val intent = Intent(this, WriteReviewActivity::class.java)
            intent.putExtra("placeName", placeName)
            intent.putExtra("placeAddress", placeAddress)
            intent.putExtra("placeImg", placeImg)
            startActivity(intent)
        }
    }

    private fun handleCommonDetailPlaceInfo(
        reviewList: List<Review>?,
        disability: List<Int>,
        name: String,
        address: String,
        overview: String,
        image: String?,
        longitude: Double,
        latitude: Double,
        category: String,
        subDisability: List<String>
    ) {
        //reviewList?.let { settingReviewRVAdapter(it) }
        settingDisabilityRVAdapter(disability)
        // settingDetailInfoRVAdapter(detailPlaceInfo.subDisability)

        binding.detailTitleTv.text = name
        binding.detailAddressTv.text = address
        binding.detailBasicContentTv.text = overview
        binding.detailBasicAddressContentTv.text = address
        //binding.detailToolbarTitleTv.text = category
        //binding.detailToolbarTitleTv.text = category

        if (image != null) {
            Glide.with(binding.detailThumbnailIv.context)
                .load(image)
                .error(R.drawable.empty_view)
                .into(binding.detailThumbnailIv)
        }

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(longitude, latitude))
        naverMap.moveCamera(cameraUpdate)

        addMapMarker(longitude, latitude)

        settingReviewBtn(name, address, image)
    }

    private fun getDetailPlaceInfo(placeId: Long) {
        viewModel.getDetailPlace(placeId)

        viewModel.detailPlaceInfo.observe(this@DetailActivity) { detailPlaceInfo ->
            handleCommonDetailPlaceInfo(
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
            Log.e("bookmarkcheck", detailPlaceInfo.isMark.toString())

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

                Log.e("bookmarkcheck", detailPlaceInfo.isMark.toString())
            }
            binding.detailBookmarkCount.text = detailPlaceInfo.bookmarkNum.toString()


        }
    }

    private fun getDetailPlaceInfoGuest(placeId: Long) {
        viewModel.getDetailPlaceGuest(placeId)

        viewModel.detailPlaceInfoGuest.observe(this@DetailActivity) { detailPlaceInfoGuest ->
            handleCommonDetailPlaceInfo(
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
        mLocationSource =
            FusedLocationSource(this, EmergencyMapActivity.LOCATION_PERMISSION_REQUEST_CODE)
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