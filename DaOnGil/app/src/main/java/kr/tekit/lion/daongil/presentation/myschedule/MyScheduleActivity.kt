package kr.tekit.lion.daongil.presentation.myschedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kr.tekit.lion.daongil.databinding.ActivityMyScheduleBinding
import kr.tekit.lion.daongil.domain.model.MySchedule
import kr.tekit.lion.daongil.presentation.myschedule.adapter.MyScheduleElapsedAdapter
import kr.tekit.lion.daongil.presentation.myschedule.adapter.MyScheduleUpcomingAdapter

class MyScheduleActivity : AppCompatActivity() {
    private val binding : ActivityMyScheduleBinding by lazy {
        ActivityMyScheduleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        settingToolbar()
        settingMyScheduleTab()
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
        binding.recyclerViewMyScheduleList.adapter = MyScheduleUpcomingAdapter()
    }

    private fun settingElapsedScheduleAdapter() {
        val elapsedSchedule = listOf(
            MySchedule(1, "여행 제목", "2024-06-02", "2024-06-04", "http://tong.visitkorea.or.kr/cms/resource/21/2657021_image2_1.jpg", null, false),
            MySchedule(2, "여행 제목", "2024-06-02", "2024-06-04", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", null, false),
            MySchedule(3, "여행 제목", "2024-06-02", "2024-06-04", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", null, false)
        )

        binding.recyclerViewMyScheduleList.adapter = MyScheduleElapsedAdapter(
            elapsedSchedule,
            onReviewButtonClicked = { planId, hasReview ->
                if (hasReview != null && hasReview == false) {
                    // TO DO 리뷰 작성화면으로 이동
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