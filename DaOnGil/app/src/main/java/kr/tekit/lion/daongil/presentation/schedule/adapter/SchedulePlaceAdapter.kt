package kr.tekit.lion.daongil.presentation.schedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemScheduleEmptyBinding
import kr.tekit.lion.daongil.databinding.ItemSchedulePlaceBinding
import kr.tekit.lion.daongil.domain.model.SchedulePlace

class SchedulePlaceAdapter(
    private val schedulePlaces: List<SchedulePlace?>,
    private val schedulePlaceListener: OnSchedulePlaceClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_PLACE = 0
    private val VIEW_TYPE_EMPTY = 1

    interface OnSchedulePlaceClickListener {
        fun onSchedulePlaceClick(placeId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            VIEW_TYPE_PLACE -> {
                return SchedulePlaceViewHolder(
                    ItemSchedulePlaceBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            // VIEW_TYPE_EMPTY
            else -> {
                return ScheduleEmptyViewHolder(
                    ItemScheduleEmptyBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SchedulePlaceViewHolder -> {
                schedulePlaces[position]?.let {
                    holder.bind(it, schedulePlaceListener)
                }
            }

            is ScheduleEmptyViewHolder -> {}
        }
    }


    override fun getItemCount(): Int {
        val placesSize = schedulePlaces.size
        return if (placesSize == 0) {
            1
        } else {
            placesSize
        }
    }

    override fun getItemViewType(position: Int): Int {
        val placesSize = schedulePlaces.size
        return if (placesSize != 0) {
            VIEW_TYPE_PLACE
        } else {
            VIEW_TYPE_EMPTY
        }
    }
}

class SchedulePlaceViewHolder(private val binding: ItemSchedulePlaceBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        schedulePlace: SchedulePlace,
        schedulePlacelistener: SchedulePlaceAdapter.OnSchedulePlaceClickListener
    ) {
        with(binding) {
            textViewItemSPlaceName.text = schedulePlace.name
            textViewItemSPlaceCategory.text = schedulePlace.category

            // 무장애 유형 정보
            // 1 (지체 장애), 2 (시각 장애), 3 (청각 장애), 4 (유아 동반), 5 (고령자)
            schedulePlace.disability.forEach {
                when (it) {
                    1 -> iconISPPhysicalDisability.visibility = View.VISIBLE
                    2 -> iconISPVisualImpairment.visibility = View.VISIBLE
                    3 -> iconISPHearingImpairment.visibility = View.VISIBLE
                    4 -> iconISPInfantFamily.visibility = View.VISIBLE
                    5 -> iconISPElderlyPeople.visibility = View.VISIBLE
                }
            }

            root.setOnClickListener {
                schedulePlacelistener.onSchedulePlaceClick(schedulePlace.placeId)
            }
        }
    }
}

class ScheduleEmptyViewHolder(private val binding: ItemScheduleEmptyBinding) :
    RecyclerView.ViewHolder(binding.root)