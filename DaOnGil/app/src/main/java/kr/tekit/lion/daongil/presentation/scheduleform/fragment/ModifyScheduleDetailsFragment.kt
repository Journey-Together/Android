package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentModifyScheduleDetailsBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.presentation.home.DetailActivity
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormScheduleAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ModifyScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ModifyScheduleFormViewModelFactory


class ModifyScheduleDetailsFragment : Fragment(R.layout.fragment_modify_schedule_details) {
    private val viewModel: ModifyScheduleFormViewModel by activityViewModels {
        ModifyScheduleFormViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentModifyScheduleDetailsBinding.bind(view)

        initToolbar(binding)
        initView(binding)
        initButtonNextStep(binding)
    }

    private fun initToolbar(binding: FragmentModifyScheduleDetailsBinding){
        binding.toolbarModifyDetails.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initView(binding: FragmentModifyScheduleDetailsBinding){
        with(binding){
            val title = viewModel.getScheduleTitle()
            textViewModifyDetailTitle.text = getString(R.string.text_selected_title, title)
            val period = viewModel.getSchedulePeriod()
            textViewModifyDetailDate.text = getString(R.string.text_selected_period, period)
        }

        viewModel.schedule.observe(viewLifecycleOwner){
            it?.let {
                settingScheduleFormAdapter(binding, it)
            }
        }
    }

    private fun settingScheduleFormAdapter(
        binding: FragmentModifyScheduleDetailsBinding,
        dailyScheduleList: List<DailySchedule>
    ){
        binding.recyclerViewModifyDetail.adapter = FormScheduleAdapter(
            dailyScheduleList,
            onAddButtonClickListener = { schedulePosition ->
                val action = ModifyScheduleDetailsFragmentDirections.actionModifyScheduleDetailsFragmentToModifySearchFragment(
                    schedulePosition
                )
                findNavController().navigate(action)
            },
            onItemClickListener = { schedulePosition, placePosition ->
                val placeId = dailyScheduleList[schedulePosition].dailyPlaces[placePosition].placeId
                showPlaceDetail(placeId)
            },
            onRemoveButtonClickListener = { schedulePosition, placePosition ->
                viewModel.removePlace(schedulePosition, placePosition)
            }
        )
    }

    private fun initButtonNextStep(binding: FragmentModifyScheduleDetailsBinding){
        binding.buttonModifyDetailNextStep.setOnClickListener { view ->
            val action = ModifyScheduleDetailsFragmentDirections.actionModifyScheduleDetailsFragmentToModifyScheduleConfirmFragment()
            findNavController().navigate(action)
        }
    }

    private fun showPlaceDetail(placeId: Long){
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("detailPlaceId", placeId)
        startActivity(intent)
    }
}