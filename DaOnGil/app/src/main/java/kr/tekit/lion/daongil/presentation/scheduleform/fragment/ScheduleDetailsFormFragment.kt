package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleDetailsFormBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.presentation.home.DetailActivity
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormScheduleAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ScheduleDetailsFormFragment : Fragment(R.layout.fragment_schedule_details_form) {

    private val scheduleFormViewModel: ScheduleFormViewModel by activityViewModels{
        ScheduleFormViewModelFactory()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            resetForm()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleDetailsFormBinding.bind(view)

        initToolbar(binding)

        if (scheduleFormViewModel.schedule.value.isNullOrEmpty()) {
            initScheduleList()
            initView(binding)
        } else { // 이미 데이터가 존재하는 경우
            initView(binding)
        }

        initButtonSubmit(binding)
    }

    private fun initToolbar(binding: FragmentScheduleDetailsFormBinding) {
        binding.toolbarScheduleDetailsForm.setNavigationOnClickListener {
            resetForm()
        }
    }

    private fun resetForm(){
        // 날짜 선택화면으로 돌아가는 경우 제목, 기간, 리스트 초기화
        scheduleFormViewModel.setTitle(null)
        scheduleFormViewModel.setStartDate(null)
        scheduleFormViewModel.setEndDate(null)
        scheduleFormViewModel.setSchedule(null)
        findNavController().popBackStack()
    }

    private fun initScheduleList() {
        // 선택된 기간을 기준으로 리스트 초기화
        val startDate = scheduleFormViewModel.startDate.value
        val endDate = scheduleFormViewModel.endDate.value
        if (startDate != null && endDate != null) {
            val days = getSchedulePeriod(startDate, endDate)
            val dailyScheduleList = initScheduleList(startDate, days)
            scheduleFormViewModel.setSchedule(dailyScheduleList)
        }
    }

    private fun initView(binding: FragmentScheduleDetailsFormBinding) {
        // ViewModel의 리스트를 토대로 리사이클러뷰 세팅
        scheduleFormViewModel.schedule.observe(viewLifecycleOwner) {
            if (it != null) { // 데이터가 바뀔 때마다 recyclerView 갱신?
                settingScheduleFormAdapter(binding, it)
            }
        }
    }

    private fun settingScheduleFormAdapter(
        binding: FragmentScheduleDetailsFormBinding,
        dailyScheduleList: List<DailySchedule>,
    ) {
        binding.recyclerViewDF.adapter = FormScheduleAdapter(
            dailyScheduleList,
            onAddButtonClickListener = { schedulePosition ->
                // 몇 번째 일정에 여행지를 추가하는지 파악하기 위해 schedulePosition 을 전달해준다.
                val action =
                    ScheduleDetailsFormFragmentDirections.actionScheduleDetailsFormFragmentToFormSearchFragment(
                        schedulePosition
                    )
                findNavController().navigate(action)
            },
            onItemClickListener = { schedulePosition, placePosition ->
                val placeId = dailyScheduleList[schedulePosition].dailyPlaces[placePosition].placeId
                showPlaceDetail(placeId)
            },
            onRemoveButtonClickListener = { schedulePosition, placePosition ->
                // viewModel에서 해당 place 제거
                scheduleFormViewModel.removePlace(schedulePosition, placePosition)
            }
        )
    }

    private fun getSchedulePeriod(startDate: Date, endDate: Date): Int {
        // 날짜 차이 계산
        val diffInMillies = kotlin.math.abs(endDate.time - startDate.time)
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillies).toInt()

        return diffInDays
    }

    // 리사이클러뷰에 필요한 DailySchedule List 초기화
    private fun initScheduleList(startDate: Date, days: Int): List<DailySchedule> {
        val schedule = mutableListOf<DailySchedule>()
        for (day in 0..days) {
            val dateInfo = getDayNString(startDate, day)
            // 0일차가 아닌 1일차부터 표기하기 위해 day+1
            schedule.add(DailySchedule(day + 1, dateInfo, mutableListOf<FormPlace>()))
        }

        return schedule.toList()
    }

    private fun getDayNString(date: Date, n: Int): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, n)

        val dayString = SimpleDateFormat("M월 d일 E요일", Locale.KOREAN).format(calendar.time)

        return dayString
    }

    private fun initButtonSubmit(binding: FragmentScheduleDetailsFormBinding){
        binding.buttonDFSubmit.setOnClickListener { view ->
            scheduleFormViewModel.submitNewPlan{ _, requestFlag ->
                if(requestFlag){
                    requireActivity().setResult(Activity.RESULT_OK)
                    requireActivity().finish()
                }else{
                    showSnackBar(view, "다시 시도해주세요")
                }
            }
        }
    }

    private fun showSnackBar(view: View, message : String ){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.text_secondary))
            .show()
    }

    private fun showPlaceDetail(placeId: Long){
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("detailPlaceId", placeId.toInt())
        startActivity(intent)
    }
}