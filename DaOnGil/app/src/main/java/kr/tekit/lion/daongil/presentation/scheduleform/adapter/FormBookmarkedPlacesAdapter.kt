package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormBookmarkedPlacesBinding
import kr.tekit.lion.daongil.domain.model.BookmarkedPlace
import kr.tekit.lion.daongil.presentation.scheduleform.fragment.FormSearchFragmentDirections

class FormBookmarkedPlacesAdapter (private val bookmarkedPlaces : MutableList<BookmarkedPlace>, val context: Context, val navController: NavController) : RecyclerView.Adapter<FormBookmarkedPlacesAdapter.FormBookmarkedPlacesViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FormBookmarkedPlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormBookmarkedPlacesViewHolder(ItemFormBookmarkedPlacesBinding.inflate(inflater, parent, false), context, navController)
    }

    override fun onBindViewHolder(holder: FormBookmarkedPlacesViewHolder, position: Int) {
        holder.bind(bookmarkedPlaces[position])
    }

    override fun getItemCount(): Int {
        return bookmarkedPlaces.size
    }

    class FormBookmarkedPlacesViewHolder(private val binding: ItemFormBookmarkedPlacesBinding, val context: Context, val navController: NavController) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place : BookmarkedPlace) {
            binding.buttonBookmarkedPlace.text = place.bookmarkedPlaceName
            binding.buttonBookmarkedPlace.setOnClickListener {
                /*val intent = Intent()
                intent.putExtra("placeId", place.bookmarkedPlaceId)
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()*/

                /*val bundle = bundleOf("placeId" to place.bookmarkedPlaceId)
                navController.navigate(R.id.action_formSearchFragment_to_scheduleDetailsFormFragment, bundle)*/
                val action = FormSearchFragmentDirections.actionFormSearchFragmentToScheduleDetailsFormFragment(null, null, null, place.bookmarkedPlaceId)
                navController.navigate(action)
            }

        }
    }
}