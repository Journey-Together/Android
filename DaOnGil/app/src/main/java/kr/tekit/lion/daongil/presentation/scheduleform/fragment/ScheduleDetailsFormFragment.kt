package kr.tekit.lion.daongil.presentation.scheduleform.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleDetailsFormBinding
import kr.tekit.lion.daongil.domain.model.DailySchedule
import kr.tekit.lion.daongil.domain.model.FormPlace
import kr.tekit.lion.daongil.presentation.scheduleform.adapter.FormScheduleAdapter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ScheduleDetailsFormFragment : Fragment(R.layout.fragment_schedule_details_form) {
    val args: ScheduleDetailsFormFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleDetailsFormBinding.bind(view)

        val title = args.title
        val startDate = args.startDate
        val endDate = args.endDate

        val days = getSchedulePeriod(startDate, endDate)
        val dailyScheduleList = initScheduleList(startDate, days)

        settingScheduleFormAdapter(binding, dailyScheduleList)
    }

    private fun settingScheduleFormAdapter(
        binding: FragmentScheduleDetailsFormBinding, dailyScheduleList: List<DailySchedule>
    ) {
        binding.recyclerViewDF.adapter = FormScheduleAdapter(dailyScheduleList, requireActivity(), findNavController())
        binding.recyclerViewDF.layoutManager = LinearLayoutManager(requireActivity())
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