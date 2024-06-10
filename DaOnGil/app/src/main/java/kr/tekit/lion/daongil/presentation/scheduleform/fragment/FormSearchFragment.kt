package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentFormSearchBinding
import kr.tekit.lion.daongil.domain.model.BookmarkedPlace
import kr.tekit.lion.daongil.domain.model.FormSearchedPlace
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormBookmarkedPlacesAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormSearchResultAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel


class FormSearchFragment : Fragment(R.layout.fragment_form_search) {
    private val args: FormSearchFragmentArgs by navArgs()
    private val scheduleFormViewModel : ScheduleFormViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 추가해야하는 일정의 position
        val schedulePosition = args.schedulePosition

        val binding = FragmentFormSearchBinding.bind(view)

        initToolbar(binding)

        val navController = findNavController()

        settingBookmarkedRV(binding, navController, schedulePosition)
        settingSearchResultRV(binding, navController, schedulePosition)
    }

    private fun initToolbar(binding: FragmentFormSearchBinding){
        binding.toolbarFormSearch.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun settingBookmarkedRV(binding: FragmentFormSearchBinding, navController: NavController, schedulePosition: Int ){
        val places = listOf(
            BookmarkedPlace(0, "보신각 터"),
            BookmarkedPlace(1, "부산 영화의 전당"),
            BookmarkedPlace(2, "광복로문화패션거리"),
            BookmarkedPlace(3, "상록해수욕장"),
            BookmarkedPlace(4, "고양 어울림누리"),
        )

        binding.recyclerViewFSBookmark.adapter = FormBookmarkedPlacesAdapter(places, navController, scheduleFormViewModel, schedulePosition)
        binding.recyclerViewFSBookmark.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    private fun settingSearchResultRV(binding: FragmentFormSearchBinding, navController: NavController, schedulePosition: Int){
        val searchResults = mutableListOf(
            FormSearchedPlace(0, "", "보신각 터", "관광지"),
            FormSearchedPlace(1, "", "광복로문화패션거리", "관광지"),
            FormSearchedPlace(2, "", "상록해수욕장", "관광지"),
            FormSearchedPlace(3, "", "원조 돼지국밥", "식당"),
            FormSearchedPlace(4, "", "냉채족발", "식당"),
            FormSearchedPlace(5, "", "부산 OOOOO 호텔", "숙박시설"),
        )

        binding.recyclerViewFSResult.adapter = FormSearchResultAdapter(searchResults, navController, scheduleFormViewModel, schedulePosition)
        binding.recyclerViewFSResult.layoutManager = LinearLayoutManager(requireActivity())
    }

}