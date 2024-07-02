package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemDisabilityTypeBinding

class DisabilityRVAdapter(private val typeList : List<String>)
    : RecyclerView.Adapter<DisabilityRVAdapter.DisabilityViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisabilityViewHolder {
        val binding : ItemDisabilityTypeBinding = ItemDisabilityTypeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return DisabilityViewHolder(binding)
    }

    override fun getItemCount(): Int = typeList.size

    override fun onBindViewHolder(holder: DisabilityViewHolder, position: Int) {
        holder.bind(typeList[position])
    }

    class DisabilityViewHolder(val binding: ItemDisabilityTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String) {
            val typeId = when(item) {
                "1" -> R.drawable.physical_disability_radius_icon
                "2" -> R.drawable.visual_impairment_radius_icon
                "3" -> R.drawable.hearing_impairment_radius_icon
                "4" -> R.drawable.infant_familly_radius_icon
                else -> R.drawable.elderly_people_radius_icon
            }
            binding.itemDisabilityTypeIv.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context, typeId
                )
            )
        }
    }
}