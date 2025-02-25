package kr.tekit.lion.daongil.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.databinding.RowSchedulePublicBinding
import kr.tekit.lion.daongil.domain.model.OpenPlanInfo
import kr.tekit.lion.daongil.presentation.ext.setImage

class SchedulePublicAdapter(
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<SchedulePublicAdapter.SchedulePublicViewHolder>() {

    //private val dataList: ArrayList<ListSearchUIModel> = ArrayList()
    //
    //    fun submitList(list: ArrayList<ListSearchUIModel>) {
    //        dataList.clear()
    //        dataList.addAll(list)
    //    }
    // https://mashup-android.vercel.app/mashup-10th/hyeonseong/sealed_class/
    private var items: MutableList<OpenPlanInfo> = mutableListOf()

    fun addItems(newItems: List<OpenPlanInfo>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    class SchedulePublicViewHolder(
        private val binding: RowSchedulePublicBinding,
        private val itemClickListener: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener(adapterPosition)
            }
        }

        fun bind(item: OpenPlanInfo) {
            with(binding) {
                textViewRowSchedulePublicPeriod.text = item.date
                textViewRowScheduleName.text = item.title
                textViewRowSchedulePublicNickname.text = item.memberNickname

                root.context.setImage(imageViewRowSchedulePublicThumb, item.imageUrl)
                root.context.setImage(imageViewRowSchedulePublicProfile, item.memberImageUrl)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedulePublicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SchedulePublicViewHolder(
            RowSchedulePublicBinding.inflate(inflater, parent, false),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: SchedulePublicViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}