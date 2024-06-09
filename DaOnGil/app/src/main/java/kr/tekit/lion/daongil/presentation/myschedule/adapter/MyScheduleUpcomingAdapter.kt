package kr.tekit.lion.daongil.presentation.myschedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemMyScheduleUpcomingBinding
import kr.tekit.lion.daongil.domain.model.MySchedule

class MyScheduleUpcomingAdapter(private val mySchedules: List<MySchedule>, private val onScheduleItemClicked: (Int) -> Unit) : RecyclerView.Adapter<MyScheduleUpcomingAdapter.UpcomingScheduleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UpcomingScheduleViewHolder(ItemMyScheduleUpcomingBinding.inflate(inflater, parent, false), onScheduleItemClicked)
    }

    override fun onBindViewHolder(holder: UpcomingScheduleViewHolder, position: Int) {
        holder.bind(mySchedules[position])
    }

    override fun getItemCount(): Int {
        return mySchedules.size
    }

    class UpcomingScheduleViewHolder(private val binding : ItemMyScheduleUpcomingBinding, private val onScheduleItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root){
        fun bind(mySchedule: MySchedule){
            binding.apply {
                textViewMyScheduleUpcomingName.text = mySchedule.title
                textViewMyScheduleUpcomingDDay.text = mySchedule.remainDate
                //머지 후 수정할 부분
                //textViewMyScheduleUpcomingPeriod.text = itemView.context.getString(R.string.)
                textViewMyScheduleUpcomingPeriod.text =  "${mySchedule.startDate} - ${mySchedule.endDate}"
                Glide.with(itemView.context)
                    .load(mySchedule.imageUrl)
                    .placeholder(R.drawable.empty_view_small)
                    .error(R.drawable.empty_view_small)
                    .override(50, 50)
                    .into(binding.imageViewMyScheduleUpcoming)
                root.setOnClickListener {
                    onScheduleItemClicked(mySchedule.planId)
                }
            }
        }

    }
}