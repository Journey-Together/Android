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
        init {
            binding.root.setOnClickListener {
                itemClickListener.invoke(adapterPosition)
            }
        }

        fun bind(myReview: MyReview) {
            binding.textViewMyReviewLocation.text = myReview.myReviewLocation
            binding.textViewMyReviewLocationName.text = myReview.myReviewLocationName
        }
    }
}