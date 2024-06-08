package kr.tekit.lion.daongil.presentation.myschedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemMyScheduleUpcomingBinding

class MyScheduleUpcomingAdapter : RecyclerView.Adapter<MyScheduleUpcomingAdapter.UpcomingScheduleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UpcomingScheduleViewHolder(ItemMyScheduleUpcomingBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UpcomingScheduleViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 3
    }

    class UpcomingScheduleViewHolder(private val binding : ItemMyScheduleUpcomingBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){
            binding.apply {
                textViewMyScheduleUpcomingName.text = "부산 무장애 여행"
                textViewMyScheduleUpcomingDDay.text = "D-10"
                textViewMyScheduleUpcomingPeriod.text = "2024.01.01 - 2024.01.05"
                // imageViewMyScheduleUpcoming -> Glide
            }
        }

    }
}