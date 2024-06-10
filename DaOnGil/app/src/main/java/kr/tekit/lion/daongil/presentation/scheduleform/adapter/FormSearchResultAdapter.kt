package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormSearchResultBinding
import kr.tekit.lion.daongil.domain.model.FormSearchedPlace
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel

class FormSearchResultAdapter(
    private val searchResult : MutableList<FormSearchedPlace>, val navController: NavController,
    val scheduleViewModel: ScheduleFormViewModel, val schedulePosition: Int
) : RecyclerView.Adapter<FormSearchResultAdapter.FormSearchResultViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormSearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormSearchResultViewHolder(ItemFormSearchResultBinding.inflate(inflater, parent, false),
            navController, scheduleViewModel, schedulePosition)
    }

    override fun onBindViewHolder(holder: FormSearchResultViewHolder, position: Int) {
        holder.bind(searchResult[position])
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    class FormSearchResultViewHolder(
        private val binding: ItemFormSearchResultBinding, val navController: NavController,
        val scheduleViewModel: ScheduleFormViewModel, val schedulePosition: Int
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(searchedPlace : FormSearchedPlace){
            binding.textViewSearchResultName.text = searchedPlace.searchedPlaceName
            binding.textViewSearchResultCategory.text = searchedPlace.searchedPlaceCategory

            binding.imageButtonSearchResultAdd.setOnClickListener {
                // viewModel에 추가
                scheduleViewModel.addNewPlace(schedulePosition, searchedPlace.searchedPlaceId)
                navController.popBackStack()
            }
        }

    }

}