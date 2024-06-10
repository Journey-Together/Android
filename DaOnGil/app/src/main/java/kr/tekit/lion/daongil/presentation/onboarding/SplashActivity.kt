package kr.tekit.lion.daongil.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.databinding.ActivitySplashBinding
import kr.tekit.lion.daongil.presentation.ext.repeatOnStarted
import kr.tekit.lion.daongil.presentation.login.LogInState
import kr.tekit.lion.daongil.presentation.main.MainActivity
import kr.tekit.lion.daongil.presentation.onboarding.vm.SplashViewModel
import kr.tekit.lion.daongil.presentation.onboarding.vm.SplashViewModelFactory

class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels { SplashViewModelFactory(this) }

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        repeatOnStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is LogInState.LoggedIn -> {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }

                    is LogInState.LoginRequired -> {
                        startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                    }

                    is LogInState.Checking -> {
                        return@collect
                    }
                }
            }
        }
    }
}