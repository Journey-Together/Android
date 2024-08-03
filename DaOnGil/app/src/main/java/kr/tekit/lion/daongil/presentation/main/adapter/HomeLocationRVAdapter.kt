package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemTouristLocationBinding
import kr.tekit.lion.daongil.domain.model.AroundPlace

class HomeLocationRVAdapter(
    private val aroundPlaceList: List<AroundPlace>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<HomeLocationRVAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding: ItemTouristLocationBinding = ItemTouristLocationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return LocationViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int = aroundPlaceList.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(aroundPlaceList[position])
    }

    class LocationViewHolder(
        val binding: ItemTouristLocationBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick.invoke(absoluteAdapterPosition)
            }
        }
        fun bind(aroundPlace: AroundPlace) {
            binding.touristLocationAddressTv.text = aroundPlace.address
            binding.touristLocationNameTv.text = aroundPlace.name

            Glide.with(binding.touristLocationIv.context)
                .load(aroundPlace.image)
                .error(R.drawable.empty_view)
                .into(binding.touristLocationIv)

            val disabilityList = aroundPlace.disability

            val disabilityRVAdapter = DisabilityRVAdapter(disabilityList)
            binding.touristLocationRv.adapter = disabilityRVAdapter
            binding.touristLocationRv.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

        }
    }
}