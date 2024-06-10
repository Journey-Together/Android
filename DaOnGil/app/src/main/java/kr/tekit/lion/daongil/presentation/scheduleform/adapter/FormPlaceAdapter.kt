package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormPlaceBinding
import kr.tekit.lion.daongil.domain.model.FormPlace

class FormPlaceAdapter(
    private val places: List<FormPlace>,
    private val schedulePosition: Int,
    private val onRemoveButtonClick: (schedulePosition: Int, placePosition: Int) -> Unit
) : RecyclerView.Adapter<FormPlaceAdapter.FormPlaceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormPlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormPlaceViewHolder(
            ItemFormPlaceBinding.inflate(inflater, parent, false),
            schedulePosition,
            onRemoveItemClick = { schedulePosition, placePosition ->
                onRemoveButtonClick(schedulePosition,placePosition)
            }
        )
    }

    override fun onBindViewHolder(holder: FormPlaceViewHolder, position: Int) {
        holder.bind(places[position], position)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    class FormPlaceViewHolder(
        private val binding: ItemFormPlaceBinding,
        private val schedulePosition: Int,
        private val onRemoveItemClick: (schedulePosition: Int, placePosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: FormPlace, placePosition: Int) {
            // to do : 이미지 url -> Glide
            // binding.imageViewFPlaceThumbnail
            binding.textViewFPlaceAddr.text = place.placeAddress
            binding.textViewFPlaceName.text = place.placeName
            place.placeDisability.forEach {
                when (it) {
                    1 -> binding.iconFPlacePhysicalDisability.visibility = View.VISIBLE
                    2 -> binding.iconFPlaceVisualImpair.visibility = View.VISIBLE
                    3 -> binding.iconFPlaceHearingImpair.visibility = View.VISIBLE
                    4 -> binding.iconFPlaceInfantFamily.visibility = View.VISIBLE
                    5 -> binding.iconFPlaceElderlyPeople.visibility = View.VISIBLE
                }
            }
            binding.imageButtonFPlaceRemove.setOnClickListener {
                onRemoveItemClick(schedulePosition, placePosition)
            }
        }
    }
}