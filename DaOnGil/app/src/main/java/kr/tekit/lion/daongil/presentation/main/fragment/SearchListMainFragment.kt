package kr.tekit.lion.daongil.presentation.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentSearchListMainBinding
import kr.tekit.lion.daongil.presentation.ext.repeatOnViewStarted
import kr.tekit.lion.daongil.presentation.main.adapter.ListSearchAdapter
import kr.tekit.lion.daongil.presentation.main.model.AreaModel
import kr.tekit.lion.daongil.presentation.main.model.Category
import kr.tekit.lion.daongil.presentation.main.model.CategoryModel
import kr.tekit.lion.daongil.presentation.main.model.ListSearchUIModel
import kr.tekit.lion.daongil.presentation.main.model.PlaceModel
import kr.tekit.lion.daongil.presentation.main.vm.SearchMainViewModel
import kr.tekit.lion.daongil.presentation.main.vm.SearchMainViewModelFactory

class SearchListMainFragment : Fragment(R.layout.fragment_search_list_main) {
    private val viewModel: SearchMainViewModel by viewModels {
        SearchMainViewModelFactory(
            requireContext()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchListMainBinding.bind(view)

        repeatOnViewStarted {
            viewModel.listSearchOption.collect{
                Log.d("OptionResult", it.toString())
            }
        }

        with(binding) {
            tabContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    // 탭이 선택되었을 때 수행할 작업
                    when (tab.position) {
                        0 -> viewModel.onSelectedTab(Category.PLACE.name)
                        1 -> viewModel.onSelectedTab(Category.RESTAURANT.name)
                        2 -> viewModel.onSelectedTab(Category.ROOM.name)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            val mainAdapter = ListSearchAdapter(
                onSelectArea = {
                    viewModel.onSelectedArea(it)
                },
                onSelectSigungu = {
                    viewModel.onSelectedSigungu(it)
                },
                onClickSearchButton = {
                    viewModel.onClickSearchButton()
                }
            )

            val arr = ArrayList<ListSearchUIModel>()
            arr.add(CategoryModel)
            arr.add(AreaModel)

            repeat(10) {
                arr.add(
                    PlaceModel(
                        "123",
                        "sdasdasd",
                        "123",
                        "http://tong.visitkorea.or.kr/cms/resource/70/3039170_image2_1.JPG",
                        arrayOf("1", "2").toList(),
                    )
                )
            }

            repeatOnViewStarted {
                viewModel.areaCode.collect {
                    mainAdapter.submitAreaList(it)
                }
            }

            repeatOnViewStarted {
                viewModel.sigunguCode.collect { result ->
                    mainAdapter.submitSigunguList(result.map { it.sigunguName })
                }
            }
            mainAdapter.submitList(arr)

            rvSearchResult.adapter = mainAdapter
            rvSearchResult.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        }
    }
}

