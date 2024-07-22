package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemFormPlaceBinding
import kr.tekit.lion.daongil.domain.model.FormPlace

class FormPlaceAdapter(
    private val places: List<FormPlace>,
    private val schedulePosition: Int,
    private val onItemClickListener: (schedulePosition: Int, placePosition: Int) -> Unit,
    private val onRemoveButtonClick: (schedulePosition: Int, placePosition: Int) -> Unit
) : RecyclerView.Adapter<FormPlaceAdapter.FormPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormPlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormPlaceViewHolder(
            ItemFormPlaceBinding.inflate(inflater, parent, false),
            schedulePosition,
            onItemClickListener,
            onRemoveButtonClick
        )
    }

    override fun onBindViewHolder(holder: FormPlaceViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int {
        return places.size
    }

    class FormPlaceViewHolder(
        private val binding: ItemFormPlaceBinding,
        private val schedulePosition: Int,
        private val onItemClickListener: (schedulePosition: Int, placePosition: Int) -> Unit,
        private val onRemoveItemClick: (schedulePosition: Int, placePosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageButtonFPlaceRemove.setOnClickListener {
                onRemoveItemClick(schedulePosition, absoluteAdapterPosition)
            }
            binding.cardViewFPlace.setOnClickListener {
                onItemClickListener(schedulePosition, absoluteAdapterPosition)
            }
        }

        fun bind(place: FormPlace) {
            binding.apply {
                textViewFPlaceCategory.text = place.placeCategory
                textViewFPlaceName.text = place.placeName

                //initDisabilityIcons()
//                place.placeDisability.forEach {
//                    when (it) {
//                        1 -> iconFPlacePhysicalDisability.visibility = View.VISIBLE
//                        2 -> iconFPlaceVisualImpair.visibility = View.VISIBLE
//                        3 -> iconFPlaceHearingImpair.visibility = View.VISIBLE
//                        4 -> iconFPlaceInfantFamily.visibility = View.VISIBLE
//                        5 -> iconFPlaceElderlyPeople.visibility = View.VISIBLE
//                    }
//                }

                place.placeImage?.let {
                    Glide.with(imageViewFPlaceThumbnail.context)
                        .load(it)
                        .placeholder(R.drawable.empty_view)
                        .error(R.drawable.empty_view)
                        .into(imageViewFPlaceThumbnail)
                }
            }
        }

//        private fun initDisabilityIcons(){
//            binding.apply {
//                iconFPlacePhysicalDisability.visibility = View.GONE
//                iconFPlaceVisualImpair.visibility = View.GONE
//                iconFPlaceHearingImpair.visibility = View.GONE
//                iconFPlaceInfantFamily.visibility = View.GONE
//                iconFPlaceElderlyPeople.visibility = View.GONE
//            }
//        }
    }
}