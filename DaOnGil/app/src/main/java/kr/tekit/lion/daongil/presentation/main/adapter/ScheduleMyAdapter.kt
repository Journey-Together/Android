package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.RowScheduleMyBinding

class ScheduleMyAdapter(private val scheduleMainItemListener: OnScheduleMainItemClickListener) :
    RecyclerView.Adapter<ScheduleMyAdapter.ScheduleMyViewHolder>() {
    interface OnScheduleMainItemClickListener {
        fun onScheduleMainItemClick(scheduleIdx: Int)
    }

    class ScheduleMyViewHolder(
        private val binding: RowScheduleMyBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scheduleMainItemListener: OnScheduleMainItemClickListener) {
            val tempDDay = 1 // 임시 변수

            with(binding) {
                textViewRowScheduleName.text = "제주 둘레길 여행"
                textViewRowSchedulePeriod.text = "2024.06.01 - 2024.06.05"

                // 다가오는 일정인 경우
                if (tempDDay > 0) {
                    viewRowScheduleRemainingDeco.visibility = View.VISIBLE
                    viewRowScheduleElapsedDeco.visibility = View.INVISIBLE
                    textViewRowScheduleDDay.isEnabled = true // 일정 상태에 따라 TextColor 변경
                    textViewRowScheduleDDay.text = "D-1"
                    buttonRowScheduleReview.visibility = View.GONE // 후기작성 버튼 숨김처리
                }
                // 다녀온 일정인 경우
                else {
                    viewRowScheduleRemainingDeco.visibility = View.INVISIBLE
                    viewRowScheduleElapsedDeco.visibility = View.VISIBLE
                    textViewRowScheduleDDay.isEnabled = false
                    buttonRowScheduleReview.visibility = View.VISIBLE
                }
                root.setOnClickListener {
                    scheduleMainItemListener.onScheduleMainItemClick(1)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleMyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScheduleMyViewHolder(RowScheduleMyBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ScheduleMyViewHolder, position: Int) {
        holder.bind(scheduleMainItemListener)
    }

    override fun getItemCount(): Int {
        return 4
    }
}