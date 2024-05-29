package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.RowScheduleMyBinding

class ScheduleMyAdapter:RecyclerView.Adapter<ScheduleMyAdapter.ScheduleMyViewHolder>(){
    inner class ScheduleMyViewHolder(binding: RowScheduleMyBinding) : RecyclerView.ViewHolder(binding.root){
        val binding : RowScheduleMyBinding
        init {
            this.binding = binding
            this.binding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleMyViewHolder {
        val binding = RowScheduleMyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val scheduleMyViewHolder = ScheduleMyViewHolder(binding)

        return scheduleMyViewHolder
    }

    override fun onBindViewHolder(holder: ScheduleMyViewHolder, position: Int) {
        if (position < 2) { // 다가오는 일정
            holder.binding.viewRowScheduleRemainingDeco.visibility = View.VISIBLE
            holder.binding.viewRowScheduleElapsedDeco.visibility = View.INVISIBLE
            holder.binding.textViewRowScheduleDDay.isEnabled = true
            holder.binding.buttonRowScheduleReview.visibility = View.GONE
        } else { // 다녀온 일정
            holder.binding.viewRowScheduleRemainingDeco.visibility = View.INVISIBLE
            holder.binding.viewRowScheduleElapsedDeco.visibility = View.VISIBLE
            holder.binding.textViewRowScheduleDDay.isEnabled = false
            holder.binding.buttonRowScheduleReview.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}