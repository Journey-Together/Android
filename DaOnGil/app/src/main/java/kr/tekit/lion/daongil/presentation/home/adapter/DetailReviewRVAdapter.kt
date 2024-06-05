package kr.tekit.lion.daongil.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemDetailReviewSmallBinding
import kr.tekit.lion.daongil.domain.model.Review

class DetailReviewRVAdapter(var reviewList : List<Review>) : RecyclerView.Adapter<DetailReviewRVAdapter.DetailReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        val binding : ItemDetailReviewSmallBinding = ItemDetailReviewSmallBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return DetailReviewViewHolder(binding)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    class DetailReviewViewHolder(val binding : ItemDetailReviewSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewData : Review) {
            binding.itemDetailReviewNickname.text = reviewData.nickname
            binding.itemDetailReviewContent.text = reviewData.content
            binding.itemDetailReviewDate.text = reviewData.date
        }
    }
}