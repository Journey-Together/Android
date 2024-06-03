package kr.tekit.lion.daongil.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.tekit.lion.daongil.databinding.RowRecentSearchedBinding
import kr.tekit.lion.daongil.domain.model.RecentSearchKeyword

class RecentSearchKeywordAdapter(
    private val rootClickListener: (String) -> Unit,
    private val delButtonClickListener: (Long) -> Unit
): ListAdapter<RecentSearchKeyword, RecentSearchKeywordAdapter.RecentSearchViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return RecentSearchViewHolder(
            RowRecentSearchedBinding.inflate(inflater, parent, false),
            rootClickListener,
            delButtonClickListener
        )
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, pos: Int) {
        holder.bind(getItem(pos))
    }

    class RecentSearchViewHolder(
        private val binding: RowRecentSearchedBinding,
        private val rootClickListener: (String) -> Unit,
        private val delButtonClickListener: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: RecentSearchKeyword) {
            with(binding){
                textviewRecentSearch.text = item.keyword

                root.setOnClickListener {
                    rootClickListener.invoke(item.keyword)
                }

                imageButtonDelete.setOnClickListener {
                    delButtonClickListener.invoke(item.id)
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RecentSearchKeyword>() {

            override fun areItemsTheSame(oldItem: RecentSearchKeyword, newItem: RecentSearchKeyword): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecentSearchKeyword, newItem: RecentSearchKeyword): Boolean {
                return oldItem == newItem
            }
        }
    }
}
