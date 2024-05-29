package kr.tekit.lion.daongil.presentation.main.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleMainBinding
import kr.tekit.lion.daongil.presentation.main.adapter.ScheduleMyAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.SchedulePublicAdapter

class ScheduleMainFragment : Fragment(R.layout.fragment_schedule_main) {
    private var isUser = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleMainBinding.bind(view)

        initView(binding)
        settingRecyclerView(binding, requireContext())
    }

    private fun initView(binding : FragmentScheduleMainBinding){
        if(isUser){
            // 회원의 일정 정보를 불러온다

        }else{ // 비회원
            displayAddSchedulePrompt(binding)
        }
    }


    private fun settingRecyclerView(binding : FragmentScheduleMainBinding, context: Context){
        binding.apply {
            recyclerViewMySchedule.apply {
                adapter = ScheduleMyAdapter()
                layoutManager = LinearLayoutManager(context)
            }

            recyclerViewPublicSchedule.apply {
                adapter = SchedulePublicAdapter()
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun displayAddSchedulePrompt(binding : FragmentScheduleMainBinding){
        // 등록된 스케쥴이 없는 경우 -> '내 일정' 리사이클러뷰를 숨겨주고, 일정 추가 권유하는 cardView를 보여준다.
        binding.recyclerViewMySchedule.visibility = View.GONE
        binding.cardViewEmptySchedule.visibility = View.VISIBLE
    }

    // 로그인 팝업 ( #30 코드 활용할 예정)

    // 일정 추가 버튼 click listener
}