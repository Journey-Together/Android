package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemFormScheduleBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.presentation.scheduleform.fragment.ScheduleDetailsFormFragment
import kr.tekit.lion.daongil.presentation.scheduleform.fragment.ScheduleDetailsFormFragmentDirections

class FormScheduleAdapter(private val dailyScheduleList: List<DailySchedule>, private val context: Context, val navController: NavController) : RecyclerView.Adapter<FormScheduleAdapter.FormScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormScheduleViewHolder(ItemFormScheduleBinding.inflate(inflater, parent, false), context, navController)
    }

    override fun onBindViewHolder(holder: FormScheduleViewHolder, position: Int) {
        holder.bind(dailyScheduleList[position], position)
    }

    override fun getItemCount(): Int {
        return dailyScheduleList.size
    }

    class FormScheduleViewHolder(
        private val binding: ItemFormScheduleBinding, private val context: Context, val navController: NavController
    )
        : RecyclerView.ViewHolder(binding.root){
        fun bind(dailySchedule: DailySchedule, schedulePosition:Int) {
            binding.textViewFScheduleDate.text = dailySchedule.dailyDate

            val formPlaceAdapter = FormPlaceAdapter(dailySchedule.dailyPlaces)
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