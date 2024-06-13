package kr.tekit.lion.daongil.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemDetailReviewBigBinding
import kr.tekit.lion.daongil.domain.model.ReviewDetail
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface

class ReviewListRVAdapter(
    private val reviewList : List<ReviewDetail>,
    private val activty : FragmentActivity
) : RecyclerView.Adapter<ReviewListRVAdapter.ReviewListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
        val binding : ItemDetailReviewBigBinding = ItemDetailReviewBigBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ReviewListViewHolder(binding, activty)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    class ReviewListViewHolder(val binding: ItemDetailReviewBigBinding, val activty: FragmentActivity) : RecyclerView.ViewHolder(binding.root), ConfirmDialogInterface {
        private var dialog : ConfirmDialog? = null
        fun bind(review : ReviewDetail) {
            binding.itemDetailReviewBigNickname.text = review.nickname
            binding.itemDetailReviewBigContent.text = review.content
            binding.itemDetailReviewBigDate.text = review.date

            val reviewImageRVAdapter = ReviewImageRVAdapter(review.imageList)
            binding.itemDetailReviewBigRv.adapter = reviewImageRVAdapter
            binding.itemDetailReviewBigRv.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            binding.itemDetailReviewBigComplain.setOnClickListener {
                val dialog = ConfirmDialog(this, "신고하기", "해당 댓글을 신고하시겠습니까?", "신고하기",
                    R.color.button_tertiary, R.color.white)
                dialog.isCancelable = false
                dialog.show(activty.supportFragmentManager, "ComplainDialog")
            }
        }

        override fun onPosBtnClick() {
            dialog?.dismiss()
        }
    }
}