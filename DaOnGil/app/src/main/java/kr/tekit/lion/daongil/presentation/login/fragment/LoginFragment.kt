package kr.tekit.lion.daongil.presentation.login.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentLoginBinding
import kr.tekit.lion.daongil.presentation.login.vm.LoginViewModel
import kr.tekit.lion.daongil.presentation.login.vm.LoginViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels{ LoginViewModelFactory(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        viewModel.myInfo.observe(viewLifecycleOwner){
            Log.d("MyInfoData", it.toString())
        }

        initView(binding)
    }

    private fun initView(binding: FragmentLoginBinding) {
        with(binding) {
            // 카카오 로그인 버튼에 클릭 리스너 설정
            kakaoLoginButton.setOnClickListener {
                kakaoLogin()
            }

            naverLoginButton.setOnClickListener {
                UserApiClient.instance.unlink { error->
                    if (error != null) {
                        Toast.makeText(requireActivity(), "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(requireActivity(), "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    private fun kakaoLogin() {
        val TAG = "test12345"
        //val apiKey = BuildConfig.KAKAO_API_KEY
        val apiKey = "775fd03d3abd4e0e83b3df4d7ea313bc"

        Log.d(TAG, apiKey)
        KakaoSdk.init(requireContext(), apiKey)

        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.d("test100", "11111111111111111")
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.onCompleteSignIn("KAKAO", "Bearer ${token.accessToken}")
                    //navigateToSelectInterestFragment()
                }

                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }


        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(
                        requireContext(),
                        callback = callback
                    )
                } else if (token != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.onCompleteSignIn("KAKAO", "Bearer ${token.accessToken}")
                        //navigateToSelectInterestFragment()
                    }
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }
    }

    private fun navigateToSelectInterestFragment() {
        val navController = findNavController()
        val action = LoginFragmentDirections.actionLoginFragmentToSelectInterestFragment()
        navController.navigate(action)
    }
}