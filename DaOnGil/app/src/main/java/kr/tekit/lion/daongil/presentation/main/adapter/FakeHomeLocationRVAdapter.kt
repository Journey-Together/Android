package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemTouristBigBinding
import kr.tekit.lion.daongil.domain.model.FakeAroundPlace

class FakeHomeLocationRVAdapter(
    private val aroundPlaceList: List<FakeAroundPlace>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<FakeHomeLocationRVAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding: ItemTouristBigBinding = ItemTouristBigBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return LocationViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int = aroundPlaceList.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(aroundPlaceList[position])
    }

    class LocationViewHolder(
        val binding: ItemTouristBigBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick.invoke(adapterPosition)
            }
        }
        fun bind(aroundPlace: FakeAroundPlace) {
            binding.touristBigLocationTv.text = aroundPlace.address
            binding.touristBigTitleTv.text = aroundPlace.name

            Glide.with(binding.touristBigIv.context)
                .load(aroundPlace.image)
                .error(R.drawable.empty_view)
                .into(binding.touristBigIv)

            val disabilityList = aroundPlace.disability

            val disabilityRVAdapter = DisabilityRVAdapter(disabilityList)
            binding.touristBigDisabilityRv.adapter = disabilityRVAdapter
            binding.touristBigDisabilityRv.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

        }
    }
}