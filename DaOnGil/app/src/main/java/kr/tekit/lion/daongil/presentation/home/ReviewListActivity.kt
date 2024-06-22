package kr.tekit.lion.daongil.presentation.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityReviewListBinding
import kr.tekit.lion.daongil.domain.model.PlaceReview
import kr.tekit.lion.daongil.presentation.home.adapter.DialogCallback
import kr.tekit.lion.daongil.presentation.home.adapter.ReviewListRVAdapter
import kr.tekit.lion.daongil.presentation.home.vm.ReviewListViewModel
import kr.tekit.lion.daongil.presentation.home.vm.ReviewListViewModelFactory
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface

class ReviewListActivity : AppCompatActivity(), DialogCallback {
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
        val reviewListRVAdapter = ReviewListRVAdapter(reviewList, this)
        binding.reviewListRv.adapter = reviewListRVAdapter
        binding.reviewListRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun getReviewListInfo(placeId : Long) {
        viewModel.getPlaceReview(placeId, size = 5, page = 0)

        viewModel.placeReviewInfo.observe(this@ReviewListActivity) { placeReviewInfo ->
            binding.reviewListTitle.text = placeReviewInfo.placeName
            binding.reviewListAddress.text = placeReviewInfo.placeAddress

            settingReviewListRVAdapter(placeReviewInfo.placeReviewList)
        }
    }

    override fun showConfirmDialog(dialogInterface: ConfirmDialogInterface) {
        val dialog = ConfirmDialog(
            dialogInterface, "신고하기", "해당 댓글을 신고하시겠습니까?", "신고하기",
            R.color.button_tertiary, R.color.white
        )
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "ComplainDialog")
    }
}