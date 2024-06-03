package kr.tekit.lion.daongil.presentation.myreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemMyReviewBinding
import kr.tekit.lion.daongil.domain.model.MyReview

class MyReviewRVAdapter(private val myReviewList: List<MyReview>) : RecyclerView.Adapter<MyReviewRVAdapter.MyReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val binding : ItemMyReviewBinding = ItemMyReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return MyReviewViewHolder(binding)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bind(myReviewList[position])
    }

    class MyReviewViewHolder(val binding: ItemMyReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val Ratings = listOf(
            binding.myReviewRating1,
            binding.myReviewRating2,
            binding.myReviewRating3,
            binding.myReviewRating4,
            binding.myReviewRating5
        )

        fun bind(myReview: MyReview) {
            binding.textViewMyReviewLocation.text = myReview.myReviewLocation
            binding.textViewMyReviewLocationName.text = myReview.myReviewLocationName

            setRating(myReview.rating.toDouble())
        }

        fun setRating(rating: Double) {
            for (i in 0 until 5) {
                Ratings[i].setImageResource(
                    when {
                        rating >= (i + 1) -> R.drawable.star_filled_icon
                        rating >= (i + 0.5) -> R.drawable.star_half_filled_icon
                        else -> R.drawable.star_icon
                    }
                )
            }
        }
    }
}