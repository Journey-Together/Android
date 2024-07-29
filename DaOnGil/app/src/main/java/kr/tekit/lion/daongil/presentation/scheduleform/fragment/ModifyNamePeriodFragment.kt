package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentModifyNamePeriodBinding
import kr.tekit.lion.daongil.presentation.ext.showSnackbar
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ModifyScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ModifyScheduleFormViewModelFactory
import java.util.*


class ModifyNamePeriodFragment : Fragment(R.layout.fragment_modify_name_period) {
    private val viewModel: ModifyScheduleFormViewModel by activityViewModels { ModifyScheduleFormViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentModifyNamePeriodBinding.bind(view)

        initScheduleData(binding)
        setPeriod(binding)
        initButtonNextStep(binding)
    }

    private fun initScheduleData(binding: FragmentModifyNamePeriodBinding){
        val planId = requireActivity().intent.getLongExtra("planId", -1)
        viewModel.initScheduleDetailInfo(planId)

        initScheduleName(binding)
        initSchedulePeriod(binding)
    }

    private fun initScheduleName(binding: FragmentModifyNamePeriodBinding){
        viewModel.title.observe(viewLifecycleOwner){
            binding.editTextModifyName.setText(it)
        }
    }

    private fun initSchedulePeriod(binding: FragmentModifyNamePeriodBinding){
        viewModel.endDate.observe(viewLifecycleOwner){
            val pickedDates = viewModel.formatPickedDates()
            binding.buttonModifyPeriod.text = pickedDates
        }
    }

    private fun setPeriod(binding: FragmentModifyNamePeriodBinding) {
        binding.buttonModifyPeriod.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.DateRangePickerTheme)
                .setTitleText("조회기간을 설정해주세요")
                .build()

            dateRangePicker.show(requireActivity().supportFragmentManager, "ModifyScheduleFormSetPeriod")
            dateRangePicker.addOnPositiveButtonClickListener {
                viewModel.setStartDate(Date(it.first))
                viewModel.setEndDate(Date(it.second))
            }
        }
    }

    private fun initButtonNextStep(binding: FragmentModifyNamePeriodBinding){
        binding.apply {
            buttonModifyNPNextStep.setOnClickListener { view ->
                val isNameAndPeriodValidate = validateScheduleNameAndPeriod(this)

                if(isNameAndPeriodValidate){
                    Log.d("test1234", "sdfsdfsdfsdf")
                    val navController = findNavController()
                    val action = ModifyNamePeriodFragmentDirections.actionModifyNamePeriodFragmentToModifyScheduleDetailsFragment()
                    navController.navigate(action)
                }
            }
        }
    }

    private fun validateScheduleNameAndPeriod(binding: FragmentModifyNamePeriodBinding): Boolean {
        with(binding) {
            editTextModifyName.apply {
                val tempName = this.text.toString()

                if (tempName.isEmpty()) {
                    textInputModifyName.error = "제목은 1글자 이상 입력해주세요"
                    return false
                }
            }

            val hasStartDate = viewModel.hasStartDate()

            if (!hasStartDate) {
                buttonModifyPeriod.showSnackbar("여행 기간을 설정해주세요")
                return false
            } else {
                // 여행 기간 재설정했다면, 여행지 목록도 재설정 해준다.
                viewModel.isPeriodReset()
            }
        }

        return true
    }
}