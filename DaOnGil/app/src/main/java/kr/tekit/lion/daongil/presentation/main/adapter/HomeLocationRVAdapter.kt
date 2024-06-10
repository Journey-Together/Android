package kr.tekit.lion.daongil.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemTouristBigBinding
import kr.tekit.lion.daongil.domain.model.AroundPlace

class HomeLocationRVAdapter(private val aroundPlaceList: List<AroundPlace>, val context: Context) : RecyclerView.Adapter<HomeLocationRVAdapter.LocationViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding : ItemTouristBigBinding = ItemTouristBigBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return LocationViewHolder(binding, context)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(aroundPlaceList[position])
    }

    class LocationViewHolder(val binding: ItemTouristBigBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aroundPlace : AroundPlace) {
            binding.touristBigLocationTv.text = aroundPlace.address
            binding.touristBigTitleTv.text = aroundPlace.name

            Glide.with(binding.touristBigIv.context)
                .load(aroundPlace.image)
                .error(R.drawable.empty_view)
                .into(binding.touristBigIv)

            val disabilityList = aroundPlace.disability

            val disabilityRVAdapter = DisabilityRVAdapter(disabilityList, context)
            binding.touristBigDisabilityRv.adapter = disabilityRVAdapter
            binding.touristBigDisabilityRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}