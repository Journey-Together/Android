package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleConfirmFormBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.presentation.ext.showSnackbar
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormConfirmScheduleAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModelFactory

class ScheduleConfirmFormFragment : Fragment(R.layout.fragment_schedule_confirm_form) {
    private val scheduleFormViewModel: ScheduleFormViewModel by activityViewModels{
        ScheduleFormViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleConfirmFormBinding.bind(view)

        initToolbar(binding)
        initConfirmView(binding)
        initButtonSubmitSchedule(binding)
    }

    private fun initToolbar(binding: FragmentScheduleConfirmFormBinding) {
        binding.toolbarSConfirmForm.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initConfirmView(binding: FragmentScheduleConfirmFormBinding){
        val title = scheduleFormViewModel.getScheduleTitle()
        val period = scheduleFormViewModel.getSchedulePeriod()
        binding.apply {
            textViewSConfirmFormTitle.text = getString(R.string.text_selected_title, title)
            textViewSConfirmFormDate.text = getString(R.string.text_selected_period, period)
        }

        scheduleFormViewModel.schedule.observe(viewLifecycleOwner) {
            if(it != null){
                settingConfirmScheduleAdapter(binding, it)
            }
        }
    }

    private fun settingConfirmScheduleAdapter(
        binding: FragmentScheduleConfirmFormBinding,
        dailyScheduleList: List<DailySchedule>
    ) {
        binding.recyclerViewSConfirmForm.adapter = FormConfirmScheduleAdapter(dailyScheduleList)
    }

    private fun initButtonSubmitSchedule(binding: FragmentScheduleConfirmFormBinding){
        binding.buttonSConfirmFormSubmit.setOnClickListener { view ->
            scheduleFormViewModel.submitNewPlan{ _, requestFlag ->
                if(requestFlag){
                    requireActivity().setResult(Activity.RESULT_OK)
                    requireActivity().finish()
                }else{
                    view.showSnackbar("다시 시도해주세요")
                }
            }
        }
    }
}