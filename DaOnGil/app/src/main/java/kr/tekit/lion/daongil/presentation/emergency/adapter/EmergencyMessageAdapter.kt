package kr.tekit.lion.daongil.presentation.emergency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemEmergencyMessageBinding
import kr.tekit.lion.daongil.domain.model.EmergencyMessageInfo

class EmergencyMessageAdapter(private val emergencyMessageList: List<EmergencyMessageInfo>) :
    RecyclerView.Adapter<EmergencyMessageAdapter.EmergencyMessageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmergencyMessageViewHolder {
        return EmergencyMessageViewHolder(
            ItemEmergencyMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: EmergencyMessageViewHolder,
        position: Int
    ) {
        holder.bind(emergencyMessageList[position])
    }

    override fun getItemCount(): Int {
        return emergencyMessageList.size
    }

    class EmergencyMessageViewHolder(private val binding: ItemEmergencyMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EmergencyMessageInfo) {
            binding.emergencyMessageText.text = item.emergencyMessage
            binding.emergencyMessageDate.text = item.emergencyDate
        }
    }

}