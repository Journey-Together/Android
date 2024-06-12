package kr.tekit.lion.daongil.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.databinding.ActivityReviewListBinding
import kr.tekit.lion.daongil.domain.model.ReviewDetail
import kr.tekit.lion.daongil.presentation.home.adapter.ReviewListRVAdapter

class ReviewListActivity : AppCompatActivity() {
    val binding : ActivityReviewListBinding by lazy {
        ActivityReviewListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingToolbar()
        settingReviewListRVAdapter()
        settingPlaceData()
    }

    private fun settingToolbar() {
        binding.reviewListToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingReviewListRVAdapter() {
        val reviewList = listOf(
            ReviewDetail("재롱맘",
                "https://i.namu.wiki/i/sLpl_9SaPt63LS9uKn7ptjw1GtopAOeL-fVSbFHsfwm2ZKwywO-4rd91q_MPds0-pXHkGqRyAj6u366J2-SygA.webp",
                "다양한 편의시설 덕분에 편하게 여행할 수 있었어요! 가족 중에 몸이 불편한 분이 계셔서 휠체어가 필요했는데 휠체어 대여도 해줘서 넘 좋았습니다 굿굿 주위에 식당도 많고 바람 쐬러 가기 좋아요 주말에 좋은 시간 보내고 왔습니다~",
                "2024.06.11",
                4.5f,
                listOf("https://a.cdn-hotels.com/gdcs/production3/d844/2981861a-8dc3-44c1-a38d-4f8257914a02.jpg?impolicy=fcrop&w=800&h=533&q=medium",
                    "https://cdn.3hoursahead.com/v2/content/image-comp/520d6aa4-2e4d-401d-925e-88f28a157f2d.webp")
            ),
            ReviewDetail("락스타",
                "https://pbs.twimg.com/media/F8oSaScbwAAWB45.png",
                "평소에도 자주 가는 곳인데 무장애 편의시설이 다양하고 배려가 많은 곳이라 편하게 쉬다 오기 좋은 것 같아요 주말에 놀러갈 곳을 찾고 계신다면 이 곳을 추천 드려요!!",
                "2024.06.11",
                5.0f,
                listOf("https://cdn.3hoursahead.com/v2/content/image-comp/f159e0e5-0570-4c3f-b963-234339ada50f.webp",
                    "https://gdimg.gmarket.co.kr/2886875362/still/280?ver=1680840277",
                    "https://media.triple.guide/triple-cms/c_limit,f_auto,h_1024,w_1024/910345ec-4493-4601-8995-ff2150405917.jpeg")
            ),
            ReviewDetail("양파쿵야",
                "https://blog.kakaocdn.net/dn/bcOiIk/btseT1XwQHL/frw5KjTFECKzzk5EdPHcGK/img.jpg",
                "날씨가 좋아서 더 좋았던 곳! 양파쿵야 이름 한번 믿고 다녀와 보세요 무장애 여행으로 딱인 곳입니다 여유롭고 딱 좋아요~",
                "2024.06.11",
                4.0f,
                listOf("https://media.triple.guide/triple-cms/c_limit,f_auto,h_1024,w_1024/b11e711e-5e70-45c4-9984-630e3a50dad3.jpeg",
                    "https://a.travel-assets.com/findyours-php/viewfinder/images/res70/39000/39255-Seongsan-Ilchulbong-Peak.jpg?impolicy=fcrop&w=400&h=225&q=mediumLow",)
            )
        )

        val reviewListRVAdapter = ReviewListRVAdapter(reviewList, this)
        binding.reviewListRv.adapter = reviewListRVAdapter
        binding.reviewListRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    fun settingPlaceData() {
        val placeName = intent.getStringExtra("placeName")
        val placeAddress = intent.getStringExtra("placeAddress")

        binding.reviewListTitle.text = placeName
        binding.reviewListAddress.text = placeAddress
    }
}