package kr.tekit.lion.daongil.presentation.scheduleform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.databinding.ActivityModifyScheduleFormBinding

class ModifyScheduleFormActivity : AppCompatActivity() {
    private val binding: ActivityModifyScheduleFormBinding by lazy {
        ActivityModifyScheduleFormBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

    }
}