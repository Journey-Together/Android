package kr.tekit.lion.daongil.presentation.myinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.tekit.lion.daongil.databinding.ActivityMyInfoBinding

class MyInfoActivity : AppCompatActivity() {

    private val binding: ActivityMyInfoBinding by lazy {
        ActivityMyInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}