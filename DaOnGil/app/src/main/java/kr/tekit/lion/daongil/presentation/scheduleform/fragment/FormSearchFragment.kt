package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentFormSearchBinding
import kr.tekit.lion.daongil.presentation.ext.addOnScrollEndListener
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormBookmarkedPlacesAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormSearchResultAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModelFactory


class FormSearchFragment : Fragment(R.layout.fragment_form_search) {
    private val args: FormSearchFragmentArgs by navArgs()
    private val scheduleFormViewModel : ScheduleFormViewModel by activityViewModels{ ScheduleFormViewModelFactory() }

    private val searchResultAdapter by lazy {
        FormSearchResultAdapter{ selectedPlacePosition ->
            addNewPlace(args.schedulePosition, selectedPlacePosition, false)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 추가해야하는 일정의 position
        val schedulePosition = args.schedulePosition

        val binding = FragmentFormSearchBinding.bind(view)

        initToolbar(binding)

        settingBookmarkedRV(binding, schedulePosition)
        settingSearchResultRV(binding)
        settingPlaceSearchView(binding)
    }

    private fun initToolbar(binding: FragmentFormSearchBinding){
        binding.toolbarFormSearch.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun settingBookmarkedRV(binding: FragmentFormSearchBinding, schedulePosition: Int ){
        // 만약 검색 화면에 들어올 때마다 북마크 목록을 갱신해주고 싶다면..
        //scheduleFormViewModel.getBookmarkedPlaceList()

        val flexboxLayoutManager = FlexboxLayoutManager(requireActivity()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.FLEX_START
            justifyContent = JustifyContent.FLEX_START
        }

        scheduleFormViewModel.bookmarkedPlaces.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                binding.recyclerViewFSBookmark.apply {
                    layoutManager = flexboxLayoutManager
                    adapter = FormBookmarkedPlacesAdapter(it){ selectedPlacePosition ->
                        addNewPlace(schedulePosition, selectedPlacePosition, true)
                    }
                }
            }else{
                // 북마크한 여행지가 없다면
                binding.apply {
                    recyclerViewFSBookmark.visibility = View.INVISIBLE
                    textViewFSBookmarkEmpty.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun settingSearchResultRV(binding: FragmentFormSearchBinding){
        binding.recyclerViewFSResult.apply {
            adapter = searchResultAdapter
            addOnScrollEndListener{
                with(scheduleFormViewModel){
                    if(!isLastPage()){
                        fetchNextPlaceResults(20)
                    }
                }
            }
        }

/*        scheduleFormViewModel.placeSearchResult.observe(viewLifecycleOwner){
            if(it.placeInfoList.isNotEmpty()){
                // ListAdapter 사용 시, submitList 메서드를 호출하여 데이터를 전달해준다.
                searchResultAdapter.submitList(it.placeInfoList)
            }else{
                binding.recyclerViewFSResult.visibility = View.GONE
                binding.textViewFSResultEmpty.visibility = View.VISIBLE
            }
        }*/

        scheduleFormViewModel.searchResultsWithNum.observe(viewLifecycleOwner) {
            // ListAdapter 사용 시, submitList 메서드를 호출하여 데이터를 전달해준다.
            searchResultAdapter.submitList(it)
            if (it.size <= 1) {
                binding.textViewFSResultEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun addNewPlace(schedulePosition: Int, selectedPlacePosition: Int, isBookmarkedPlace: Boolean) {
        val isDuplicate = scheduleFormViewModel.isPlaceAlreadyAdded(
            schedulePosition,
            selectedPlacePosition,
            isBookmarkedPlace
        )
        if (isDuplicate) {
            showSnackBar("이 여행지는 이미 일정에 추가되어 있습니다")
        } else {
            scheduleFormViewModel.getSearchedPlaceDetailInfo(
                schedulePosition,
                selectedPlacePosition,
                isBookmarkedPlace
            )
            findNavController().popBackStack()
        }
    }

    private fun settingPlaceSearchView(binding: FragmentFormSearchBinding){
        binding.searchViewFSResult.apply {
            editText.setOnEditorActionListener { textView, actionId, event ->
                if(event!=null && event.action == KeyEvent.ACTION_DOWN){
                    val word = editText.text.toString()
                    if(word.isEmpty()){
                        showSnackBar("검색어를 입력해주세요")
                    }else{
                        binding.recyclerViewFSResult.visibility = View.VISIBLE
                        binding.textViewFSResultEmpty.visibility = View.GONE
                        scheduleFormViewModel.getPlaceSearchResult(word, 0, 20)
                    }
                }
                false
            }
        }
    }

    private fun showSnackBar( message : String ){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.text_secondary))
            .show()
    }

}