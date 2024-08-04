package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemFormPlaceBinding
import kr.tekit.lion.daongil.domain.model.FormPlace

class FormConfirmPlaceAdapter(private val places: List<FormPlace>)
    : RecyclerView.Adapter<FormConfirmPlaceAdapter.FormConfirmPlaceViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormConfirmPlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormConfirmPlaceViewHolder(
            ItemFormPlaceBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FormConfirmPlaceViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    class FormConfirmPlaceViewHolder(private val binding: ItemFormPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(place: FormPlace) {
                binding.apply {
                    imageButtonFPlaceRemove.visibility = View.GONE

                    textViewFPlaceCategory.text = place.placeCategory
                    textViewFPlaceName.text = place.placeName

                    place.placeImage?.let {
                        Glide.with(imageViewFPlaceThumbnail.context)
                            .load(it)
                            .placeholder(R.drawable.empty_view)
                            .error(R.drawable.empty_view)
                            .into(imageViewFPlaceThumbnail)
                    }
                }
            }
    }
}