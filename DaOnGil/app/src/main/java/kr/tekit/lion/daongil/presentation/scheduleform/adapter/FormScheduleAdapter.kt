package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormScheduleBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule

class FormScheduleAdapter(
    private val dailyScheduleList: List<DailySchedule>,
    private val onAddButtonClickListener : (schedulePosition: Int) -> Unit,
    private val onItemClickListener: (schedulePosition: Int, placePosition: Int) -> Unit,
    private val onRemoveButtonClickListener : (schedulePosition: Int, placePosition: Int) -> Unit
) : RecyclerView.Adapter<FormScheduleAdapter.FormScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormScheduleViewHolder(
            ItemFormScheduleBinding.inflate(inflater, parent, false),
            onAddButtonClickListener,
            onItemClickListener,
            onRemoveButtonClickListener
        )
    }

    override fun onBindViewHolder(holder: FormScheduleViewHolder, position: Int) {
        holder.bind(dailyScheduleList[position])
    }

    override fun getItemCount(): Int {
        return dailyScheduleList.size
    }

    class FormScheduleViewHolder(
        private val binding: ItemFormScheduleBinding,
        private val onAddButtonClickListener : (schedulePosition: Int) -> Unit,
        private val onItemClickListener: (schedulePosition: Int, placePosition: Int) -> Unit,
        private val onRemoveButtonClickListener : (schedulePosition: Int, placePosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            // 여행지 추가 버튼
            binding.buttonFScheduleAddPlace.setOnClickListener {
                onAddButtonClickListener(absoluteAdapterPosition)
            }
        }

        fun bind(dailySchedule: DailySchedule) {
            binding.textViewFScheduleDate.text = dailySchedule.dailyDate

            // 추가할 여행지목록 Adapter
            val formPlaceAdapter = FormPlaceAdapter(
                dailySchedule.dailyPlaces,
                absoluteAdapterPosition,
                onItemClickListener,
                onRemoveButtonClickListener
            )
            binding.recyclerViewFSchedulePlaces.adapter = formPlaceAdapter

            binding.viewFScheduleTopDeco.visibility = when (absoluteAdapterPosition) {
                0 -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
    }
}