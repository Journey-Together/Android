package kr.tekit.lion.daongil.presentation.myreview.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemMyReviewDetailVpBinding

class MyReviewDetailVPAdapter(private val reviewImages: List<String>) : RecyclerView.Adapter<MyReviewDetailVPAdapter.MyReviewDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewDetailViewHolder {
        val binding : ItemMyReviewDetailVpBinding = ItemMyReviewDetailVpBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return MyReviewDetailViewHolder(binding)
    }

    override fun getItemCount(): Int = reviewImages.size

    override fun onBindViewHolder(holder: MyReviewDetailViewHolder, position: Int) {
        holder.bind(reviewImages[position])
    }

    class MyReviewDetailViewHolder(private val binding: ItemMyReviewDetailVpBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewImages: String) {
            Glide.with(binding.imageViewMyReviewDetail.context)
                .load(reviewImages)
                .error(R.drawable.empty_view)
                .into(binding.imageViewMyReviewDetail)
        }
    }
}