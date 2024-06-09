package kr.tekit.lion.daongil.presentation.myschedule.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemMyScheduleElapsedBinding
import kr.tekit.lion.daongil.domain.model.MySchedule

class MyScheduleElapsedAdapter(
    private val mySchedules: List<MySchedule>,
    private val onReviewButtonClicked: (Int, Boolean?) -> Unit,
    private val onScheduleItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<MyScheduleElapsedAdapter.ElapsedScheduleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElapsedScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ElapsedScheduleViewHolder(
            ItemMyScheduleElapsedBinding.inflate(
                inflater,
                parent,
                false
            ), onReviewButtonClicked, onScheduleItemClicked
        )
    }

    override fun onBindViewHolder(holder: ElapsedScheduleViewHolder, position: Int) {
        holder.bind(mySchedules[position])
    }

    override fun getItemCount(): Int {
        return mySchedules.size
    }

    class ElapsedScheduleViewHolder(
        private val binding: ItemMyScheduleElapsedBinding,
        private val onReviewButtonClicked: (Int, Boolean?) -> Unit,
        private val onScheduleItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mySchedule: MySchedule) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(mySchedule.imageUrl)
                    .placeholder(R.drawable.empty_view)
                    .error(R.drawable.empty_view)
                    .override(50, 50)
                    .into(binding.imageViewMyScheduleElapsed)
                textViewMyScheduleElapsedName.text = mySchedule.title
                //머지 후 수정할 부분
                //textViewMyScheduleElapsedPeriod.text = itemView.context.getString(R.string.)
                textViewMyScheduleElapsedPeriod.text = "${mySchedule.startDate} - ${mySchedule.endDate}"
                buttonMyScheduleElapsedReview.setOnClickListener {
                    onReviewButtonClicked(mySchedule.planId, mySchedule.hasReview)
                }
                root.setOnClickListener {
                    onScheduleItemClicked(mySchedule.planId)
                }
            }
        }
    }
}