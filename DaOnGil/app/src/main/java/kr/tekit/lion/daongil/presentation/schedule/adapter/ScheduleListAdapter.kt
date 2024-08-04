package kr.tekit.lion.daongil.presentation.schedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemScheduleListBinding
import kr.tekit.lion.daongil.domain.model.DailyPlan

class ScheduleListAdapter(
    private val dailyPlans: List<DailyPlan>,
    private val scheduleListListener: (Int, Int) -> Unit
) : RecyclerView.Adapter<ScheduleListAdapter.ScheduleHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScheduleHeaderViewHolder(
            ItemScheduleListBinding.inflate(
                inflater, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ScheduleHeaderViewHolder, position: Int) {
        holder.bind(dailyPlans[position], scheduleListListener)
    }

    override fun getItemCount(): Int {
        return dailyPlans.size
    }

    class ScheduleHeaderViewHolder(
        private val binding: ItemScheduleListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dailyPlan: DailyPlan, scheduleListListener: (Int, Int) -> Unit) {
            with(binding) {
                textViewItemDay.text = "Day ${absoluteAdapterPosition + 1}"
                textViewItemDate.text = dailyPlan.dailyPlanDate
                rvItemScheduleList.adapter = SchedulePlaceAdapter(dailyPlan.schedulePlaces) { childPosition ->
                    scheduleListListener(absoluteAdapterPosition, childPosition)
                }
            }
        }
    }
}