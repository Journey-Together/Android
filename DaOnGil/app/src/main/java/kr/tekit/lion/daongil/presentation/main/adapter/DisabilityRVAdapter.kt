package kr.tekit.lion.daongil.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemDisabilityTypeBinding

class DisabilityRVAdapter(var typeList : List<String>, val context: Context) : RecyclerView.Adapter<DisabilityRVAdapter.DisabilityViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisabilityViewHolder {
        val binding : ItemDisabilityTypeBinding = ItemDisabilityTypeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return DisabilityViewHolder(binding, context)
    }

    override fun getItemCount(): Int = typeList.size

    override fun onBindViewHolder(holder: DisabilityViewHolder, position: Int) {
        holder.bind(typeList[position])
    }

    class DisabilityViewHolder(val binding: ItemDisabilityTypeBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String) {
            val typeId = when(item) {
                "physical_disability" -> R.drawable.physical_disability_radius_icon
                "visual_impairment" -> R.drawable.visual_impairment_radius_icon
                "infant_family" -> R.drawable.infant_familly_radius_icon
                "elderly_people" -> R.drawable.elderly_people_radius_icon
                else -> R.drawable.hearing_impairment_icon
            }
            binding.itemDisabilityTypeIv.setImageDrawable(
                ContextCompat.getDrawable(
                    context, typeId
                )
            )
        }
    }
}