package kr.tekit.lion.daongil.presentation.main.customview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemEmergencyBottomBinding
import kr.tekit.lion.daongil.domain.model.FakeAroundPlace

class FakeBottomAdapter(
    private val pharmacyBottomList: List<FakeAroundPlace>,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<FakeBottomAdapter.PharmacyBottomViewHolder>() {
    class PharmacyBottomViewHolder(
        private val binding: ItemEmergencyBottomBinding,
        private val itemClickListener: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener.invoke(adapterPosition)
            }
        }

        fun bind(item: FakeAroundPlace) {
            binding.emergencyBottomImage.setImageResource(item.image)
            binding.emergencyName.text = item.name
            binding.emergencyAddress.text = item.address
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PharmacyBottomViewHolder {
        return PharmacyBottomViewHolder(
            ItemEmergencyBottomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    }

    override fun getItemCount(): Int {
        return pharmacyBottomList.size
    }

    override fun onBindViewHolder(
        holder: PharmacyBottomViewHolder, position: Int
    ) {
        holder.bind(pharmacyBottomList[position])
    }

}