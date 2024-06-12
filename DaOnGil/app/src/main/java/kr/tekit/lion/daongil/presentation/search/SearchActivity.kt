package kr.tekit.lion.daongil.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.databinding.ActivitySearchBinding
import kr.tekit.lion.daongil.presentation.ext.repeatOnStarted
import kr.tekit.lion.daongil.presentation.search.adapter.RecentSearchKeywordAdapter
import kr.tekit.lion.daongil.presentation.search.vm.SearchViewModel
import kr.tekit.lion.daongil.presentation.search.vm.SearchViewModelFactory

class SearchActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModels { SearchViewModelFactory(this) }
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val recentSearchAdapter by lazy {
        RecentSearchKeywordAdapter(
            rootClickListener = { keyword ->

            },
            delButtonClickListener = { id ->
                viewModel.onClickItemRemoveButton(id)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        with(binding) {
            btnBack.setOnClickListener { finish() }
            searchView.requestFocus()

            tvDeleteAll.setOnClickListener {
                viewModel.onClickAllItemRemoveButton()
            }

            searchView.addTextChangedListener {
                val searchKeyword = it.toString().trim()
                viewModel.onCompleteTextChanged(searchKeyword)
            }

            rvRecentSearchKeyword.adapter = recentSearchAdapter
            rvRecentSearchKeyword.layoutManager = LinearLayoutManager(
                this@SearchActivity,
                LinearLayoutManager.VERTICAL,
                true
            )
            (rvRecentSearchKeyword.layoutManager as LinearLayoutManager).stackFromEnd = true

            repeatOnStarted {
                viewModel.recentSearchKeyword.collect{
                    recentSearchAdapter.submitList(it)
                }
            }

        }
    }
}