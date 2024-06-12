package kr.tekit.lion.daongil.presentation.onboarding

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import kr.tekit.lion.daongil.R
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

        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash_video
        with(binding.splashVideoView){

            setVideoURI(Uri.parse(videoPath))

            setOnPreparedListener { mp ->
                val videoWidth = mp.videoWidth.toFloat()
                val videoHeight = mp.videoHeight.toFloat()
                val videoAspectRatio = videoWidth / videoHeight

                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                val screenWidth = displayMetrics.widthPixels.toFloat()
                val screenHeight = displayMetrics.heightPixels.toFloat()

                val screenAspectRatio = screenWidth / screenHeight

                val layoutParams = this.layoutParams

                if (videoAspectRatio > screenAspectRatio) {
                    layoutParams.width = screenWidth.toInt()
                    layoutParams.height = (screenWidth / videoAspectRatio).toInt()
                } else {
                    layoutParams.width = screenWidth.toInt()
                    layoutParams.height = (screenWidth / videoAspectRatio).toInt()
                }
                this.layoutParams = layoutParams

                this.start()
                repeatOnStarted {
                    delay(400)
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
    }
}