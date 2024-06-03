package kr.tekit.lion.daongil.presentation.scheduleform.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentNameAndPeriodFormBinding
import java.text.SimpleDateFormat
import java.util.*

class NameAndPeriodFormFragment : Fragment(R.layout.fragment_name_and_period_form) {
    var startDate = Date()
    var endDate = Date()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNameAndPeriodFormBinding.bind(view)

        setPeriod(binding)
        proceedToNext(binding)
    }

    private fun proceedToNext(binding: FragmentNameAndPeriodFormBinding){
        binding.apply {
            buttonNPFNextStep.setOnClickListener {
                val navController = findNavController()

                val title = editTextNPFName.text.toString()

                val action = NameAndPeriodFormFragmentDirections.actionNameAndPeriodFormFragmentToScheduleDetailsFormFragment(startDate, endDate, title)
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
                startDate = Date(it.first)
                endDate = Date(it.second)

                showPickedDates(binding, Date(it.first), Date(it.second))
            }
        }
    }

    private fun formatDateValue(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val formattedDate = dateFormat.format(date)

        return formattedDate
    }

    private fun showPickedDates(binding: FragmentNameAndPeriodFormBinding, startDate: Date, endDate: Date) {
        val startDateFormatted = formatDateValue(startDate)
        val endDateFormatted = formatDateValue(endDate)
        binding.buttonNPFSetPeriod.text =
            getString(R.string.picked_dates, startDateFormatted, endDateFormatted)
    }
}