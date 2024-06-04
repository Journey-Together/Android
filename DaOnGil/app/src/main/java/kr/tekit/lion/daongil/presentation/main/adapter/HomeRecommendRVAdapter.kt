package kr.tekit.lion.daongil.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemTouristSmallBinding
import kr.tekit.lion.daongil.domain.model.Tourist

class HomeRecommendRVAdapter(private val touristList: List<Tourist>, val context: Context) : RecyclerView.Adapter<HomeRecommendRVAdapter.RecommendViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val binding : ItemTouristSmallBinding = ItemTouristSmallBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return RecommendViewHolder(binding, context)
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.bind(touristList[position])
    }

    class RecommendViewHolder(val binding: ItemTouristSmallBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tourist: Tourist) {
            binding.touristSmallLocationTv.text = tourist.touristLocation
            binding.touristSmallTitleTv.text = tourist.touristName

            val disabilityList = tourist.disabilityType

            val disabilityRVAdapter = DisabilityRVAdapter(disabilityList, context)
            binding.touristSmallDisabilityRv.adapter = disabilityRVAdapter
            binding.touristSmallDisabilityRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}