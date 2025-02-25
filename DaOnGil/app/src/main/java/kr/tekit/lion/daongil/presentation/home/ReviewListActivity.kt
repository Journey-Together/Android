package kr.tekit.lion.daongil.presentation.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityReviewListBinding
import kr.tekit.lion.daongil.domain.model.PlaceReview
import kr.tekit.lion.daongil.presentation.ext.addOnScrollEndListener
import kr.tekit.lion.daongil.presentation.ext.showConfirmDialog
import kr.tekit.lion.daongil.presentation.home.adapter.ReviewListRVAdapter
import kr.tekit.lion.daongil.presentation.home.vm.ReviewListViewModel
import kr.tekit.lion.daongil.presentation.home.vm.ReviewListViewModelFactory

class ReviewListActivity : AppCompatActivity() {
    private val viewModel : ReviewListViewModel by viewModels { ReviewListViewModelFactory() }
    private val binding : ActivityReviewListBinding by lazy {
        ActivityReviewListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val placeId = intent.getLongExtra("reviewPlaceId", -1)

        settingToolbar()
        getReviewListInfo(placeId)
    }

    private fun settingToolbar() {
        binding.reviewListToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingReviewListRVAdapter(reviewList: List<PlaceReview>) {
        val reviewListRVAdapter = ReviewListRVAdapter(reviewList) {
            this.showConfirmDialog(
                supportFragmentManager,
                "신고하기", "해당 댓글을 신고하시겠습니까?", "신고하기",
            ){
                // 신고하기 api 연결
            }

        }
        binding.reviewListRv.adapter = reviewListRVAdapter
        binding.reviewListRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun getReviewListInfo(placeId : Long) {
        viewModel.getPlaceReview(placeId)

        binding.reviewListRv.addOnScrollEndListener {
            if (viewModel.isLastPage.value == false) {
                viewModel.getNewPlaceReview(placeId)
            }
        }

        viewModel.placeReviewInfo.observe(this@ReviewListActivity) { placeReviewInfo ->
            binding.reviewListTitleTv.text = placeReviewInfo.placeName
            binding.reviewListAddressTv.text = placeReviewInfo.placeAddress
            binding.reviewListCount2Tv.text = placeReviewInfo.placeReviewList.size.toString()

            Glide.with(binding.reviewListThumbnailIv)
                .load(placeReviewInfo.placeImg)
                .error(R.drawable.empty_view)
                .into(binding.reviewListThumbnailIv)

            settingReviewListRVAdapter(placeReviewInfo.placeReviewList)
        }
    }
}