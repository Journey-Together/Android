package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.RowSchedulePublicBinding

class SchedulePublicAdapter :
    RecyclerView.Adapter<SchedulePublicAdapter.SchedulePublicViewHolder>() {

    class SchedulePublicViewHolder(private val binding: RowSchedulePublicBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(){
                // data와 view연결
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedulePublicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SchedulePublicViewHolder(RowSchedulePublicBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: SchedulePublicViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 5
    }
}