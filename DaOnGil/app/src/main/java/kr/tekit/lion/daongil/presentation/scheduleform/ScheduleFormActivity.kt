package kr.tekit.lion.daongil.presentation.scheduleform

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.databinding.ActivityScheduleFormBinding
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModelFactory

class ScheduleFormActivity : AppCompatActivity() {
    private val scheduleFormViewModel : ScheduleFormViewModel by viewModels { ScheduleFormViewModelFactory() }

    private val binding: ActivityScheduleFormBinding by lazy {
        ActivityScheduleFormBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}