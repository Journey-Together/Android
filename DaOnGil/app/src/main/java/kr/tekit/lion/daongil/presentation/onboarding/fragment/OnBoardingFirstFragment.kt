package kr.tekit.lion.daongil.presentation.onboarding.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentOnBoardingFirstBinding
import kr.tekit.lion.daongil.databinding.FragmentOnBoardingLastBinding

class OnBoardingFirstFragment : Fragment(R.layout.fragment_on_boarding_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOnBoardingFirstBinding.bind(view)
    }
}