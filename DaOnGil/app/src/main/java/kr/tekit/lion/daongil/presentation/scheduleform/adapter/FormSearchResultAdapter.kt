package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ItemFormSearchResultBinding
import kr.tekit.lion.daongil.domain.model.PlaceSearchInfo

class FormSearchResultAdapter(
    private val searchResult: List<PlaceSearchInfo>,
    private val onPlaceSelectedListener : (selectedPlaceId: Long) -> Unit
) : RecyclerView.Adapter<FormSearchResultAdapter.FormSearchResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormSearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormSearchResultViewHolder(
            ItemFormSearchResultBinding.inflate(inflater, parent, false),
            onPlaceSelectedListener
        )
    }

    override fun onBindViewHolder(holder: FormSearchResultViewHolder, position: Int) {
        holder.bind(searchResult[position])
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    class FormSearchResultViewHolder(
        private val binding: ItemFormSearchResultBinding,
        private val onPlaceSelectedListener : (selectedPlaceId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeSearchInfo: PlaceSearchInfo) {
            binding.apply {
                textViewSearchResultName.text = placeSearchInfo.placeName
                textViewSearchResultCategory.text = placeSearchInfo.category
                Glide.with(binding.imageViewSearchResultThumbnail.context)
                    .load(placeSearchInfo.imageUrl)
                    .placeholder(R.drawable.empty_view_small)
                    .error(R.drawable.empty_view_small)
                    .into(imageViewSearchResultThumbnail)

                imageButtonSearchResultAdd.setOnClickListener {
                    onPlaceSelectedListener(placeSearchInfo.placeId)
                }
            }
        }
    }

}