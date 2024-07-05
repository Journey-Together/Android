package kr.tekit.lion.daongil.presentation.scheduleform.fragment


import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentNameAndPeriodFormBinding
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class NameAndPeriodFormFragment : Fragment(R.layout.fragment_name_and_period_form) {
    // activity의 뷰모델
    private val scheduleFormViewModel: ScheduleFormViewModel by activityViewModels { ScheduleFormViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNameAndPeriodFormBinding.bind(view)

        initToolbar(binding)
        initView(binding)
        setPeriod(binding)
        proceedToNext(binding)

        showSoftInput(binding.editTextNPFName)
    }

    private fun initToolbar(binding: FragmentNameAndPeriodFormBinding) {
        binding.toolbarNPF.setNavigationOnClickListener {
            requireActivity().setResult(Activity.RESULT_CANCELED)
            requireActivity().finish()
        }
    }

    private fun initView(binding: FragmentNameAndPeriodFormBinding){
        with(binding){
            editTextNPFName.addTextChangedListener {
                clearErrorMessage(textInputNPFName)
            }
        }
    }

    private fun proceedToNext(binding: FragmentNameAndPeriodFormBinding) {
        binding.apply {
            buttonNPFNextStep.setOnClickListener {
                val isNameAndPeriodValidate = validateScheduleNameAndPeriod(this)
                if (isNameAndPeriodValidate) {
                    scheduleFormViewModel.setTitle(editTextNPFName.text.toString())

                    val navController = findNavController()
                    val action =
                        NameAndPeriodFormFragmentDirections.actionNameAndPeriodFormFragmentToScheduleDetailsFormFragment()
                    navController.navigate(action)
                }
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

    private fun validateScheduleNameAndPeriod(binding: FragmentNameAndPeriodFormBinding): Boolean {
        with(binding) {
            editTextNPFName.apply {
                val tempName = this.text.toString()

                if (tempName.isEmpty()) {
                    textInputNPFName.error = "제목은 1글자 이상 입력해주세요"
                    return false
                }
            }

            val hasStartDate = scheduleFormViewModel.hasStartDate()

            if (!hasStartDate) {
                showSnackBar(buttonNPFSetPeriod, "여행 기간을 설정해주세요")
                return false
            }
        }

        return true
    }

    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.text_secondary))
            .show()
    }

    private fun showSoftInput(view: View) {
        view.requestFocus()

        thread {
            SystemClock.sleep(400)
            val inputMethodManager =
                requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, 0)
        }
    }

    private fun clearErrorMessage(textInputLayout: TextInputLayout){
        textInputLayout.error = null
    }
}