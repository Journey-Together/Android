package kr.tekit.lion.daongil.presentation.concerntype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityConcernTypeBinding

class ConcernTypeActivity : AppCompatActivity() {

    val binding: ActivityConcernTypeBinding by lazy {
        ActivityConcernTypeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}