package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.RowScheduleMyBinding
import kr.tekit.lion.daongil.domain.model.MyMainSchedule

class ScheduleMyAdapter(
    private val itemClickListener: (Int) -> Unit,
    private val reviewClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ScheduleMyAdapter.ScheduleMyViewHolder>() {

    private var items: MutableList<MyMainSchedule> = mutableListOf()

    fun addItems(newItems: List<MyMainSchedule>){
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    class ScheduleMyViewHolder(
        private val binding: RowScheduleMyBinding,
        private val itemClickListener: (Int) -> Unit,
        private val reviewClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener(absoluteAdapterPosition)
            }
            binding.buttonRowScheduleReview.setOnClickListener {
                reviewClickListener(absoluteAdapterPosition)
            }
        }

        fun bind(item: MyMainSchedule) {

            with(binding) {
                textViewRowScheduleName.text = item.title
                textViewRowSchedulePeriod.text = itemView.context.getString(
                    R.string.text_schedule_period,
                    item.startDate,
                    item.endDate
                )

                // 다녀온 일정인 경우
                if (item.remainDate == null) {
                    viewRowScheduleRemainingDeco.visibility = View.INVISIBLE
                    viewRowScheduleElapsedDeco.visibility = View.VISIBLE
                    textViewRowScheduleDDay.visibility = View.INVISIBLE
                    if(item.hasReview == false) {
                        buttonRowScheduleReview.visibility = View.VISIBLE
                    }
                    if(item.hasReview == true){
                        buttonRowScheduleReview.visibility = View.GONE
                    }
                }
                // 다가오는 일정인 경우
                else {
                    viewRowScheduleRemainingDeco.visibility = View.VISIBLE
                    viewRowScheduleElapsedDeco.visibility = View.INVISIBLE
                    textViewRowScheduleDDay.isEnabled = true // 일정 상태에 따라 TextColor 변경
                    textViewRowScheduleDDay.text = item.remainDate
                    buttonRowScheduleReview.visibility = View.GONE // 후기작성 버튼 숨김처리
                }

                if(item.imageUrl != ""){
                    Glide.with(imageViewRowSchedule.context)
                        .load(item.imageUrl)
                        .error(R.drawable.empty_view_small)
                        .into(binding.imageViewRowSchedule)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleMyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScheduleMyViewHolder(
            RowScheduleMyBinding.inflate(inflater, parent, false),
            itemClickListener,
            reviewClickListener
        )
    }

    override fun onBindViewHolder(holder: ScheduleMyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}