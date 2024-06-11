package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormBookmarkedPlacesBinding
import kr.tekit.lion.daongil.domain.model.BookmarkedPlace

class FormBookmarkedPlacesAdapter(
    private val bookmarkedPlaces: List<BookmarkedPlace>,
    private val onPlaceSelectedListener : (selectedPlaceId: Long) -> Unit
) : RecyclerView.Adapter<FormBookmarkedPlacesAdapter.FormBookmarkedPlacesViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FormBookmarkedPlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormBookmarkedPlacesViewHolder(
            ItemFormBookmarkedPlacesBinding.inflate(inflater, parent, false),
            onPlaceSelectedListener
        )
    }

    override fun onBindViewHolder(holder: FormBookmarkedPlacesViewHolder, position: Int) {
        holder.bind(bookmarkedPlaces[position])
    }

    override fun getItemCount(): Int {
        return bookmarkedPlaces.size
    }

    class FormBookmarkedPlacesViewHolder(
        private val binding: ItemFormBookmarkedPlacesBinding,
        private val onPlaceSelectedListener : (selectedPlaceId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: BookmarkedPlace) {
            binding.buttonBookmarkedPlace.text = place.bookmarkedPlaceName
            binding.buttonBookmarkedPlace.setOnClickListener {
                onPlaceSelectedListener(place.bookmarkedPlaceId)
            }
        }
    }
}