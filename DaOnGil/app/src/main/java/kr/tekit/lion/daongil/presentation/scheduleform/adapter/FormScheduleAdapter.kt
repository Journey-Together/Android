package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormScheduleBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace

class FormScheduleAdapter(private val dailyScheduleList: List<DailySchedule>, private val context: Context) : RecyclerView.Adapter<FormScheduleAdapter.FormScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormScheduleViewHolder(ItemFormScheduleBinding.inflate(inflater, parent, false), context)
    }

    override fun onBindViewHolder(holder: FormScheduleViewHolder, position: Int) {
        holder.bind(dailyScheduleList[position])
    }

    override fun getItemCount(): Int {
        return dailyScheduleList.size
    }

    class FormScheduleViewHolder(private val binding: ItemFormScheduleBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){
        fun bind(dailySchedule: DailySchedule) {
            binding.textViewFScheduleDate.text = dailySchedule.dailyDate

            val formPlaceAdapter = FormPlaceAdapter(dailySchedule.dailyPlaces)
            binding.recyclerViewFSchedulePlaces.adapter = formPlaceAdapter
            binding.recyclerViewFSchedulePlaces.layoutManager = LinearLayoutManager(context)

            binding.buttonFScheduleAddPlace.setOnClickListener {
                // to do - 검색 화면에서 선택한 장소 정보를 서버에서 가져와서 리스트에 추가할 것
                val addedPlace = FormPlace(0, "", "", "", listOf(1, 2, 3))
                formPlaceAdapter.addNewPlace(addedPlace)
                formPlaceAdapter.notifyItemInserted(dailySchedule.dailyPlaces.size-1)
            }
        }
    }
}