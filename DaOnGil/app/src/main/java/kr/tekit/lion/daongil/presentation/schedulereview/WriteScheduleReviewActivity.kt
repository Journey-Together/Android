package kr.tekit.lion.daongil.presentation.schedulereview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityWriteScheduleReviewBinding

class WriteScheduleReviewActivity : AppCompatActivity() {
    private val binding : ActivityWriteScheduleReviewBinding by lazy {
        ActivityWriteScheduleReviewBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val planId = intent.getIntExtra("planId", -1)

        initView()
    }

    private fun initView(){
        binding.apply {
            toolbarWriteScheReview.setNavigationOnClickListener {
                finish()
            }

            textViewWriteScheReviewName.text = "일정 제목"
            textViewWriteScheReviewPeriod.text = getString(R.string.text_schedule_period, "2024.01.01", "2024.01.02")
//            imageViewWriteScheReviewThumb
        }
    }
}