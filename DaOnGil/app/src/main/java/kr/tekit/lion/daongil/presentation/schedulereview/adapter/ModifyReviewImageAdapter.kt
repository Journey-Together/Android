package kr.tekit.lion.daongil.presentation.schedulereview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemReviewWriteImageBinding
import kr.tekit.lion.daongil.domain.model.ReviewImage

class ModifyReviewImageAdapter (
    private val images : List<ReviewImage>,
    private val imageRemoveListener: (imagePosition: Int) -> Unit
) : RecyclerView.Adapter<ModifyReviewImageAdapter.ModifyScheduleReviewImageViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ModifyScheduleReviewImageViewHolder {
        val binding: ItemReviewWriteImageBinding = ItemReviewWriteImageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

        return ModifyScheduleReviewImageViewHolder(binding, imageRemoveListener)
    }

    override fun onBindViewHolder(holder: ModifyScheduleReviewImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int  = images.size

    class ModifyScheduleReviewImageViewHolder(
        private val binding: ItemReviewWriteImageBinding,
        private val imageRemoveListener: (imagePosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemWriteReviewCancelIb.setOnClickListener {
                imageRemoveListener(absoluteAdapterPosition)
            }
        }

        fun bind(reviewImage: ReviewImage) {
            val imageData = determineImageType(reviewImage)

            imageData?.let {
                Glide.with(binding.itemWriteReviewImage.context)
                    .load(it)
                    .error(R.drawable.empty_view_small)
                    .into(binding.itemWriteReviewImage)
            }
        }

        private fun determineImageType(reviewImage: ReviewImage) : Any? {
            if (reviewImage.imageUri != null) {
                return reviewImage.imageUri
            } else if (reviewImage.imageUrl != null) {
                return reviewImage.imageUrl
            }
            return null
        }
    }
}