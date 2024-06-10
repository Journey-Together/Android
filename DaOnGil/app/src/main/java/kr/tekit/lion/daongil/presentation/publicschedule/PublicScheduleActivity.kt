package kr.tekit.lion.daongil.presentation.publicschedule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.databinding.ActivityPublicScheduleBinding
import kr.tekit.lion.daongil.domain.model.PublicSchedule
import kr.tekit.lion.daongil.presentation.publicschedule.adapter.PublicScheduleAdpater
import kr.tekit.lion.daongil.presentation.schedule.ScheduleActivity

class PublicScheduleActivity : AppCompatActivity() {
    private val binding : ActivityPublicScheduleBinding by lazy {
        ActivityPublicScheduleBinding.inflate(layoutInflater)
    }
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
        val publicSchedules = listOf(
            PublicSchedule(14, "오감만족 강릉 여행", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", 4, "김사자", "https://journey-together.s3.ap-northeast-2.amazonaws.com/c789b719-2ae5-4717-9cfd-2714d3f4461d/profile", "2일일정"),
            PublicSchedule(13, "천년고도 꽃길 - 경주", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", 4, "김사자", "https://journey-together.s3.ap-northeast-2.amazonaws.com/c789b719-2ae5-4717-9cfd-2714d3f4461d/profile", "5일일정"),
            PublicSchedule(11, "알록달록 여름빛 제주여행", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", 4, "김사자", "https://journey-together.s3.ap-northeast-2.amazonaws.com/c789b719-2ae5-4717-9cfd-2714d3f4461d/profile", "4일일정"),
            PublicSchedule(15, "낭만 가득한 전주 여행", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", 4, "김사자", "https://journey-together.s3.ap-northeast-2.amazonaws.com/c789b719-2ae5-4717-9cfd-2714d3f4461d/profile", "1일일정"),
            PublicSchedule(10, "제주 둘레길 여행", "http://tong.visitkorea.or.kr/cms/resource/40/2952540_image2_1.jpg", 4, "김사자", "https://journey-together.s3.ap-northeast-2.amazonaws.com/c789b719-2ae5-4717-9cfd-2714d3f4461d/profile", "3일일정"),
        )

        binding.recyclerViewPublicScheduleList.adapter = PublicScheduleAdpater(publicSchedules){ planId ->
            val intent = Intent(this, ScheduleActivity::class.java)
            intent.putExtra("planId", planId)
            startActivity(intent)
        }
    }
}