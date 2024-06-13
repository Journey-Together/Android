package kr.tekit.lion.daongil.presentation.schedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemScheduleImageBinding

class ScheduleImageViewPagerAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ScheduleImageViewPagerAdapter.ScheduleImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScheduleImageViewHolder(
            ItemScheduleImageBinding.inflate(
                inflater, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ScheduleImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ScheduleImageViewHolder(private val binding: ItemScheduleImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.empty_view)
                .error(R.drawable.empty_view)
                .into(binding.imageViewItemScheduleImage)
        }
    }
}