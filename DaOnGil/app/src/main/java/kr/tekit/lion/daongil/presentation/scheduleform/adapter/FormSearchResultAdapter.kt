package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemFormSearchResultBinding
import kr.tekit.lion.daongil.domain.model.PlaceSearchInfo

class FormSearchResultAdapter(
    private val onPlaceSelectedListener : (selectedPlacePosition: Int) -> Unit
) : ListAdapter<PlaceSearchInfo, FormSearchResultAdapter.FormSearchResultViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormSearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormSearchResultViewHolder(
            ItemFormSearchResultBinding.inflate(inflater, parent, false),
            onPlaceSelectedListener
        )
    }

    override fun onBindViewHolder(holder: FormSearchResultViewHolder, position: Int) {
        // ListAdapter 사용 시, getItem(position) 메서드로 position의 아이템을 가져오는 걸 권장
        holder.bind(getItem(position))
    }

    class FormSearchResultViewHolder(
        private val binding: ItemFormSearchResultBinding,
        private val onPlaceSelectedListener : (selectedPlacePosition: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageButtonSearchResultAdd.setOnClickListener {
                onPlaceSelectedListener(absoluteAdapterPosition)
            }
        }
        fun bind(placeSearchInfo: PlaceSearchInfo) {
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

    // DiffUtil -> 하나의 인스턴스로 공유
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PlaceSearchInfo>() {
            // 두 개의 아이템이 동일한 지 확인 (고유 식별자 - e.g. placeId - 를 비교
            // true를 반환하면 areContentsTheSame를 호출하여 내용 변경사항이 없는 지 확인
            override fun areItemsTheSame(oldItem: PlaceSearchInfo, newItem: PlaceSearchInfo): Boolean {
                return oldItem.placeId == newItem.placeId
            }
            // 아이템의 내용(객체의 모든 필드를 비교)이 동일한 지 확인
            // true를 반환하면, DiffUtil은 해당 항목이 업데이트가 필요하지 않다고 판단하고,
            // false를 반환하면, 해당 항목을 업데이트한다.
            override fun areContentsTheSame(oldItem: PlaceSearchInfo, newItem: PlaceSearchInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}