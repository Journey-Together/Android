package kr.tekit.lion.daongil.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemDetailServiceInfoBinding
import kr.tekit.lion.daongil.domain.model.SubDisability

class DetailInfoRVAdapter(private val infoList: List<SubDisability>) :
    RecyclerView.Adapter<DetailInfoRVAdapter.DetailInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailInfoViewHolder {
        val binding: ItemDetailServiceInfoBinding = ItemDetailServiceInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return DetailInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = infoList.size

    override fun onBindViewHolder(holder: DetailInfoViewHolder, position: Int) {
        holder.bind(infoList[position])
    }

    class DetailInfoViewHolder(private val binding: ItemDetailServiceInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(infoData: SubDisability) {
            binding.itemDetailInfoTitleTv.text = infoData.subDisabilityName
            binding.itemDetailInfoContentTv.text = infoData.description
        }
    }
}