package kr.tekit.lion.daongil.presentation.login.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentLoginBinding
import kr.tekit.lion.daongil.presentation.ext.repeatOnViewStarted
import kr.tekit.lion.daongil.presentation.login.vm.LoginViewModel
import kr.tekit.lion.daongil.presentation.login.vm.LoginViewModelFactory
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)

        with(binding) {

            kakaoLoginButton.setOnClickListener {
                observeDoLogin()
            }

            naverLoginButton.setOnClickListener {
                naverLogin()
            }
        }
    
    repeatOnViewStarted {
            viewModel.sigInInUiState.collectLatest {
                if (it) Navigation.findNavController(view).navigate(
                    R.id.to_selectInterestFragment
                )

            }
        }
    }

    private fun observeDoLogin() = repeatOnViewStarted {
        UserApiClient.login(requireContext())
            .onSuccess {
                viewModel.onCompleteSignIn("KAKAO", "Bearer ${it.accessToken}")
            }
    }


    suspend fun UserApiClient.Companion.login(context: Context): Result<OAuthToken> = runCatching {
        if (instance.isKakaoTalkLoginAvailable(context)) {
            try {
                UserApiClient.loginWithKakaoTalk(context)
            } catch (error: Throwable) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    throw error
                } else {
                    UserApiClient.loginWithKakaoAccount(context)
                }
            }
        } else {
            UserApiClient.loginWithKakaoAccount(context)
        }
    }

    // 카카오톡으로 로그인 시도
    private suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoTalk(context) { token, error ->
                continuation.resumeTokenOrException(token, error)
            }
        }

    // 카카오 계정으로 로그인 시도
    private suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken =
        suspendCoroutine { continuation ->
            instance.loginWithKakaoAccount(context) { token, error ->
                continuation.resumeTokenOrException(token, error)
            }
        }

    private fun Continuation<OAuthToken>.resumeTokenOrException(
        token: OAuthToken?,
        error: Throwable?
    ) {
        if (error != null) {
            resumeWithException(error)
        } else if (token != null) {
            resume(token)
        } else {
            resumeWithException(RuntimeException("Can't Receive Kakao Access Token"))
        }
    }

    private fun naverLogin() {

        val TAG = "test12345"

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {

            override fun onSuccess(result: NidProfileResponse) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.onCompleteSignIn("NAVER","Bearer ${NaverIdLoginSDK.getAccessToken()}")
                }
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.d(TAG, "네이버 로그인 실패 : \"에러코드 : ${errorCode}\" + \"에러 이유 : ${errorDescription}\"")
            }
        }

        val oauthLoginCallback = object : OAuthLoginCallback {

            override fun onSuccess() {
                viewLifecycleOwner.lifecycleScope.launch {
                    //로그인 유저 정보 가져오기
                    NidOAuthLogin().callProfileApi(profileCallback)
                    Log.d(
                        TAG,
                        "네이버 로그인 성공 ${NaverIdLoginSDK.getAccessToken()}, ${NaverIdLoginSDK.getRefreshToken()}, ${
                            NidOAuthLogin().callProfileApi(profileCallback)}"
                    )
                }
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.d(TAG, "네이버 로그인 실패 : \"에러코드 : ${errorCode}\" + \"에러 이유 : ${errorDescription}\"")
            }
        }
        NaverIdLoginSDK.authenticate(requireActivity(), oauthLoginCallback)
    }

}