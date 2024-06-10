package kr.tekit.lion.daongil.presentation.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityOnBoardingBinding
import kr.tekit.lion.daongil.presentation.onboarding.fragment.OnBoardingFragment
import kr.tekit.lion.daongil.presentation.onboarding.fragment.SplashFragment

class OnBoardingActivity : AppCompatActivity() {

    private val binding : ActivityOnBoardingBinding by lazy {
        ActivityOnBoardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 프래그먼트 트랜잭션 시작
        supportFragmentManager.beginTransaction().apply {
            // OnBoardingFirstFragment를 fragment_container (FrameLayout)에 추가 혹은 교체
            replace(R.id.onBoardingContainer, OnBoardingFragment())
            // 트랜잭션 완료 (커밋)
            commit()
        }
    }
}