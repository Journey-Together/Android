package kr.tekit.lion.daongil.presentation.myreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemMyReviewBinding
import kr.tekit.lion.daongil.databinding.ItemMyReviewHeaderBinding
import kr.tekit.lion.daongil.domain.model.MyPlaceReview
import kr.tekit.lion.daongil.domain.model.MyPlaceReviewInfo

class MyReviewRVAdapter(
    private val myReviewList: MyPlaceReview,
    private val myReviewListInfo: List<MyPlaceReviewInfo>,
    private val onMoveReviewListClick: (Long) -> Unit,
    private val onModifyClick: () -> Unit,
    private val onDeleteClick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ViewType.HEADER.ordinal
        } else {
            ViewType.ITEM.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.entries[viewType]) {
            ViewType.HEADER -> {
                val binding: ItemMyReviewHeaderBinding = ItemMyReviewHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                MyReviewHeaderViewHolder(binding)
            }
            ViewType.ITEM -> {
                val binding: ItemMyReviewBinding = ItemMyReviewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                MyReviewViewHolder(binding, onMoveReviewListClick, onModifyClick, onDeleteClick)
            }
        }
    }

    override fun getItemCount(): Int = myReviewListInfo.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyReviewHeaderViewHolder) {
            holder.bind(myReviewList)
        } else if (holder is MyReviewViewHolder) {
            holder.bind(myReviewListInfo[position - 1])
        }
    }

    class MyReviewViewHolder(
        private val binding: ItemMyReviewBinding,
        private val onMoveReviewListClick: (Long) -> Unit,
        private val onModifyClick: () -> Unit,
        private val onDeleteClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(myPlaceReview: MyPlaceReviewInfo) {
            binding.textViewMyReviewLocationName.text = myPlaceReview.name
            binding.ratingbarItemMyReview.rating = myPlaceReview.grade
            binding.textViewMyReviewDate.text = myPlaceReview.date.toString()
            binding.textViewMyReviewContent.text = myPlaceReview.content

            binding.imageViewMove.setOnClickListener {
                onMoveReviewListClick(myPlaceReview.placeId)
            }

            binding.myReviewModifyBtn.setOnClickListener {
                onModifyClick()
            }

            binding.myReviewDeleteBtn.setOnClickListener {
                onDeleteClick(myPlaceReview.reviewId)
            }

            val myReviewImageRVAdapter = MyReviewImageRVAdapter(myPlaceReview.images)
            binding.recyclerViewMyReivew.adapter = myReviewImageRVAdapter
        }
    }

    class MyReviewHeaderViewHolder(
        private val binding: ItemMyReviewHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(myPlaceReview: MyPlaceReview) {
            binding.textViewMyReivewTotal.text = myPlaceReview.reviewNum.toString()
        }
    }
}

enum class ViewType {
    HEADER,
    ITEM
}