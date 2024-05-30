package kr.tekit.lion.daongil.presentation.onboarding.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentOnBoardingLastBinding
import kr.tekit.lion.daongil.presentation.login.LoginActivity
import kr.tekit.lion.daongil.presentation.main.MainActivity

class OnBoardingLastFragment : Fragment(R.layout.fragment_on_boarding_last) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOnBoardingLastBinding.bind(view)

        binding.onBoardingStartButton.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.onBoardingLoginTextView.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}