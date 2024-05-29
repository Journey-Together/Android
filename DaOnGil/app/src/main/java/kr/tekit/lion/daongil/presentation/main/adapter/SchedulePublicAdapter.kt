package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.RowSchedulePublicBinding

class SchedulePublicAdapter :
    RecyclerView.Adapter<SchedulePublicAdapter.SchedulePublicViewHolder>() {

    inner class SchedulePublicViewHolder(binding: RowSchedulePublicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: RowSchedulePublicBinding

        init {
            this.binding = binding
            this.binding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedulePublicViewHolder {
        val binding = RowSchedulePublicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val schedulePublicViewHolder = SchedulePublicViewHolder(binding)

        return schedulePublicViewHolder
    }

    override fun onBindViewHolder(holder: SchedulePublicViewHolder, position: Int) {
        // "Not yet implemented"
    }

    override fun getItemCount(): Int {
        return 5
    }
}