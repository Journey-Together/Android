package kr.tekit.lion.daongil.presentation.onboarding.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.presentation.ext.repeatOnViewStarted
import kr.tekit.lion.daongil.presentation.login.UiState
import kr.tekit.lion.daongil.presentation.onboarding.vm.SplashViewModel
import kr.tekit.lion.daongil.presentation.onboarding.vm.SplashViewModelFactory
import kr.tekit.lion.daongil.presentation.main.MainActivity
import kr.tekit.lion.daongil.presentation.onboarding.OnBoardingActivity

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val viewModel: SplashViewModel by viewModels { SplashViewModelFactory(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatOnViewStarted {
            viewModel.uiState.collect{ state->
                when(state){
                    is UiState.LoggedIn ->{
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                    is UiState.LoginRequired -> {
                        startActivity(Intent(requireContext(), OnBoardingActivity::class.java))
                    }
                    is UiState.Checking -> {
                        return@collect
                    }
                }
            }
        }
    }
}