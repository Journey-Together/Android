package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentModifySearchBinding
import kr.tekit.lion.daongil.presentation.ext.addOnScrollEndListener
import kr.tekit.lion.daongil.presentation.ext.showSnackbar
import kr.tekit.lion.daongil.presentation.home.DetailActivity
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormBookmarkedPlacesAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormSearchResultAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ModifyScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ModifyScheduleFormViewModelFactory


class ModifySearchFragment : Fragment(R.layout.fragment_modify_search) {
    private val args: ModifySearchFragmentArgs by navArgs()
    private val viewModel: ModifyScheduleFormViewModel by activityViewModels {
        ModifyScheduleFormViewModelFactory()
    }

    private val searchResultAdapter by lazy {
        FormSearchResultAdapter(
            onPlaceSelectedListener = { selectedPlacePosition ->
                addNewPlace(args.schedulePosition, selectedPlacePosition, false, requireView())
            },
            onItemClickListener = { selectedPlacePosition ->
                val placeId = viewModel.getPlaceId(selectedPlacePosition)
                if(placeId != -1L){
                    showPlaceDetail(placeId)
                }
            }
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val schedulePosition = args.schedulePosition

        val binding = FragmentModifySearchBinding.bind(view)

        initToolbar(binding)
        settingBookmarkRV(binding, schedulePosition)
        settingSearchResultRV(binding)
        settingPlaceSearchView(binding)
    }

    private fun initToolbar(binding: FragmentModifySearchBinding){
        binding.toolbarModifySearch.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun settingBookmarkRV(binding: FragmentModifySearchBinding, schedulePosition: Int ){
        val flexboxLayoutManager = FlexboxLayoutManager(requireActivity()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.FLEX_START
            justifyContent = JustifyContent.FLEX_START
        }

        viewModel.bookmarkedPlaces.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                binding.recyclerViewModifyBookmark.apply {
                    layoutManager = flexboxLayoutManager
                    adapter = FormBookmarkedPlacesAdapter(it){ selectedPlacePosition ->
                        addNewPlace(schedulePosition, selectedPlacePosition, true, this)
                    }
                }
            }else{
                // 북마크한 여행지가 없다면
                binding.apply {
                    recyclerViewModifyBookmark.visibility = View.INVISIBLE
                    textViewModifyBookmarkEmpty.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun addNewPlace(schedulePosition: Int, selectedPlacePosition: Int, isBookmarkedPlace: Boolean, view: View) {
        val isDuplicate = viewModel.isPlaceAlreadyAdded(
            schedulePosition,
            selectedPlacePosition,
            isBookmarkedPlace
        )
        if (isDuplicate) {
            view.showSnackbar("이 여행지는 이미 일정에 추가되어 있습니다")
        } else {
            viewModel.getSearchedPlaceDetailInfo(
                schedulePosition,
                selectedPlacePosition,
                isBookmarkedPlace
            )
            findNavController().popBackStack()
        }
    }

    private fun settingSearchResultRV(binding: FragmentModifySearchBinding){
        binding.recyclerViewModifySearchResult.apply {
            adapter = searchResultAdapter
            addOnScrollEndListener{
                with(viewModel){
                    if(!isLastPage()){
                        fetchNextPlaceResults()
                    }
                }
            }
        }

        viewModel.searchResultsWithNum.observe(viewLifecycleOwner) {
            // ListAdapter 사용 시, submitList 메서드를 호출하여 데이터를 전달해준다.
            searchResultAdapter.submitList(it)
            if (it.size <= 1) {
                binding.textViewModifySearchResultEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun settingPlaceSearchView(binding: FragmentModifySearchBinding){
        binding.searchViewModifySearchResult.apply {
            editText.setOnEditorActionListener { textView, actionId, event ->
                if(event!=null && event.action == KeyEvent.ACTION_DOWN){
                    val word = editText.text.toString()
                    if(word.isEmpty()){
                        this.showSnackbar("검색어를 입력해주세요")
                    }else{
                        binding.recyclerViewModifySearchResult.visibility = View.VISIBLE
                        binding.textViewModifySearchResultEmpty.visibility = View.GONE
                        viewModel.getPlaceSearchResult(word, 0)
                    }
                }
                false
            }
        }
    }

    private fun showPlaceDetail(placeId: Long){
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("detailPlaceId", placeId)
        startActivity(intent)
    }
}