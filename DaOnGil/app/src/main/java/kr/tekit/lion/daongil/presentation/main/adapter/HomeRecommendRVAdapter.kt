package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemTouristSmallBinding
import kr.tekit.lion.daongil.domain.model.RecommendPlace

class HomeRecommendRVAdapter(
    private val recommendPlaceList: List<RecommendPlace>,
    private val onClick: (RecommendPlace) -> Unit
) : RecyclerView.Adapter<HomeRecommendRVAdapter.RecommendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val binding: ItemTouristSmallBinding = ItemTouristSmallBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return RecommendViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.bind(recommendPlaceList[position])
    }

    class RecommendViewHolder(
        val binding: ItemTouristSmallBinding,
        private val onClick: (RecommendPlace) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendPlace: RecommendPlace) {
            binding.touristSmallLocationTv.text = recommendPlace.address
            binding.touristSmallTitleTv.text = recommendPlace.name

            val disabilityList = recommendPlace.disability

            Glide.with(binding.touristSmallIv.context)
                .load(recommendPlace.image)
                .error(R.drawable.empty_view_small)
                .into(binding.touristSmallIv)

            val disabilityRVAdapter = DisabilityRVAdapter(disabilityList)
            binding.touristSmallDisabilityRv.adapter = disabilityRVAdapter
            binding.touristSmallDisabilityRv.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            binding.root.setOnClickListener {
                onClick(recommendPlace)
            }
        }
    }
}