package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormBookmarkedPlacesBinding
import kr.tekit.lion.daongil.domain.model.BookmarkedPlace
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel

class FormBookmarkedPlacesAdapter(
    private val bookmarkedPlaces: List<BookmarkedPlace>,
    private val navController: NavController,
    private val scheduleViewModel: ScheduleFormViewModel,
    private val schedulePosition: Int
) : RecyclerView.Adapter<FormBookmarkedPlacesAdapter.FormBookmarkedPlacesViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FormBookmarkedPlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormBookmarkedPlacesViewHolder(
            ItemFormBookmarkedPlacesBinding.inflate(inflater, parent, false),
            navController, scheduleViewModel, schedulePosition
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
        private val navController: NavController,
        private val scheduleViewModel: ScheduleFormViewModel,
        private val schedulePosition: Int
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: BookmarkedPlace) {
            binding.buttonBookmarkedPlace.text = place.bookmarkedPlaceName
            binding.buttonBookmarkedPlace.setOnClickListener {
                // viewModel에 추가
                scheduleViewModel.addNewPlace(schedulePosition, place.bookmarkedPlaceId)

                navController.popBackStack()
            }
        }
    }
}