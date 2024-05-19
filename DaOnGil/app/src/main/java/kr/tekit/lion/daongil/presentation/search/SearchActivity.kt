package kr.tekit.lion.daongil.presentation.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import kr.tekit.lion.daongil.databinding.ActivitySearchBinding
import kr.tekit.lion.daongil.presentation.ext.repeatOnStarted
import kr.tekit.lion.daongil.presentation.search.vm.SearchViewModel
import kr.tekit.lion.daongil.presentation.search.vm.SearchViewModelFactory

class SearchActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModels{ SearchViewModelFactory() }
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.repeatOnStarted{
            viewModel.searchResult.collect{
                Log.d("SearchResult", it.toString())
            }
        }

        with(binding){
            btnBack.setOnClickListener { finish() }

            searchView.editText.doAfterTextChanged {
                viewModel.onCompleteTextChanged(it.toString())
            }


        }
    }
}