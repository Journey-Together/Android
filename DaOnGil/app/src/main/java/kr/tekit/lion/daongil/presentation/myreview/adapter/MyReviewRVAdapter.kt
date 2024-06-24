package kr.tekit.lion.daongil.presentation.myreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemMyReviewBinding
import kr.tekit.lion.daongil.domain.model.MyPlaceReview

class MyReviewRVAdapter(
    private val myReviewList: List<MyPlaceReview>,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MyReviewRVAdapter.MyReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val binding: ItemMyReviewBinding = ItemMyReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return MyReviewViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bind(myReviewList[position])
    }

    class MyReviewViewHolder(
        private val binding: ItemMyReviewBinding,
        private val itemClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener.invoke(absoluteAdapterPosition)
            }
        }

        fun bind(myPlaceReview: MyPlaceReview) {
            binding.textViewMyReviewLocation.text = myPlaceReview.address
            binding.textViewMyReviewLocationName.text = myPlaceReview.name
            binding.ratingbarItemMyReview.rating = myPlaceReview.grade

            Glide.with(binding.imageViewMyReivew.context)
                .load(myPlaceReview.image)
                .error(R.drawable.empty_view)
                .into(binding.imageViewMyReivew)
        }
    }
}