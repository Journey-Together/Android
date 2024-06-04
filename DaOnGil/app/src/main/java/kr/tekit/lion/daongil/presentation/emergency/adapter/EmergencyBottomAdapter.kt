package kr.tekit.lion.daongil.presentation.emergency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemEmergencyBottomBinding
import kr.tekit.lion.daongil.domain.model.EmergencyBottom

class EmergencyBottomAdapter(
    private val emergencyBottomList: List<EmergencyBottom>,
    private val itemClickListener: (String) -> Unit
):
    RecyclerView.Adapter<EmergencyBottomAdapter.EmergencyBottomViewHolder>(){
    class EmergencyBottomViewHolder(
        private val binding: ItemEmergencyBottomBinding,
        private val itemClickListener: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: EmergencyBottom){
                with(binding){
                    binding.emergencyName.text = item.emergencyName
                    binding.emergencyAddress.text = item.emergencyAddress
                    binding.emergencyCall.text = item.emergencyCall
                    binding.emergencyBed.text = item.emergencyBed
                    binding.emergencyDistance.text = item.emergencyDistance

                    root.setOnClickListener {
                        itemClickListener.invoke(item.emergencyId)
                    }
                }

                // 추후 type 별 이미지 설정, 병상 수와 병상수 이미지  설정 추가
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): EmergencyBottomViewHolder {
        return EmergencyBottomViewHolder(
            ItemEmergencyBottomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    }

    override fun getItemCount(): Int {
        return emergencyBottomList.size
    }

    override fun onBindViewHolder(
        holder: EmergencyBottomViewHolder, position: Int
    ) {
       holder.bind(emergencyBottomList[position])
    }

}