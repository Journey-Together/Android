package kr.tekit.lion.daongil.presentation.home.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemReviewWriteImageBinding

class WriteReviewImageRVAdapter(private val imageList : MutableList<Uri>)
    : RecyclerView.Adapter<WriteReviewImageRVAdapter.WriteReviewImageViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteReviewImageViewHolder {
        val binding : ItemReviewWriteImageBinding = ItemReviewWriteImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return WriteReviewImageViewHolder(binding, this)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: WriteReviewImageViewHolder, position: Int) {
        holder.bind(imageList[position], position)
    }

    class WriteReviewImageViewHolder(
        private val binding: ItemReviewWriteImageBinding,
        private val adapter : WriteReviewImageRVAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image : Uri, position: Int) {
            Glide.with(binding.itemWriteReviewImage.context)
                .load(image)
                .error(R.drawable.empty_view_small)
                .into(binding.itemWriteReviewImage)

            binding.itemWriteReviewCancelIb.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    adapter.imageList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}