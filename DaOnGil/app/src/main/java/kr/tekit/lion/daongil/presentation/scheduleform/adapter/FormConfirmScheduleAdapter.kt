package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormConfirmScheduleBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule

class FormConfirmScheduleAdapter (private val dailyScheduleList: List<DailySchedule> )
    : RecyclerView.Adapter<FormConfirmScheduleAdapter.FormConfirmScheduleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormConfirmScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormConfirmScheduleViewHolder(
            ItemFormConfirmScheduleBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FormConfirmScheduleViewHolder, position: Int) {
        holder.bind(dailyScheduleList[position])
    }

    override fun getItemCount(): Int = dailyScheduleList.size

    class FormConfirmScheduleViewHolder(
        private val binding: ItemFormConfirmScheduleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind (dailySchedule: DailySchedule){
            binding.textViewFSCDate.text = dailySchedule.dailyDate

            val formConfirmPlaceAdapter = FormConfirmPlaceAdapter(
                dailySchedule.dailyPlaces
            )
            binding.recyclerViewFSCPlaces.adapter = formConfirmPlaceAdapter

            binding.viewFSCTopDeco.visibility = when (absoluteAdapterPosition) {
                0 -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
    }
}