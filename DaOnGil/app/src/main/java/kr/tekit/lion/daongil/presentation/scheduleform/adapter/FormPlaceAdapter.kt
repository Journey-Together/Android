package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormPlaceBinding
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel

class FormPlaceAdapter(
    private val places : MutableList<FormPlace>,
    private val scheduleViewModel: ScheduleFormViewModel, private val schedulePosition: Int
) : RecyclerView.Adapter<FormPlaceAdapter.FormPlaceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormPlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormPlaceViewHolder(ItemFormPlaceBinding.inflate(inflater, parent, false), scheduleViewModel, schedulePosition)
    }

    override fun onBindViewHolder(holder: FormPlaceViewHolder, position: Int) {
        holder.bind(places[position], position)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    fun addNewPlace(addedPlace : FormPlace){
        places.add(addedPlace)
    }

    class FormPlaceViewHolder(
        private val binding: ItemFormPlaceBinding, private val scheduleViewModel: ScheduleFormViewModel,
        private val schedulePosition: Int
        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place : FormPlace, placePosition: Int) {
            // to do : 이미지 url -> Glide
            // binding.imageViewFPlaceThumbnail
             binding.textViewFPlaceAddr.text = place.placeAddress
             binding.textViewFPlaceName.text = place.placeName
            place.placeDisability.forEach {
                when(it){
                    1 -> binding.iconFPlacePhysicalDisability.visibility = View.VISIBLE
                    2 -> binding.iconFPlaceVisualImpair.visibility = View.VISIBLE
                    // 3 - 청각 아이콘 추가되면 코드 추가할 예정
                    4 -> binding.iconFPlaceInfantFamily.visibility = View.VISIBLE
                    5 -> binding.iconFPlaceElderlyPeople.visibility = View.VISIBLE
                }
            }
            binding.imageButtonFPlaceRemove.setOnClickListener {
                // viewModel에서 해당 place 제거
                scheduleViewModel.removePlace(schedulePosition, placePosition)

            }
        }

    }

}