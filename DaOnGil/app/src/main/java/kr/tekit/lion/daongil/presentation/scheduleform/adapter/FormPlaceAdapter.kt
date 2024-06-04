package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormPlaceBinding
import kr.tekit.lion.daongil.domain.model.FormPlace

class FormPlaceAdapter(private val places : MutableList<FormPlace>) : RecyclerView.Adapter<FormPlaceAdapter.FormPlaceViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormPlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormPlaceViewHolder(ItemFormPlaceBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FormPlaceViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int {
        return places.size
    }

    fun addNewPlace(addedPlace : FormPlace){
        places.add(addedPlace)
    }

    class FormPlaceViewHolder(private val binding: ItemFormPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place : FormPlace) {
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
        }

    }

}