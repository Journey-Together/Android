package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleDetailsFormBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormScheduleAdapter
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ScheduleDetailsFormFragment : Fragment(R.layout.fragment_schedule_details_form) {
    private val scheduleFormViewModel : ScheduleFormViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleDetailsFormBinding.bind(view)

        initToolbar(binding)

        if(scheduleFormViewModel.schedule.value.isNullOrEmpty()){
            initView(binding)
        }else{ // 이미 데이터가 존재하는 경우 - smartCast가 되지 않고 있는데, 혹시 !!를 대체할 방법이 있는지?
            settingScheduleFormAdapter(binding, scheduleFormViewModel.schedule.value!!)
        }

        // observe data - 데이터가 변경되면 리사이클러뷰를 갱신시켜준다.
        scheduleFormViewModel.schedule.observe(viewLifecycleOwner){
            Log.d("test1234", "dfdfdfdfdfdfdfsdgsdfgwretwe vfs")
            binding.recyclerViewDF.adapter?.notifyDataSetChanged()
        }

    }

    private fun initToolbar(binding:FragmentScheduleDetailsFormBinding){
        binding.toolbarScheduleDetailsForm.setNavigationOnClickListener {
            // 이 때, viewModel에 있는 데이터 초기화?
            findNavController().popBackStack()
        }
    }

    private fun initView(binding:FragmentScheduleDetailsFormBinding){
        val startDate = scheduleFormViewModel.startDate.value
        val endDate = scheduleFormViewModel.endDate.value
        if(startDate!=null && endDate!=null){
            val days = getSchedulePeriod(startDate, endDate)
            val dailyScheduleList = initScheduleList(startDate, days)
            scheduleFormViewModel.setSchedule(dailyScheduleList)

            scheduleFormViewModel.schedule.value?.let {
                settingScheduleFormAdapter(binding, it )
            }
        }
    }

    private fun settingScheduleFormAdapter(
        binding: FragmentScheduleDetailsFormBinding, dailyScheduleList: List<DailySchedule>,
    ) {
        val navController = findNavController()
        binding.recyclerViewDF.adapter = FormScheduleAdapter(dailyScheduleList, requireActivity(), navController, scheduleFormViewModel)
    }

    private fun getSchedulePeriod(startDate:Date, endDate:Date) : Int{
        // 날짜 차이 계산
        val diffInMillies = kotlin.math.abs(endDate.time - startDate.time)
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillies).toInt()

        return diffInDays
    }

    // 리사이클러뷰에 필요한 DailySchedule List 초기화
    private fun initScheduleList(startDate: Date, days : Int) : List<DailySchedule>{
        val schedule = mutableListOf<DailySchedule>()
        for(day in 0..days){
            val dateInfo = getDayNString(startDate, day)
            // 0일차가 아닌 1일차부터 표기하기 위해 day+1
            schedule.add(DailySchedule(day+1, dateInfo, mutableListOf<FormPlace>()))
        }

        return schedule.toList()
    }

    private fun getDayNString(date: Date, n:Int) : String{
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, n)

        val dayString = SimpleDateFormat("M월 d일 E요일", Locale.KOREAN).format(calendar.time)

        return dayString
    }
}