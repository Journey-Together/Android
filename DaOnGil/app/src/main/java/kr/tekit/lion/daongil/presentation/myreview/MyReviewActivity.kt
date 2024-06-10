package kr.tekit.lion.daongil.presentation.myreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityMyReviewBinding

class MyReviewActivity : AppCompatActivity() {

    private val binding: ActivityMyReviewBinding by lazy {
        ActivityMyReviewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}