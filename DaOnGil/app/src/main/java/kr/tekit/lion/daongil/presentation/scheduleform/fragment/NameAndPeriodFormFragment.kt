package kr.tekit.lion.daongil.presentation.scheduleform.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentNameAndPeriodFormBinding
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import java.text.SimpleDateFormat
import java.util.*

class NameAndPeriodFormFragment : Fragment(R.layout.fragment_name_and_period_form) {
    // activity의 뷰모델
    private val scheduleFormViewModel : ScheduleFormViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNameAndPeriodFormBinding.bind(view)

        setPeriod(binding)
        proceedToNext(binding)
    }

    private fun proceedToNext(binding: FragmentNameAndPeriodFormBinding){
        binding.apply {
            buttonNPFNextStep.setOnClickListener {
                scheduleFormViewModel.setTitle(editTextNPFName.text.toString())

                val navController = findNavController()
                val action = NameAndPeriodFormFragmentDirections.actionNameAndPeriodFormFragmentToScheduleDetailsFormFragment()
                navController.navigate(action)
            }
        }
    }

    private fun setPeriod(binding: FragmentNameAndPeriodFormBinding) {
        binding.buttonNPFSetPeriod.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.DateRangePickerTheme)
                .setTitleText("조회기간을 설정해주세요")
                .build()

            dateRangePicker.show(requireActivity().supportFragmentManager, "ScheduleFormSetPeriod")
            dateRangePicker.addOnPositiveButtonClickListener {
                // viewModel에 시작일, 종료일 데이터 전달
                scheduleFormViewModel.setStartDate(Date(it.first))
                scheduleFormViewModel.setEndDate(Date(it.second))
                showPickedDates(binding)
            }
        }
    }

    private fun formatDateValue(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val formattedDate = dateFormat.format(date)

        return formattedDate
    }

    private fun showPickedDates(binding: FragmentNameAndPeriodFormBinding) {
        val startDate = scheduleFormViewModel.startDate.value
        val endDate = scheduleFormViewModel.endDate.value
        val startDateFormatted = startDate?.let {
            formatDateValue(startDate)
        }
        val endDateFormatted = endDate?.let {
            formatDateValue(endDate)
        }
        binding.buttonNPFSetPeriod.text =
            getString(R.string.picked_dates, startDateFormatted, endDateFormatted)
    }
}