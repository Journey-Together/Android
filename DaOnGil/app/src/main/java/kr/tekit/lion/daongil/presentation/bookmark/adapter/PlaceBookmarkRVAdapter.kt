package kr.tekit.lion.daongil.presentation.bookmark.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemLocationBookmarkBinding
import kr.tekit.lion.daongil.domain.model.PlaceBookmark

class PlaceBookmarkRVAdapter(private val placeBookmarkList: List<PlaceBookmark>, val context: Context, private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<PlaceBookmarkRVAdapter.LocationBookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationBookmarkViewHolder {
        val binding : ItemLocationBookmarkBinding = ItemLocationBookmarkBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return LocationBookmarkViewHolder(binding, context, itemClickListener)
    }

    override fun getItemCount(): Int = placeBookmarkList.size

    override fun onBindViewHolder(holder: LocationBookmarkViewHolder, position: Int) {
        holder.bind(placeBookmarkList[position])
    }

    class LocationBookmarkViewHolder(val binding: ItemLocationBookmarkBinding, val context: Context, private val itemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeBookmarkList: PlaceBookmark) {
            binding.textViewLocationBookmark.text = placeBookmarkList.bookmarkLocation
            binding.textViewLocationBookmarkName.text = placeBookmarkList.bookmarkLocationName

            val disabilityList = placeBookmarkList.disabilityType

            val bookmarkDisabilityRVAdapter = bookmarkDisabilityRVAdapter(disabilityList, context)
            binding.recyclerViewLocationBookmark.adapter = bookmarkDisabilityRVAdapter
            binding.recyclerViewLocationBookmark.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}