package kr.tekit.lion.daongil.presentation.schedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemScheduleListBinding
import kr.tekit.lion.daongil.domain.model.DailyPlan

class ScheduleListAdapter(
    private val dailyPlans: List<DailyPlan>,
    private val schedulePlaceListener: SchedulePlaceAdapter.OnSchedulePlaceClickListener
) : RecyclerView.Adapter<ScheduleListAdapter.ScheduleHeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScheduleHeaderViewHolder(
            ItemScheduleListBinding.inflate(
                inflater, parent, false
            ), schedulePlaceListener
        )
    }

    override fun onBindViewHolder(holder: ScheduleHeaderViewHolder, position: Int) {
        holder.bind(dailyPlans[position])
    }

    override fun getItemCount(): Int {
        return dailyPlans.size
    }

    class ScheduleHeaderViewHolder(
        private val binding: ItemScheduleListBinding,
        private val schedulePlaceListener: SchedulePlaceAdapter.OnSchedulePlaceClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dailyPlan: DailyPlan) {
            with(binding) {
                textViewItemSLDays.text = dailyPlan.dailyPlanDate.toString()
                rvItemScheduleList.adapter =
                    SchedulePlaceAdapter(dailyPlan.schedulePlaces, schedulePlaceListener)
            }
        }
    }
}