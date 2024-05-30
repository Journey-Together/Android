package kr.tekit.lion.daongil.presentation.login.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        // NavController 한 번만 찾기
        val navController = findNavController()

        // 카카오 로그인 버튼에 클릭 리스너 설정
        binding.kakaoLoginButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSelectInterestFragment()
            // Navigation.findNavController(view).navigate(action)
            navController.navigate(action)
        }

        // 네이버 로그인 버튼에 클릭 리스너 설정
        binding.naverLoginButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSelectInterestFragment()
            // Navigation.findNavController(view).navigate(action)
            navController.navigate(action)
        }
    }

}