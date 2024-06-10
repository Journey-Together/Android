package kr.tekit.lion.daongil.presentation.main.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleMainBinding
import kr.tekit.lion.daongil.presentation.main.customview.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.customview.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.main.adapter.ScheduleMyAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.SchedulePublicAdapter
import kr.tekit.lion.daongil.presentation.main.vm.ScheduleMainViewModel
import kr.tekit.lion.daongil.presentation.main.vm.ScheduleMainViewModelFactory
import kr.tekit.lion.daongil.presentation.myschedule.MyScheduleActivity
import kr.tekit.lion.daongil.presentation.publicschedule.PublicScheduleActivity
import kr.tekit.lion.daongil.presentation.schedule.ScheduleActivity
import kr.tekit.lion.daongil.presentation.scheduleform.ScheduleFormActivity

class ScheduleMainFragment : Fragment(R.layout.fragment_schedule_main), ConfirmDialogInterface,
    ScheduleMyAdapter.OnScheduleMainItemClickListener {

    private var isUser = true

    private val viewModel: ScheduleMainViewModel by viewModels { ScheduleMainViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleMainBinding.bind(view)

        initView(binding)
        settingRecyclerView(binding, requireContext())
        initNewScheduleButton(binding)

        initMoreViewClickListener(binding)
    }

    private fun initView(binding : FragmentScheduleMainBinding){
        if(isUser){
            // 회원의 일정 정보를 불러온다

        }else{ // 비회원
            binding.textViewMyScheduleMore.visibility = View.INVISIBLE
            displayAddSchedulePrompt(binding)
        }
    }


    private fun settingRecyclerView(binding : FragmentScheduleMainBinding, context: Context){

        with(binding){
            recyclerViewMySchedule.apply {
                adapter = ScheduleMyAdapter(this@ScheduleMainFragment)
                layoutManager = LinearLayoutManager(context)
            }

            recyclerViewPublicSchedule.apply {
                val schedulePublicAdapter = SchedulePublicAdapter()
                viewModel.openPlanList.observe(viewLifecycleOwner) {
                    schedulePublicAdapter.addItems(it)
                }
                    adapter = schedulePublicAdapter
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
            val intent = Intent(requireActivity(), ScheduleFormActivity::class.java)
            startActivity(intent)
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


    private fun initMoreViewClickListener(binding : FragmentScheduleMainBinding){
        binding.apply {
            textViewMyScheduleMore.setOnClickListener {
                val intent = Intent(requireActivity(), MyScheduleActivity::class.java)
                startActivity(intent)
            }
            textViewPublicScheduleMore.setOnClickListener {
                val intent = Intent(requireActivity(), PublicScheduleActivity::class.java)
                startActivity(intent)
            }
        }
    }
    
    override fun onScheduleMainItemClick(scheduleIdx: Int) {
        val intent = Intent(requireActivity(), ScheduleActivity::class.java)
        // to do - 여행 일정 idx 전달
//        intent.putExtra("placeId", 1)
        startActivity(intent)
    }
}