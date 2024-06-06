package kr.tekit.lion.daongil.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemDetailReviewBigBinding
import kr.tekit.lion.daongil.domain.model.ReviewDetail

class ReviewListRVAdapter(private val reviewList : List<ReviewDetail>, private val context: Context) : RecyclerView.Adapter<ReviewListRVAdapter.ReviewListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
        val binding : ItemDetailReviewBigBinding = ItemDetailReviewBigBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ReviewListViewHolder(binding, context)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    class ReviewListViewHolder(val binding: ItemDetailReviewBigBinding, val context : Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review : ReviewDetail) {
            binding.itemDetailReviewBigNickname.text = review.nickname
            binding.itemDetailReviewBigContent.text = review.content
            binding.itemDetailReviewBigDate.text = review.date

            val reviewImageRVAdapter = ReviewImageRVAdapter(review.imageList)
            binding.itemDetailReviewBigRv.adapter = reviewImageRVAdapter
            binding.itemDetailReviewBigRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}