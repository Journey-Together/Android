package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentFormSearchBinding
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormBookmarkedPlacesAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormSearchResultAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModelFactory


class FormSearchFragment : Fragment(R.layout.fragment_form_search) {
    private val args: FormSearchFragmentArgs by navArgs()
    private val scheduleFormViewModel : ScheduleFormViewModel by activityViewModels{ ScheduleFormViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 추가해야하는 일정의 position
        val schedulePosition = args.schedulePosition

        val binding = FragmentFormSearchBinding.bind(view)

        initToolbar(binding)

        settingBookmarkedRV(binding, schedulePosition)
        settingSearchResultRV(binding, schedulePosition)
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

        scheduleFormViewModel.bookmarkedPlaces.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                binding.recyclerViewFSBookmark.apply {
                    adapter = FormBookmarkedPlacesAdapter(it){ selectedPlaceId ->
                        val placeId = it[selectedPlaceId].bookmarkedPlaceId
                        addNewPlace(this, schedulePosition, placeId)
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

    private fun settingSearchResultRV(binding: FragmentFormSearchBinding, schedulePosition: Int){
        scheduleFormViewModel.placeSearchResult.observe(viewLifecycleOwner){
            if(it.placeInfoList.isNotEmpty()){
                binding.recyclerViewFSResult.apply {
                    adapter = FormSearchResultAdapter(it.placeInfoList){ selectedPlacePosition ->
                        val placeId = it.placeInfoList[selectedPlacePosition].placeId
                        addNewPlace(this, schedulePosition, placeId)
                    }
                }
            }else{
                binding.recyclerViewFSResult.visibility = View.GONE
                binding.textViewFSResultEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun addNewPlace(view: View, schedulePosition: Int, selectedPlaceId: Long) {
        val isDuplicate = scheduleFormViewModel.isPlaceAlreadyAdded(schedulePosition, selectedPlaceId)
        if (isDuplicate) {
            showSnackBar(view, "해당 여행지는 이미 일정에 추가되어 있습니다")
        }else {
            scheduleFormViewModel.getSearchedPlaceDetailInfo(schedulePosition, selectedPlaceId)
            findNavController().popBackStack()
        }
    }

    private fun settingPlaceSearchView(binding: FragmentFormSearchBinding){
        binding.searchViewFSResult.apply {
            editText.setOnEditorActionListener { textView, actionId, event ->
                if(event!=null && event.action == KeyEvent.ACTION_DOWN){
                    val word = editText.text.toString()
                    if(word.isEmpty()){
                        showSnackBar(this, "검색어를 입력해주세요")
                    }else{
                        binding.recyclerViewFSResult.visibility = View.VISIBLE
                        binding.textViewFSResultEmpty.visibility = View.GONE
                        scheduleFormViewModel.getPlaceSearchResult(word, 0, 10)
                    }
                }
                false
            }
        }
    }

    private fun showSnackBar(view: View, message : String ){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.text_secondary))
            .show()
    }


}