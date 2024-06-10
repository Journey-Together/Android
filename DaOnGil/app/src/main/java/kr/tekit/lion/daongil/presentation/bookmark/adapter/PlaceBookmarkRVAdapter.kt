package kr.tekit.lion.daongil.presentation.bookmark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemLocationBookmarkBinding
import kr.tekit.lion.daongil.domain.model.PlaceBookmark

class PlaceBookmarkRVAdapter(private val placeBookmarkList: List<PlaceBookmark>, private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<PlaceBookmarkRVAdapter.PlaceBookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceBookmarkViewHolder {
        val binding : ItemLocationBookmarkBinding = ItemLocationBookmarkBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return PlaceBookmarkViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = placeBookmarkList.size

    override fun onBindViewHolder(holder: PlaceBookmarkViewHolder, position: Int) {
        holder.bind(placeBookmarkList[position])
    }

    class PlaceBookmarkViewHolder(val binding: ItemLocationBookmarkBinding, private val itemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeBookmarkList: PlaceBookmark) {
            binding.textViewLocationBookmark.text = placeBookmarkList.address
            binding.textViewLocationBookmarkName.text = placeBookmarkList.name

            val disabilityList = placeBookmarkList.disability

            val bookmarkDisabilityRVAdapter = bookmarkDisabilityRVAdapter(disabilityList)
            binding.recyclerViewLocationBookmark.adapter = bookmarkDisabilityRVAdapter
        }
    }
}