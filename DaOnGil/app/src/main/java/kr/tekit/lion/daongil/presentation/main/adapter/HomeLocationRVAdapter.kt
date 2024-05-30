package kr.tekit.lion.daongil.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemTouristBigBinding
import kr.tekit.lion.daongil.domain.model.Tourist

class HomeLocationRVAdapter(private val touristList: List<Tourist>, val context: Context) : RecyclerView.Adapter<HomeLocationRVAdapter.LocationViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding : ItemTouristBigBinding = ItemTouristBigBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return LocationViewHolder(binding, context)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(touristList[position])
    }

    class LocationViewHolder(val binding: ItemTouristBigBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tourist : Tourist) {
            binding.touristBigLocationTv.text = tourist.touristLocation
            binding.touristBigTitleTv.text = tourist.touristName

            val disabilityList = tourist.disabilityType

            val disabilityRVAdapter = DisabilityRVAdapter(disabilityList, context)
            binding.touristBigDisabilityRv.adapter = disabilityRVAdapter
            binding.touristBigDisabilityRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}