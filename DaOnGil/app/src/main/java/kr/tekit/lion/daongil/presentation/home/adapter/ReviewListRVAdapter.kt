package kr.tekit.lion.daongil.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemDetailReviewBigBinding
import kr.tekit.lion.daongil.domain.model.PlaceReview

class ReviewListRVAdapter(
    private val reviewList: List<PlaceReview>,
    private val dialogCallback: () -> Unit
) : RecyclerView.Adapter<ReviewListRVAdapter.ReviewListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
        val binding: ItemDetailReviewBigBinding = ItemDetailReviewBigBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ReviewListViewHolder(binding, dialogCallback)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    class ReviewListViewHolder(
        private val binding: ItemDetailReviewBigBinding,
        private val dialogCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: PlaceReview) {
            binding.itemDetailReviewBigNickname.text = review.nickname
            binding.itemDetailReviewBigContent.text = review.content
            binding.itemDetailReviewBigDate.text = review.date.toString()
            binding.itemDetailReviewBigRatingbar.rating = review.grade

            Glide.with(binding.itemDetailReviewBigProfileIv.context)
                .load(review.profileImg)
                .error(R.drawable.default_profile)
                .into(binding.itemDetailReviewBigProfileIv)

            val reviewImageRVAdapter = ReviewImageRVAdapter(review.imageList)
            binding.itemDetailReviewBigRv.adapter = reviewImageRVAdapter
            binding.itemDetailReviewBigRv.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            binding.itemDetailReviewBigComplain.setOnClickListener {
               dialogCallback()
            }
        }
    }
}