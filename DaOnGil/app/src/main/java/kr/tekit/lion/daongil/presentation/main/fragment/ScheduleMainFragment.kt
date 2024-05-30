package kr.tekit.lion.daongil.presentation.main.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleMainBinding
import kr.tekit.lion.daongil.presentation.main.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.main.adapter.ScheduleMyAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.SchedulePublicAdapter

class ScheduleMainFragment : Fragment(R.layout.fragment_schedule_main), ConfirmDialogInterface {
    private var isUser = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleMainBinding.bind(view)

        initView(binding)
        settingRecyclerView(binding, requireContext())
        initNewScheduleButton(binding)
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

    private fun createNewSchedule(){
        if(isUser){
            // 일정 추가 화면으로 이동
        }else{
            // 비회원 -> 로그인 다이얼로그
            displayLoginDialog()
        }
    }

    private fun initNewScheduleButton(binding:FragmentScheduleMainBinding){
        binding.buttonAddSchedule.setOnClickListener {
            createNewSchedule()
        }

        binding.textViewAddSchedule.setOnClickListener {
            createNewSchedule()
        }
    }

    private fun displayLoginDialog(){
        val dialog = ConfirmDialog(
            this,
            "로그인이 필요해요!",
            "여행 일정을 추가/관리하고 싶다면\n로그인을 진행해주세요",
            "로그인하기",
            R.color.primary,
            R.color.text_primary)
        dialog.isCancelable = true
        dialog.show(activity?.supportFragmentManager!!, "ScheduleLoginDialog")
    }

    override fun onPosBtnClick() {
        // login 화면으로 이동
    }
}