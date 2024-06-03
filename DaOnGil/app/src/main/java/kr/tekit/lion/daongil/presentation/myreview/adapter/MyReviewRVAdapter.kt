package kr.tekit.lion.daongil.presentation.myreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemMyReviewBinding
import kr.tekit.lion.daongil.domain.model.MyReview

class MyReviewRVAdapter(private val myReviewList: List<MyReview>, private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<MyReviewRVAdapter.MyReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val binding : ItemMyReviewBinding = ItemMyReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return MyReviewViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bind(myReviewList[position])
    }

    class MyReviewViewHolder(val binding: ItemMyReviewBinding, private val itemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        val Ratings = listOf(
            binding.myReviewRating1,
            binding.myReviewRating2,
            binding.myReviewRating3,
            binding.myReviewRating4,
            binding.myReviewRating5
        )

        init {
            binding.root.setOnClickListener {
                itemClickListener.invoke(adapterPosition)
            }
        }

        fun bind(myReview: MyReview) {
            binding.textViewMyReviewLocation.text = myReview.myReviewLocation
            binding.textViewMyReviewLocationName.text = myReview.myReviewLocationName

            settingRating(myReview.rating.toDouble())
        }

        private fun settingRating(rating: Double) {
            for (i in 0 until 5) {
                val imageView = Ratings[i]
                when {
                    rating >= (i + 1) -> {
                        imageView.setImageResource(R.drawable.star_filled_icon)
                        imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.primary))
                    }
                    rating >= (i + 0.5) -> {
                        imageView.setImageResource(R.drawable.star_half_filled_icon)
                        imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.primary))
                    }
                    else -> {
                        imageView.setImageResource(R.drawable.star_icon)
                        imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.primary_disabled))
                    }
                }
            }
        }
    }
}