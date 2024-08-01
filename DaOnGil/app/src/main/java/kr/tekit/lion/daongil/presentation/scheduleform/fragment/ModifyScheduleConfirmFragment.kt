package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentModifyScheduleConfirmBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.presentation.ext.showSnackbar
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormConfirmScheduleAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ModifyScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ModifyScheduleFormViewModelFactory

class ModifyScheduleConfirmFragment : Fragment(R.layout.fragment_modify_schedule_confirm) {

    private val viewModel: ModifyScheduleFormViewModel by activityViewModels {
        ModifyScheduleFormViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentModifyScheduleConfirmBinding.bind(view)

        val planId = requireActivity().intent.getLongExtra("planId", -1)

        initToolbar(binding)
        initView(binding)
        initButtonSubmit(planId, binding)
    }

    private fun initToolbar(binding: FragmentModifyScheduleConfirmBinding) {
        binding.toolbarModifyConfirm.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initView(binding: FragmentModifyScheduleConfirmBinding){
        val title = viewModel.getScheduleTitle()
        val period = viewModel.getSchedulePeriod()
        binding.apply {
            textViewModifyConfirmTitle.text = getString(R.string.text_selected_title, title)
            textViewModifyConfirmDate.text = getString(R.string.text_selected_period, period)
        }

        viewModel.schedule.observe(viewLifecycleOwner){
            if(it != null) settingConfirmScheduleAdapter(binding, it)
        }
    }

    private fun settingConfirmScheduleAdapter(
        binding: FragmentModifyScheduleConfirmBinding,
        dailyScheduleList: List<DailySchedule>
    ){
        binding.recyclerViewModifyConfirm.adapter = FormConfirmScheduleAdapter(dailyScheduleList)
    }

    private fun initButtonSubmit(planId: Long, binding: FragmentModifyScheduleConfirmBinding){
        binding.buttonModifyConfirmSubmit.setOnClickListener { view ->
            viewModel.submitRevisedSchedule(planId){ _, flag ->
                if(flag){
                    view.showSnackbar("여행 일정이 수정되었습니다")

                    // TO DO 예인님이 수정할 예정
                    requireActivity().setResult(Activity.RESULT_OK)
                    requireActivity().finish()
                }else{
                    view.showSnackbar("다시 시도해 주세요")
                }
            }
        }
    }
}