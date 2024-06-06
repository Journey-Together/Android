package kr.tekit.lion.daongil.presentation.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityReviewListBinding
import kr.tekit.lion.daongil.domain.model.Review
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
    }

    private fun settingToolbar() {
        binding.reviewListToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingReviewListRVAdapter() {
        val reviewList = listOf(
            ReviewDetail("홍길동", "휠체어를 대여해주셔서 편하게 여행할 수 있었던 것 같습니다! 주차도 편하고 좋았어요! 아쉬운 점은 장애인 화장실 시설이 아쉬웠습니다~\n" +
                    "그래도 여행은 즐거웠어요~~~~~~~~~~~~~", "2024.06.01",
                listOf("https://i.namu.wiki/i/aaXYKOQIQysu0xGNylPeZ0WGm9rMQvRxPcavkWndMHDCbDXauKFT2w_tbC2zygc-z993Ok-F2THft8xsX42dWoijVKTV85RVz9LJY1BODXEooKpPVf57K0OWZeETE5QWvleeZHnZeNUxageq1I8S5w.webp", "https://post-phinf.pstatic.net/MjAyMDA5MDJfMTcy/MDAxNTk5MDQyMzUyMzI3.2AFfkxQrEt77Rykdoui235yMaUEJ-Ernj0VgAuHygc0g.fgj_F4mphxVoeld5q8lmAFaX2l9-8lQiWyfSbwBjd_Ag.JPEG/IMG_4919.jpg?type=w1200")
            ),
            ReviewDetail("김길동", "재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워.. 재롱이 귀여워..", "2024.06.01",
                listOf("https://i.namu.wiki/i/aaXYKOQIQysu0xGNylPeZ0WGm9rMQvRxPcavkWndMHDCbDXauKFT2w_tbC2zygc-z993Ok-F2THft8xsX42dWoijVKTV85RVz9LJY1BODXEooKpPVf57K0OWZeETE5QWvleeZHnZeNUxageq1I8S5w.webp", "https://post-phinf.pstatic.net/MjAyMDA5MDJfMTcy/MDAxNTk5MDQyMzUyMzI3.2AFfkxQrEt77Rykdoui235yMaUEJ-Ernj0VgAuHygc0g.fgj_F4mphxVoeld5q8lmAFaX2l9-8lQiWyfSbwBjd_Ag.JPEG/IMG_4919.jpg?type=w1200")
            ),
            ReviewDetail("김길동", "여행지로 추천!! 서울 구경 제대로 하고 갑니다 굿굿", "2024.06.01",
                listOf("https://i.namu.wiki/i/aaXYKOQIQysu0xGNylPeZ0WGm9rMQvRxPcavkWndMHDCbDXauKFT2w_tbC2zygc-z993Ok-F2THft8xsX42dWoijVKTV85RVz9LJY1BODXEooKpPVf57K0OWZeETE5QWvleeZHnZeNUxageq1I8S5w.webp", "https://post-phinf.pstatic.net/MjAyMDA5MDJfMTcy/MDAxNTk5MDQyMzUyMzI3.2AFfkxQrEt77Rykdoui235yMaUEJ-Ernj0VgAuHygc0g.fgj_F4mphxVoeld5q8lmAFaX2l9-8lQiWyfSbwBjd_Ag.JPEG/IMG_4919.jpg?type=w1200")
            ),
            ReviewDetail("김길동", "여행지로 추천!! 서울 구경 제대로 하고 갑니다 굿굿", "2024.06.01",
                listOf("https://i.namu.wiki/i/aaXYKOQIQysu0xGNylPeZ0WGm9rMQvRxPcavkWndMHDCbDXauKFT2w_tbC2zygc-z993Ok-F2THft8xsX42dWoijVKTV85RVz9LJY1BODXEooKpPVf57K0OWZeETE5QWvleeZHnZeNUxageq1I8S5w.webp", "https://post-phinf.pstatic.net/MjAyMDA5MDJfMTcy/MDAxNTk5MDQyMzUyMzI3.2AFfkxQrEt77Rykdoui235yMaUEJ-Ernj0VgAuHygc0g.fgj_F4mphxVoeld5q8lmAFaX2l9-8lQiWyfSbwBjd_Ag.JPEG/IMG_4919.jpg?type=w1200")
            ),
            ReviewDetail("김길동", "여행지로 추천!! 서울 구경 제대로 하고 갑니다 굿굿", "2024.06.01",
                listOf("https://i.namu.wiki/i/aaXYKOQIQysu0xGNylPeZ0WGm9rMQvRxPcavkWndMHDCbDXauKFT2w_tbC2zygc-z993Ok-F2THft8xsX42dWoijVKTV85RVz9LJY1BODXEooKpPVf57K0OWZeETE5QWvleeZHnZeNUxageq1I8S5w.webp", "https://post-phinf.pstatic.net/MjAyMDA5MDJfMTcy/MDAxNTk5MDQyMzUyMzI3.2AFfkxQrEt77Rykdoui235yMaUEJ-Ernj0VgAuHygc0g.fgj_F4mphxVoeld5q8lmAFaX2l9-8lQiWyfSbwBjd_Ag.JPEG/IMG_4919.jpg?type=w1200")
            ),
        )

        val reviewListRVAdapter = ReviewListRVAdapter(reviewList, applicationContext)
        binding.reviewListRv.adapter = reviewListRVAdapter
        binding.reviewListRv.layoutManager = LinearLayoutManager(applicationContext)
    }
}