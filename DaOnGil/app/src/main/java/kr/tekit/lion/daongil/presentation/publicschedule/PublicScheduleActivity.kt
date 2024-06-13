package kr.tekit.lion.daongil.presentation.publicschedule

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.databinding.ActivityPublicScheduleBinding
import kr.tekit.lion.daongil.presentation.publicschedule.adapter.PublicScheduleAdapter
import kr.tekit.lion.daongil.presentation.publicschedule.vm.PublicScheduleViewModel
import kr.tekit.lion.daongil.presentation.publicschedule.vm.PublicScheduleViewModelFactory
import kr.tekit.lion.daongil.presentation.schedule.ScheduleActivity

class PublicScheduleActivity : AppCompatActivity() {
    private val binding : ActivityPublicScheduleBinding by lazy {
        ActivityPublicScheduleBinding.inflate(layoutInflater)
    }

    private val viewModel: PublicScheduleViewModel by viewModels { PublicScheduleViewModelFactory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initToolbar()
        initPublicScheduleRecyclerView()
    }

    private fun initToolbar(){
        binding.toolbarPublicSchedule.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initPublicScheduleRecyclerView(){
        with(binding.recyclerViewPublicScheduleList){
            viewModel.openPlanList.observe(this@PublicScheduleActivity) {
                val scheduleAdpater = PublicScheduleAdapter(
                    onPublicScheduleClicked = { position ->
                        // 공개 일정 상세보기 페이지로 이동 it[position].planId
                        val intent = Intent(this@PublicScheduleActivity,ScheduleActivity::class.java)
                        intent.putExtra("planId", it[position].planId)
                        startActivity(intent)
                    }
                )
                scheduleAdpater.addItems(it)
                adapter = scheduleAdpater
            }
        }
    }
}