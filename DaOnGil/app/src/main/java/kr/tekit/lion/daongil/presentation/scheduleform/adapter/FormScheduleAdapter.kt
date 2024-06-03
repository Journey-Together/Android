package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemFormScheduleBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace

class FormScheduleAdapter(private val dailyScheduleList: List<DailySchedule>, private val context: Context, private val navController: NavController) : RecyclerView.Adapter<FormScheduleAdapter.FormScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormScheduleViewHolder(ItemFormScheduleBinding.inflate(inflater, parent, false), context, navController)
    }

    override fun onBindViewHolder(holder: FormScheduleViewHolder, position: Int) {
        holder.bind(dailyScheduleList[position])
    }

    override fun getItemCount(): Int {
        return dailyScheduleList.size
    }

    class FormScheduleViewHolder(
        private val binding: ItemFormScheduleBinding, private val context: Context, private val navController: NavController)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(dailySchedule: DailySchedule) {
            binding.textViewFScheduleDate.text = dailySchedule.dailyDate

            val formPlaceAdapter = FormPlaceAdapter(dailySchedule.dailyPlaces)
            binding.recyclerViewFSchedulePlaces.adapter = formPlaceAdapter
            binding.recyclerViewFSchedulePlaces.layoutManager = LinearLayoutManager(context)

            binding.buttonFScheduleAddPlace.setOnClickListener {
                navController.navigate(R.id.formSearchFragment)

/*                // to do - 검색 화면에서 선택한 장소 정보를 서버에서 가져와서 리스트에 추가할 것
                val addedPlace = FormPlace(0, "", "", "", listOf(1, 2, 4, 5))
                formPlaceAdapter.addNewPlace(addedPlace)
                formPlaceAdapter.notifyItemInserted(dailySchedule.dailyPlaces.size-1)*/
            }
        }
    }
}