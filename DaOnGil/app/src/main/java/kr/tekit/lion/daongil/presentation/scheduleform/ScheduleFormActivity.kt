package kr.tekit.lion.daongil.presentation.scheduleform

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityScheduleFormBinding
import kr.tekit.lion.daongil.presentation.scheduleform.vm.ScheduleFormViewModel

class ScheduleFormActivity : AppCompatActivity() {
    private val scheduleFormViewModel : ScheduleFormViewModel by viewModels()

    private val binding: ActivityScheduleFormBinding by lazy {
        ActivityScheduleFormBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentScheduleForm) as NavHostFragment
        val navController = navHostFragment.navController

        val toolbar = binding.toolbarAddSchedule
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)

        toolbar.setNavigationIcon(R.drawable.back_icon)
        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    // 뒤로가기 버튼
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentScheduleForm) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}