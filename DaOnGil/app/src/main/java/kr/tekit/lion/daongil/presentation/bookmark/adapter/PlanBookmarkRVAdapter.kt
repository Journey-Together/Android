package kr.tekit.lion.daongil.presentation.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemScheduleBookmarkBinding
import kr.tekit.lion.daongil.domain.model.PlanBookmark

class PlanBookmarkRVAdapter(private val planBookmarkList: List<PlanBookmark>, private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<PlanBookmarkRVAdapter.PlanBookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanBookmarkViewHolder {
        val binding : ItemScheduleBookmarkBinding = ItemScheduleBookmarkBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return PlanBookmarkViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = planBookmarkList.size

    override fun onBindViewHolder(holder: PlanBookmarkViewHolder, position: Int) {
        holder.bind(planBookmarkList[position])
    }

    class PlanBookmarkViewHolder(val binding: ItemScheduleBookmarkBinding, private val itemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planBookmark: PlanBookmark) {
            binding.textViewScheduleBookmarkTitle.text = planBookmark.title
            binding.textViewScheduleBookmarkNickname.text = planBookmark.name
        }
    }
}