package kr.tekit.lion.daongil.presentation.myschedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemMyScheduleElapsedBinding
import kr.tekit.lion.daongil.domain.model.MyElapsedScheduleInfo

class MyScheduleElapsedAdapter(
    private val onReviewButtonClicked: (planPosition: Int) -> Unit,
    private val onScheduleItemClicked: (planPosition: Int) -> Unit
) : ListAdapter<MyElapsedScheduleInfo, MyScheduleElapsedAdapter.ElapsedScheduleViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElapsedScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ElapsedScheduleViewHolder(
            ItemMyScheduleElapsedBinding.inflate(
                inflater,
                parent,
                false
            ), onReviewButtonClicked, onScheduleItemClicked
        )
    }

    override fun onBindViewHolder(holder: ElapsedScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ElapsedScheduleViewHolder(
        private val binding: ItemMyScheduleElapsedBinding,
        private val onReviewButtonClicked: (Int) -> Unit,
        private val onScheduleItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonMyScheduleElapsedReview.setOnClickListener {
                onReviewButtonClicked(absoluteAdapterPosition)
            }
            binding.root.setOnClickListener {
                onScheduleItemClicked(absoluteAdapterPosition)
            }
        }

        fun bind(mySchedule: MyElapsedScheduleInfo) {
            binding.apply {
                buttonMyScheduleElapsedReview.apply {
                    if (mySchedule.hasReview) {
                        visibility = View.GONE
                    } else {
                        visibility = View.VISIBLE
                    }
                }

                if (mySchedule.imageUrl != "") {
                    Glide.with(itemView.context)
                        .load(mySchedule.imageUrl)
                        .placeholder(R.drawable.empty_view_small)
                        .error(R.drawable.empty_view_small)
                        .override(50, 50)
                        .into(binding.imageViewMyScheduleElapsed)
                }

                textViewMyScheduleElapsedName.text = mySchedule.title
                textViewMyScheduleElapsedPeriod.text = itemView.context.getString(
                    R.string.text_schedule_period,
                    mySchedule.startDate,
                    mySchedule.endDate
                )
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyElapsedScheduleInfo>() {
            override fun areItemsTheSame(
                oldItem: MyElapsedScheduleInfo,
                newItem: MyElapsedScheduleInfo
            ): Boolean {
                return oldItem.planId == newItem.planId
            }

            override fun areContentsTheSame(
                oldItem: MyElapsedScheduleInfo,
                newItem: MyElapsedScheduleInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}