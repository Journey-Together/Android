package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.RowScheduleMyBinding

class ScheduleMyAdapter:RecyclerView.Adapter<ScheduleMyAdapter.ScheduleMyViewHolder>(){
    class ScheduleMyViewHolder(private val binding: RowScheduleMyBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){
            val tempDDay = 1 // 임시 변수
            // 다가오는 일정인 경우
            if(tempDDay > 0){
                binding.viewRowScheduleRemainingDeco.visibility = View.VISIBLE
                binding.viewRowScheduleElapsedDeco.visibility = View.INVISIBLE
                binding.textViewRowScheduleDDay.isEnabled = true // 일정 상태에 따라 TextColor 변경
                binding.buttonRowScheduleReview.visibility = View.GONE // 후기작성 버튼 숨김처리
            }
            // 다녀온 일정인 경우
            else{
                binding.viewRowScheduleRemainingDeco.visibility = View.INVISIBLE
                binding.viewRowScheduleElapsedDeco.visibility = View.VISIBLE
                binding.textViewRowScheduleDDay.isEnabled = false
                binding.buttonRowScheduleReview.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleMyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScheduleMyViewHolder(RowScheduleMyBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ScheduleMyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 3
    }
}