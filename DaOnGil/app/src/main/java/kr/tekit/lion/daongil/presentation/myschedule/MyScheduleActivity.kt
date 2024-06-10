package kr.tekit.lion.daongil.presentation.myschedule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kr.tekit.lion.daongil.databinding.ActivityMyScheduleBinding
import kr.tekit.lion.daongil.domain.model.MySchedule
import kr.tekit.lion.daongil.presentation.myschedule.adapter.MyScheduleElapsedAdapter
import kr.tekit.lion.daongil.presentation.myschedule.adapter.MyScheduleUpcomingAdapter
import kr.tekit.lion.daongil.presentation.schedulereview.WriteScheduleReviewActivity

class MyScheduleActivity : AppCompatActivity() {
    private val binding : ActivityMyScheduleBinding by lazy {
        ActivityMyScheduleBinding.inflate(layoutInflater)
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
        val upcomingSchedule = listOf(
            MySchedule(1, "오감만족 강릉 여행", "2024-07-02", "2024-07-04", "http://tong.visitkorea.or.kr/cms/resource/21/2657021_image2_1.jpg", "D-5", null),
            MySchedule(2, "천년고도 꽃길 - 경주", "2024-07-20", "2024-07-24", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", "D-10", null),
            MySchedule(3, "알록달록 여름빛 제주여행", "2024-08-09", "2024-08-11", "http://tong.visitkorea.or.kr/cms/resource/40/2657021_image2_1.jpg", "D-30", null)
        )
        binding.recyclerViewMyScheduleList.adapter = MyScheduleUpcomingAdapter(
            upcomingSchedule,
            onScheduleItemClicked = { planId ->
                // TO DO 일정화면으로 이동
            })
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