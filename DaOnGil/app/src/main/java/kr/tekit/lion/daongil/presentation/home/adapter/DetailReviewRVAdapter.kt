package kr.tekit.lion.daongil.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemDetailReviewSmallBinding
import kr.tekit.lion.daongil.domain.model.Review

class DetailReviewRVAdapter(private val reviewList : List<Review>)
    : RecyclerView.Adapter<DetailReviewRVAdapter.DetailReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        val binding : ItemDetailReviewSmallBinding = ItemDetailReviewSmallBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return DetailReviewViewHolder(binding)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    class DetailReviewViewHolder(private val binding : ItemDetailReviewSmallBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewData : Review) {
            with(binding) {
                itemDetailReviewNickname.text = reviewData.nickname
                itemDetailReviewContent.text = reviewData.content
                itemDetailReviewDate.text = reviewData.date.toString()
                itemDetailReviewRatingbar.rating = reviewData.grade

                Glide.with(itemDetailReviewProfileIv.context)
                    .load(reviewData.profileImg)
                    .error(R.drawable.default_profile)
                    .into(itemDetailReviewProfileIv)

                if (reviewData.reviewImg != null) {
                    itemDetailReviewIv.visibility = View.VISIBLE

                    Glide.with(itemDetailReviewIv.context)
                        .load(reviewData.reviewImg)
                        .error(R.drawable.empty_view_small)
                        .into(itemDetailReviewIv)
                } else {
                    itemDetailReviewIv.visibility = View.GONE
                }
            }
        }
    }
}