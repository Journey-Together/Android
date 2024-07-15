package kr.tekit.lion.daongil.presentation.main.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentScheduleMainBinding
import kr.tekit.lion.daongil.domain.model.MyMainSchedule
import kr.tekit.lion.daongil.presentation.ext.repeatOnStarted
import kr.tekit.lion.daongil.presentation.login.LogInState
import kr.tekit.lion.daongil.presentation.main.adapter.ScheduleMyAdapter
import kr.tekit.lion.daongil.presentation.main.adapter.SchedulePublicAdapter
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.main.vm.ScheduleMainViewModel
import kr.tekit.lion.daongil.presentation.main.vm.ScheduleMainViewModelFactory
import kr.tekit.lion.daongil.presentation.myschedule.MyScheduleActivity
import kr.tekit.lion.daongil.presentation.publicschedule.PublicScheduleActivity
import kr.tekit.lion.daongil.presentation.schedule.ScheduleActivity
import kr.tekit.lion.daongil.presentation.scheduleform.ScheduleFormActivity
import kr.tekit.lion.daongil.presentation.schedulereview.WriteScheduleReviewActivity

class ScheduleMainFragment : Fragment(R.layout.fragment_schedule_main), ConfirmDialogInterface {

    private var isUser = true

    private val viewModel: ScheduleMainViewModel by viewModels {
        ScheduleMainViewModelFactory(
            requireActivity()
        )
    }


    private val scheduleReviewLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            view?.let {
                Snackbar.make(it, "후기가 저장되었습니다", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.text_secondary))
                    .show()
            }
        }
    }

    private val scheduleFormLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            view?.let{
                Snackbar.make(it, "일정이 저장되었습니다", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.text_secondary))
                    .show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getMyMainPlanList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScheduleMainBinding.bind(view)

        // initView(binding)
        settingRecyclerView(binding)
        initNewScheduleButton(binding)

        initMoreViewClickListener(binding)

        repeatOnStarted {
            viewModel.loginState.collect { uiState ->
                when (uiState) {
                    is LogInState.Checking -> {
                        return@collect
                    }

                    is LogInState.LoggedIn -> {
                        isUser = true
                        viewModel.getMyMainPlanList()
                    }

                    is LogInState.LoginRequired -> {
                        isUser = false
                        binding.textViewMyScheduleMore.visibility = View.INVISIBLE
                        displayAddSchedulePrompt(binding)
                    }
                }
            }
        }

    }

    /*private fun initView(binding: FragmentScheduleMainBinding) {
        if (isUser) {
            // 회원의 일정 정보를 불러온다
            viewModel.getMyMainPlanList()
        } else { // 비회원
            binding.textViewMyScheduleMore.visibility = View.INVISIBLE
            displayAddSchedulePrompt(binding)
        }
    }*/


    private fun settingRecyclerView(binding: FragmentScheduleMainBinding) {

        with(binding) {
            viewModel.myMainPlanList.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    displayAddSchedulePrompt(binding)
                    return@observe
                }

                binding.recyclerViewMySchedule.visibility = View.VISIBLE
                binding.cardViewEmptySchedule.visibility = View.GONE

                recyclerViewMySchedule.apply {
                    val myscheduleAdapter = ScheduleMyAdapter(
                        itemClickListener = { position ->
                            val planId = it[position]?.planId
                            planId?.let {
                                initScheduleDetailActivity(it)
                            }
                        },
                        reviewClickListener = { position ->
                            val intent = Intent(requireActivity(), WriteScheduleReviewActivity::class.java)
                            intent.putExtra("planId", it.get(position)?.planId)
                            scheduleReviewLauncher.launch(intent)
                        }
                    )
                    myscheduleAdapter.addItems(it as List<MyMainSchedule>)
                    adapter = myscheduleAdapter

                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

            }

            // 공개 일정
            recyclerViewPublicSchedule.apply {
                viewModel.openPlanList.observe(viewLifecycleOwner) {
                    val schedulePublicAdapter = SchedulePublicAdapter(
                        itemClickListener = { position ->
                            val planId = it[position].planId
                            initScheduleDetailActivity(planId.toLong())
                        }
                    )
                    schedulePublicAdapter.addItems(it)
                    adapter = schedulePublicAdapter
                }
            }
        }
    }

    private fun displayAddSchedulePrompt(binding: FragmentScheduleMainBinding) {
        // 등록된 스케쥴이 없는 경우 -> '내 일정' 리사이클러뷰를 숨겨주고, 일정 추가 권유하는 cardView를 보여준다.
        binding.recyclerViewMySchedule.visibility = View.GONE
        binding.cardViewEmptySchedule.visibility = View.VISIBLE
    }

    private fun createNewSchedule() {
        if (isUser) {
            // 일정 추가 화면으로 이동
            val intent = Intent(requireActivity(), ScheduleFormActivity::class.java)
            scheduleFormLauncher.launch(intent)
        } else {
            // 비회원 -> 로그인 다이얼로그
            displayLoginDialog()
        }
    }

    private fun initNewScheduleButton(binding: FragmentScheduleMainBinding) {
        binding.buttonAddSchedule.setOnClickListener {
            createNewSchedule()
        }

        binding.textViewAddSchedule.setOnClickListener {
            createNewSchedule()
        }
    }

    private fun displayLoginDialog() {
        val dialog = ConfirmDialog(
            this,
            "로그인이 필요해요!",
            "여행 일정을 추가/관리하고 싶다면\n로그인을 진행해주세요",
            "로그인하기",
            R.color.primary,
            R.color.text_primary
        )
        dialog.isCancelable = true
        dialog.show(activity?.supportFragmentManager!!, "ScheduleLoginDialog")
    }

    override fun onPosBtnClick() {
        // login 화면으로 이동
    }


    private fun initMoreViewClickListener(binding: FragmentScheduleMainBinding) {
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

    private fun initScheduleDetailActivity(planId: Long){
        val intent = Intent(requireActivity(), ScheduleActivity::class.java)
        intent.putExtra("planId", planId)
        startActivity(intent)
    }

}