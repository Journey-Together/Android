package kr.tekit.lion.daongil.presentation.emergency.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemEmergencyBottomBinding
import kr.tekit.lion.daongil.domain.model.EmergencyBottom

class EmergencyBottomAdapter(
    private val emergencyBottomList: List<EmergencyBottom>,
    private val itemClickListener: (String, EmergencyBottom) -> Unit
):
    RecyclerView.Adapter<EmergencyBottomAdapter.EmergencyBottomViewHolder>(){
    class EmergencyBottomViewHolder(
        private val binding: ItemEmergencyBottomBinding,
        private val itemClickListener: (String, EmergencyBottom) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: EmergencyBottom){
                with(binding){
                    if(item.emergencyType == "emergency"){
                        binding.emergencyBed.visibility = View.VISIBLE
                        binding.emergencyName.text = item.emergencyList.hospitalName
                        binding.emergencyAddress.text = item.emergencyList.hospitalAddress
                        binding.emergencyCall.text = item.emergencyList.emergencyTel
                        binding.emergencyBed.text =
                            item.emergencyList.emergencyCount.toString() + " / " + item.emergencyList.emergencyAllCount.toString()

                        item.emergencyList.emergencyBed?.let { it ->
                            val emergencyBedIcon = binding.emergencyBedIcon

                            val drawable = emergencyBedIcon.drawable
                            if (drawable != null) {
                                val color = when {
                                    it >= 80 -> Color.parseColor("#008000") // Green
                                    it >= 50 -> Color.parseColor("#FFFF00") // Yellow
                                    else -> Color.parseColor("#FF0000") // Red
                                }
                                drawable.setTint(color)
                            }
                        }

                    }

                    root.setOnClickListener {
                        itemClickListener.invoke(item.emergencyId, item)
                    }
                }
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