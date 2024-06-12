package kr.tekit.lion.daongil.presentation.publicschedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemPublicScheduleBinding
import kr.tekit.lion.daongil.domain.model.OpenPlanInfo

class PublicScheduleAdpater(
    private val onPublicScheduleClicked: (Int) -> Unit
) :
    RecyclerView.Adapter<PublicScheduleAdpater.PublicScheduleViewHolder>() {

    private var items: MutableList<OpenPlanInfo> = mutableListOf()

    fun addItems(newItems: List<OpenPlanInfo>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PublicScheduleViewHolder(
            ItemPublicScheduleBinding.inflate(inflater, parent, false),
            onPublicScheduleClicked
        )
    }

    override fun onBindViewHolder(holder: PublicScheduleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PublicScheduleViewHolder(
        private val binding: ItemPublicScheduleBinding,
        private val onPublicScheduleClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
            init{
                binding.root.setOnClickListener {
                    onPublicScheduleClicked(adapterPosition)
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