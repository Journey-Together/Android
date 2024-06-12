package kr.tekit.lion.daongil.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemDetailReviewSmallBinding
import kr.tekit.lion.daongil.domain.model.Review
import java.time.format.DateTimeFormatter

class DetailReviewRVAdapter(private val reviewList : List<Review>) : RecyclerView.Adapter<DetailReviewRVAdapter.DetailReviewViewHolder>() {

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
            binding.itemDetailReviewStarScoreTv.text = reviewData.grade.toString()

            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            val formattedDate = reviewData.date.format(formatter)
            binding.itemDetailReviewDate.text = formattedDate

            Glide.with(binding.itemDetailReviewProfileIv.context)
                .load(reviewData.profileImg)
                .error(R.drawable.default_profile)
                .into(binding.itemDetailReviewProfileIv)

            Glide.with(binding.itemDetailReviewIv.context)
                .load(reviewData.reviewImg)
                .error(R.drawable.empty_view_small)
                .into(binding.itemDetailReviewIv)
        }
    }
}