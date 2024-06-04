package kr.tekit.lion.daongil.presentation.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemScheduleBookmarkBinding
import kr.tekit.lion.daongil.domain.model.ScheduleBookmark

class ScheduleBookmarkRVAdapter(private val scheduleBookmarkList: List<ScheduleBookmark>, private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<ScheduleBookmarkRVAdapter.ScheduleBookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleBookmarkViewHolder {
        val binding : ItemScheduleBookmarkBinding = ItemScheduleBookmarkBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return ScheduleBookmarkViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = scheduleBookmarkList.size

    override fun onBindViewHolder(holder: ScheduleBookmarkViewHolder, position: Int) {
        holder.bind(scheduleBookmarkList[position])
    }

    class ScheduleBookmarkViewHolder(val binding: ItemScheduleBookmarkBinding, private val itemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleBookmark: ScheduleBookmark) {
            binding.textViewScheduleBookmarkTitle.text = scheduleBookmark.scheduleTitle
            binding.textViewScheduleBookmarkNickname.text = scheduleBookmark.userNickname
        }
    }
}