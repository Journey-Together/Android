package kr.tekit.lion.daongil.presentation.emergency.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemEmergencyBottomBinding
import kr.tekit.lion.daongil.domain.model.PharmacyMapInfo

class PharmacyBottomAdapter(
    private val pharmacyBottomList: List<PharmacyMapInfo>,
    private val itemClickListener: (PharmacyMapInfo) -> Unit
) : RecyclerView.Adapter<PharmacyBottomAdapter.PharmacyBottomViewHolder>(){
    class PharmacyBottomViewHolder(
        private val binding: ItemEmergencyBottomBinding,
        private val itemClickListener: (PharmacyMapInfo) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PharmacyMapInfo) {
            with(binding) {
                binding.emergencyBottomImage.setImageResource(R.drawable.pharmacy_bottom_img)
                binding.emergencyName.text = item.pharmacyName
                binding.emergencyAddress.text = item.pharmacyAddress
                binding.emergencyCall.text = item.pharmacyTel
                root.setOnClickListener {
                    itemClickListener.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): PharmacyBottomViewHolder {
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
        holder:  PharmacyBottomViewHolder, position: Int
    ) {
        holder.bind(pharmacyBottomList[position])
    }

}