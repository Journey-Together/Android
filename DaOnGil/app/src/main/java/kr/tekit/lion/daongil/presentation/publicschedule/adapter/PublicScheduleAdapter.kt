package kr.tekit.lion.daongil.presentation.publicschedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemPublicScheduleBinding
import kr.tekit.lion.daongil.domain.model.OpenPlanInfo

class PublicScheduleAdapter(
    private val onPublicScheduleClicked: (Int) -> Unit
) : ListAdapter<OpenPlanInfo, PublicScheduleAdapter.PublicScheduleViewHolder>(diffUtil) {

    companion object diffUtil : DiffUtil.ItemCallback<OpenPlanInfo>() {
        override fun areItemsTheSame(oldItem: OpenPlanInfo, newItem: OpenPlanInfo): Boolean {
            return oldItem.planId == newItem.planId
        }

        override fun areContentsTheSame(oldItem: OpenPlanInfo, newItem: OpenPlanInfo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PublicScheduleViewHolder(
            ItemPublicScheduleBinding.inflate(inflater, parent, false),
            onPublicScheduleClicked
        )
    }

    override fun onBindViewHolder(holder: PublicScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PublicScheduleViewHolder(
        private val binding: ItemPublicScheduleBinding,
        private val onPublicScheduleClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onPublicScheduleClicked(absoluteAdapterPosition)
            }
        }

        fun bind(publicSchedule: OpenPlanInfo) {
            binding.apply {
                textViewItemPublicScheduleName.text = publicSchedule.title
                textViewItemPublicScheduleNickname.text = publicSchedule.memberNickname
                textViewItemPublicSchedulePeriod.text = publicSchedule.date

                // 여행지 대표 이미지
                Glide.with(itemView.context)
                    .load(publicSchedule.imageUrl)
                    .placeholder(R.drawable.empty_view)
                    .error(R.drawable.empty_view)
                    .into(imageViewItemPublicScheduleThumb)

                // 프로필 사진
                Glide.with(itemView.context)
                    .load(publicSchedule.memberImageUrl)
                    .placeholder(R.drawable.empty_view_small)
                    .error(R.drawable.empty_view_small)
                    .override(16, 16)
                    .into(imageViewItemPublicScheduleProfile)
            }
        }
    }
}
