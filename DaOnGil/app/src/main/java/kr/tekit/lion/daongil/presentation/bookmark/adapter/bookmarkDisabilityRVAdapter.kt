package kr.tekit.lion.daongil.presentation.bookmark.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemDisabilityTypeBinding

class bookmarkDisabilityRVAdapter(var typeList : List<String>, val context: Context) : RecyclerView.Adapter<bookmarkDisabilityRVAdapter.bookmarkDisabilityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookmarkDisabilityViewHolder {
        val binding : ItemDisabilityTypeBinding = ItemDisabilityTypeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return bookmarkDisabilityViewHolder(binding, context)
    }

    override fun getItemCount(): Int = typeList.size

    override fun onBindViewHolder(holder: bookmarkDisabilityViewHolder, position: Int) {
        holder.bind(typeList[position])
    }

    class bookmarkDisabilityViewHolder(val binding: ItemDisabilityTypeBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String) {
            val typeId = when(item) {
                "physical_disability" -> R.drawable.physical_disability_radius_icon
                "visual_impairment" -> R.drawable.visual_impairment_radius_icon
                "infant_family" -> R.drawable.infant_familly_radius_icon
                "elderly_people" -> R.drawable.elderly_people_radius_icon
                else -> R.drawable.hearing_impairment_icon
            }
            binding.itemDisabilityTypeIv.setImageDrawable(
                ContextCompat.getDrawable(context, typeId)
            )
        }
    }

}