package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormScheduleBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.presentation.scheduleform.fragment.ScheduleDetailsFormFragmentDirections
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel

class FormScheduleAdapter(
    private val dailyScheduleList: List<DailySchedule>, private val context: Context,
    private val navController: NavController, private val scheduleViewModel: ScheduleFormViewModel
) : RecyclerView.Adapter<FormScheduleAdapter.FormScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormScheduleViewHolder(ItemFormScheduleBinding.inflate(inflater, parent, false), context, navController, scheduleViewModel)
    }

    override fun onBindViewHolder(holder: FormScheduleViewHolder, position: Int) {
        holder.bind(dailyScheduleList[position], position)
    }

    override fun getItemCount(): Int {
        return dailyScheduleList.size
    }

    class FormScheduleViewHolder(
        private val binding: ItemFormScheduleBinding, private val context: Context,
        private val navController: NavController, private val scheduleViewModel: ScheduleFormViewModel
    )
        : RecyclerView.ViewHolder(binding.root){
        fun bind(dailySchedule: DailySchedule, schedulePosition:Int) {
            binding.textViewFScheduleDate.text = dailySchedule.dailyDate

            val formPlaceAdapter = FormPlaceAdapter(dailySchedule.dailyPlaces, scheduleViewModel, schedulePosition)
            binding.recyclerViewFSchedulePlaces.adapter = formPlaceAdapter
            binding.recyclerViewFSchedulePlaces.layoutManager = LinearLayoutManager(context)

            binding.buttonFScheduleAddPlace.setOnClickListener {
                // 몇 번째 일정에 여행지를 추가하는지 파악하기 위해 schedulePosition 을 전달해준다.
                val action = ScheduleDetailsFormFragmentDirections.actionScheduleDetailsFormFragmentToFormSearchFragment(schedulePosition)
                navController.navigate(action)
            }
        }
    }
}