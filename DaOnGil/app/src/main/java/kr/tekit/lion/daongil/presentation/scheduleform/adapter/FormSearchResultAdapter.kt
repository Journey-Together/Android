package kr.tekit.lion.daongil.presentation.scheduleform.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.ItemFormSearchResultBinding
import kr.tekit.lion.daongil.domain.model.FormSearchedPlace

class FormSearchResultAdapter(private val searchResult : MutableList<FormSearchedPlace>) : RecyclerView.Adapter<FormSearchResultAdapter.FormSearchResultViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormSearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FormSearchResultViewHolder(ItemFormSearchResultBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FormSearchResultViewHolder, position: Int) {
        holder.bind(searchResult[position])
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }

    class FormSearchResultViewHolder(private val binding: ItemFormSearchResultBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result : FormSearchedPlace){
            binding.textViewSearchResultName.text = result.searchedPlaceName
            binding.textViewSearchResultCategory.text = result.searchedPlaceCategory

            binding.imageButtonSearchResultAdd.setOnClickListener {
            }
        }

    }

}