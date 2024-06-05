package kr.tekit.lion.daongil.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.databinding.ActivityDetailBinding
import kr.tekit.lion.daongil.domain.model.DetailInfo
import kr.tekit.lion.daongil.domain.model.Review
import kr.tekit.lion.daongil.presentation.home.adapter.DetailInfoRVAdapter
import kr.tekit.lion.daongil.presentation.home.adapter.DetailReviewRVAdapter

class DetailActivity : AppCompatActivity() {
    private val binding : ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingDetailInfoRVAdapter()
        settingReviewRVAdapter()
        settingToolbar()
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

    private fun settingReviewRVAdapter() {
        val reviewList = listOf(
            Review("홍길동", "휠체어를 대여해주셔서 편하게 여행할 수 있었던 것 같습니다! 주차도 편하고 좋았어요! 아쉬운 점은 장애인 화장실 시설이 아쉬웠습니다~\n" +
                    "그래도 여행은 즐거웠어요~~~~~~~~~~~~~", "2024.06.01"),
            Review("김길동", "여행지로 추천!! 서울 구경 제대로 하고 갑니다 굿굿", "2024.06.01")
        )

        val detailReviewRVAdapter = DetailReviewRVAdapter(reviewList)
        binding.detailReviewRv.adapter = detailReviewRVAdapter
        binding.detailReviewRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun settingToolbar() {
        binding.detailToolbar.setNavigationOnClickListener {
            finish()
        }
    }
}