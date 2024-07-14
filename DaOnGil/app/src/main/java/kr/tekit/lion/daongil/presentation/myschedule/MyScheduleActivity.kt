package kr.tekit.lion.daongil.presentation.myschedule

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kr.tekit.lion.daongil.databinding.ActivityMyScheduleBinding
import kr.tekit.lion.daongil.domain.model.MySchedule
import kr.tekit.lion.daongil.presentation.myschedule.adapter.MyScheduleElapsedAdapter
import kr.tekit.lion.daongil.presentation.myschedule.adapter.MyScheduleUpcomingAdapter
import kr.tekit.lion.daongil.presentation.myschedule.vm.MyScheduleViewModel
import kr.tekit.lion.daongil.presentation.myschedule.vm.MyScheduleViewModelFactory
import kr.tekit.lion.daongil.presentation.schedulereview.WriteScheduleReviewActivity

class MyScheduleActivity : AppCompatActivity() {

    private val viewModel : MyScheduleViewModel by viewModels { MyScheduleViewModelFactory() }

    private val binding : ActivityMyScheduleBinding by lazy {
        ActivityMyScheduleBinding.inflate(layoutInflater)
    }

    private val upcomingAdapter by lazy {
        MyScheduleUpcomingAdapter{ planPosition ->
            // todo - ScheduleActivity로 이동하는 함수
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        settingToolbar()
        settingMyScheduleTab()
        settingUpcomingScheduleAdapter()
    }

    private fun settingToolbar(){
        binding.toolbarMySchedule.setNavigationOnClickListener {
            finish()
        }
    }

    private fun settingMyScheduleTab(){
        binding.tabLayoutMySchedule.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){ // TO DO - List 없을 때 처리할 것
                    0 -> settingUpcomingScheduleAdapter()
                    1 -> settingElapsedScheduleAdapter()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) { }

            override fun onTabReselected(tab: TabLayout.Tab) { }
        })
    }

    private fun settingUpcomingScheduleAdapter(){
        binding.recyclerViewMyScheduleList.apply {
            adapter = upcomingAdapter
        }

        viewModel.upcomingSchedules.observe(this@MyScheduleActivity){
            if((it?.size ?: 0) > 0){
                binding.layoutMyScheduleEmpty.visibility = View.GONE
                binding.recyclerViewMyScheduleList.visibility = View.VISIBLE
                upcomingAdapter.submitList(it)
            }else {
                binding.layoutMyScheduleEmpty.visibility = View.VISIBLE
                binding.recyclerViewMyScheduleList.visibility = View.GONE
            }
        }
    }

    private fun settingElapsedScheduleAdapter() {
        val elapsedSchedule = listOf(
            MySchedule(1, "낭만 가득한 전주 여행", "2024-06-02", "2024-06-04", "http://tong.visitkorea.or.kr/cms/resource/21/2657021_image2_1.jpg", null, false),
            MySchedule(2, "자연과 혁신이 공존하는 나주", "2024-05-20", "2024-05-24", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", null, false),
            MySchedule(3, "제주 둘레길 여행", "2024-04-09", "2024-04-11", "", null, true)
        )

        binding.recyclerViewMyScheduleList.adapter = MyScheduleElapsedAdapter(
            elapsedSchedule,
            onReviewButtonClicked = { planId, hasReview ->
                if (hasReview != null && hasReview == false) {
                    val intent = Intent(this, WriteScheduleReviewActivity::class.java)
                    intent.putExtra("planId", planId)
                    startActivity(intent)
                } else {
                    // TO DO 일정화면으로 이동
                }
            },
            onScheduleItemClicked = { planId ->
                // TO DO 일정화면으로 이동
            }
        )
    }
}