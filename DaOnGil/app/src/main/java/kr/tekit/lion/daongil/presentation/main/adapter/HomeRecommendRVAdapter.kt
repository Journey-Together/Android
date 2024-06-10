package kr.tekit.lion.daongil.presentation.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemTouristSmallBinding
import kr.tekit.lion.daongil.domain.model.RecommendPlace
import kr.tekit.lion.daongil.domain.model.Tourist

class HomeRecommendRVAdapter(private val recommendPlaceList: List<RecommendPlace>, val context: Context, private val listener: OnRecommendClickListener) : RecyclerView.Adapter<HomeRecommendRVAdapter.RecommendViewHolder>(){

    interface OnRecommendClickListener {
        fun onRecommendClicked(recommendPlace: RecommendPlace)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val binding : ItemTouristSmallBinding = ItemTouristSmallBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return RecommendViewHolder(binding, context, listener)
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.bind(recommendPlaceList[position])
    }

    class RecommendViewHolder(val binding: ItemTouristSmallBinding, val context: Context, private val listener : OnRecommendClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(recommendPlace: RecommendPlace) {
            binding.touristSmallLocationTv.text = recommendPlace.address
            binding.touristSmallTitleTv.text = recommendPlace.name

            val disabilityList = recommendPlace.disability

            Glide.with(binding.touristSmallIv.context)
                .load(recommendPlace.image)
                .error(R.drawable.empty_view)
                .into(binding.touristSmallIv)

            val disabilityRVAdapter = DisabilityRVAdapter(disabilityList, context)
            binding.touristSmallDisabilityRv.adapter = disabilityRVAdapter
            binding.touristSmallDisabilityRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            binding.root.setOnClickListener {
                listener.onRecommendClicked(recommendPlace)
            }
        }

        override fun onClick(v: View?) {
            listener
        }
    }
}