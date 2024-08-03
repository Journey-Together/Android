package kr.tekit.lion.daongil.presentation.publicschedule

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.databinding.ActivityPublicScheduleBinding
import kr.tekit.lion.daongil.presentation.ext.addOnScrollEndListener
import kr.tekit.lion.daongil.presentation.ext.gridAddOnScrollEndListener
import kr.tekit.lion.daongil.presentation.publicschedule.adapter.PublicScheduleAdapter
import kr.tekit.lion.daongil.presentation.publicschedule.vm.PublicScheduleViewModel
import kr.tekit.lion.daongil.presentation.publicschedule.vm.PublicScheduleViewModelFactory
import kr.tekit.lion.daongil.presentation.schedule.ScheduleDetailActivity

class PublicScheduleActivity : AppCompatActivity() {
    private val binding: ActivityPublicScheduleBinding by lazy {
        ActivityPublicScheduleBinding.inflate(layoutInflater)
    }

    private val viewModel: PublicScheduleViewModel by viewModels { PublicScheduleViewModelFactory() }
    private lateinit var scheduleAdapter: PublicScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        initPublicScheduleRecyclerView()
        scrollPublicSchedule()
    }

    private fun initToolbar() {
        binding.toolbarPublicSchedule.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initPublicScheduleRecyclerView() {
        scheduleAdapter = PublicScheduleAdapter { position ->
            // 공개 일정 상세보기 페이지로 이동
            val intent = Intent(this@PublicScheduleActivity, ScheduleDetailActivity::class.java)
            intent.putExtra("planId", scheduleAdapter.currentList[position].planId)
            startActivity(intent)
        }

        binding.recyclerViewPublicScheduleList.adapter = scheduleAdapter

        viewModel.openPlanList.observe(this@PublicScheduleActivity) { newList ->
            val rvState = binding.recyclerViewPublicScheduleList.layoutManager?.onSaveInstanceState()
            scheduleAdapter.submitList(newList) {
                binding.recyclerViewPublicScheduleList.layoutManager?.onRestoreInstanceState(rvState)
            }
        }
    }

    private fun scrollPublicSchedule() {
        binding.recyclerViewPublicScheduleList.gridAddOnScrollEndListener {
            viewModel.getOpenPlanListPaging()
        }
    }
}