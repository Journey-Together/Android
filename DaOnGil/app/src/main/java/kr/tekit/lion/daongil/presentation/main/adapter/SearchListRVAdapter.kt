package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemTouristMediumBinding
import kr.tekit.lion.daongil.domain.model.AroundPlace


class SearchListRVAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<AroundPlace, SearchListRVAdapter.LocationViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = ItemTouristMediumBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return LocationViewHolder(inflater, onClick)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class LocationViewHolder(
        val binding: ItemTouristMediumBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick.invoke(absoluteAdapterPosition)
            }
        }

        fun bind(item: AroundPlace) {
            with(binding) {
                touristBigLocationTv.text = item.address
                touristBigTitleTv.text = item.name

                Glide.with(binding.touristBigIv.context)
                    .load(item.image)
                    .error(R.drawable.empty_view)
                    .into(touristBigIv)

                val disabilityList = item.disability

                val disabilityRVAdapter = DisabilityRVAdapter(disabilityList)
                touristBigDisabilityRv.adapter = disabilityRVAdapter
                touristBigDisabilityRv.layoutManager = LinearLayoutManager(
                    root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AroundPlace>() {
            override fun areItemsTheSame(oldItem: AroundPlace, newItem: AroundPlace): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(oldItem: AroundPlace, newItem: AroundPlace): Boolean {
                return oldItem == newItem
            }

        }
    }
}