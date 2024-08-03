package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemFormSearchResultBinding
import kr.tekit.lion.daongil.databinding.ItemFormSearchTotalBinding
import kr.tekit.lion.daongil.domain.model.PlaceSearchInfoList

class FormSearchResultAdapter(
    private val onPlaceSelectedListener: (selectedPlacePosition: Int) -> Unit,
    private val onItemClickListener: (selectedPlacePosition: Int) -> Unit
) : ListAdapter<PlaceSearchInfoList, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_TOTAL_ELEMENTS -> {
                FormSearchTotalElementsViewHolder(
                    ItemFormSearchTotalBinding.inflate(inflater, parent, false)
                )
            }

            VIEW_TYPE_PLACE -> {
                FormSearchResultViewHolder(
                    ItemFormSearchResultBinding.inflate(inflater, parent, false),
                    onPlaceSelectedListener,
                    onItemClickListener
                )
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is PlaceSearchInfoList.TotalElementsInfo -> VIEW_TYPE_TOTAL_ELEMENTS
        is PlaceSearchInfoList.PlaceSearchInfo -> VIEW_TYPE_PLACE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // ListAdapter 사용 시, getItem(position) 메서드로 position의 아이템을 가져오는 걸 권장
        //holder.bind(getItem(position))
        when (holder) {
            is FormSearchTotalElementsViewHolder -> holder.bind(getItem(position) as PlaceSearchInfoList.TotalElementsInfo)
            is FormSearchResultViewHolder -> holder.bind(getItem(position) as PlaceSearchInfoList.PlaceSearchInfo)
        }
    }

    class FormSearchResultViewHolder(
        private val binding: ItemFormSearchResultBinding,
        private val onPlaceSelectedListener: (selectedPlacePosition: Int) -> Unit,
        private val onItemClickListener: (selectedPlacePosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageButtonSearchResultAdd.setOnClickListener {
                // 장소 목록 앞에 검색 결과 수 item 이 추가되었으므로 AdapterPosition에서 1을 빼준다
                onPlaceSelectedListener(absoluteAdapterPosition - 1)
            }

            binding.root.setOnClickListener {
                onItemClickListener(absoluteAdapterPosition -1)
            }
        }

        fun bind(placeSearchInfo: PlaceSearchInfoList.PlaceSearchInfo) {
            binding.apply {
                textViewSearchResultName.text = placeSearchInfo.placeName
                textViewSearchResultCategory.text = placeSearchInfo.category
                placeSearchInfo.imageUrl?.let {
                    Glide.with(binding.imageViewSearchResultThumbnail.context)
                        .load(it)
                        .placeholder(R.drawable.empty_view_small)
                        .error(R.drawable.empty_view_small)
                        .into(imageViewSearchResultThumbnail)
                }
            }
        }
    }

    class FormSearchTotalElementsViewHolder(
        private val binding: ItemFormSearchTotalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(totalElements: PlaceSearchInfoList.TotalElementsInfo) {
            val numOfElements = totalElements.totalElements.toString()

            binding.textViewFSTotalElements.apply {
                text = this.context.getString(R.string.text_number_of_result, numOfElements)
            }
        }
    }

    // DiffUtil -> 하나의 인스턴스로 공유
    companion object {
        private const val VIEW_TYPE_TOTAL_ELEMENTS = 0
        private const val VIEW_TYPE_PLACE = 1

        val diffUtil = object : DiffUtil.ItemCallback<PlaceSearchInfoList>() {
            // 두 개의 아이템이 동일한 지 확인 (고유 식별자 - e.g. placeId - 를 비교
            // true를 반환하면 areContentsTheSame를 호출하여 내용 변경사항이 없는 지 확인
            override fun areItemsTheSame(
                oldItem: PlaceSearchInfoList,
                newItem: PlaceSearchInfoList
            ): Boolean {
                // 같은 타입인 경우에만 data 비교
                return when {
                    oldItem is PlaceSearchInfoList.TotalElementsInfo && newItem is PlaceSearchInfoList.TotalElementsInfo -> {
                        oldItem.totalElements == newItem.totalElements
                    }

                    oldItem is PlaceSearchInfoList.PlaceSearchInfo && newItem is PlaceSearchInfoList.PlaceSearchInfo -> {
                        oldItem.placeId == newItem.placeId
                    }

                    else -> false
                }
            }

            // 아이템의 내용(객체의 모든 필드를 비교)이 동일한 지 확인
            // true를 반환하면, DiffUtil은 해당 항목이 업데이트가 필요하지 않다고 판단하고,
            // false를 반환하면, 해당 항목을 업데이트한다.
            override fun areContentsTheSame(
                oldItem: PlaceSearchInfoList,
                newItem: PlaceSearchInfoList
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}