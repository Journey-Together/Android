package kr.tekit.lion.daongil.presentation.onboarding

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.ActivityOnBoardingBinding
import kr.tekit.lion.daongil.presentation.onboarding.adapter.OnBoardingViewpagerAdapter
import kr.tekit.lion.daongil.presentation.onboarding.fragment.OnBoardingFirstFragment
import kr.tekit.lion.daongil.presentation.onboarding.fragment.OnBoardingLastFragment
import kr.tekit.lion.daongil.presentation.onboarding.fragment.OnBoardingSecondFragment

class OnBoardingActivity : AppCompatActivity() {

    private val binding : ActivityOnBoardingBinding by lazy {
        ActivityOnBoardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fragments = listOf(
            OnBoardingLastFragment()
        )

        val adapter = OnBoardingViewpagerAdapter(fragments, this)
        binding.viewpager.adapter = adapter
    }


}