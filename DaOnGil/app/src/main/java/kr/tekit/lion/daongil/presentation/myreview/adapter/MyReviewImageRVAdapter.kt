package kr.tekit.lion.daongil.presentation.myreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.getstream.photoview.PhotoView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemReviewBigImageBinding
import kr.tekit.lion.daongil.presentation.ext.showPhotoDialog

class MyReviewImageRVAdapter(private val imageList: List<String>) :
    RecyclerView.Adapter<MyReviewImageRVAdapter.MyReviewImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewImageViewHolder {
        val binding = ItemReviewBigImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return MyReviewImageViewHolder(binding)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: MyReviewImageViewHolder, position: Int) {
        holder.bind(imageList[position], imageList, position)
    }

    class MyReviewImageViewHolder(
        private val binding: ItemReviewBigImageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: String, imageList: List<String>, position: Int) {
            Glide.with(binding.reviewImage.context)
                .load(image)
                .error(R.drawable.empty_view_small)
                .into(binding.reviewImage)

            binding.reviewImage.setOnClickListener {
                val context = binding.root.context
                if (context is FragmentActivity) {
                    val fragmentManager = context.supportFragmentManager
                    context.showPhotoDialog(fragmentManager, imageList, position)
                }
            }
        }
    }
}