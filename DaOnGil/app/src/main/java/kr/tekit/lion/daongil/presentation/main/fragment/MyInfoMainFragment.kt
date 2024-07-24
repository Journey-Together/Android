package kr.tekit.lion.daongil.presentation.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyInfoMainBinding
import kr.tekit.lion.daongil.presentation.bookmark.BookmarkActivity
import kr.tekit.lion.daongil.presentation.concerntype.ConcernTypeActivity
import kr.tekit.lion.daongil.presentation.ext.repeatOnViewStarted
import kr.tekit.lion.daongil.presentation.login.LogInState
import kr.tekit.lion.daongil.presentation.login.LoginActivity
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.main.vm.MyInfoMainViewModel
import kr.tekit.lion.daongil.presentation.main.vm.MyInfoMainViewModelFactory
import kr.tekit.lion.daongil.presentation.myinfo.DeleteUserActivity
import kr.tekit.lion.daongil.presentation.myinfo.MyInfoActivity
import kr.tekit.lion.daongil.presentation.myreview.MyReviewActivity

class MyInfoMainFragment : Fragment(R.layout.fragment_my_info_main), ConfirmDialogInterface {
    private val viewModel: MyInfoMainViewModel by activityViewModels {
        MyInfoMainViewModelFactory(requireContext())
    }

    private var originalStatusBarColor: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyInfoMainBinding.bind(view)

        repeatOnViewStarted {
            viewModel.loginState.collect { uiState ->
                when (uiState) {
                    is LogInState.Checking -> {
                        return@collect
                    }
                    is LogInState.LoggedIn -> {
                        setUiLoggedInState(binding)
                        viewModel.onStateLoggedIn()
                    }
                    is LogInState.LoginRequired -> {
                        setUiLoginRequiredState(binding)
                    }
                }
            }
        }

        changeStatusBarColor()
    }

    private fun setUiLoggedInState(binding: FragmentMyInfoMainBinding) {
        moveMyInfo(binding)
        logoutDialog(binding)
        moveConcernType(binding)
        moveBookmark(binding)
        moveMyReview(binding)
        moveDeleteUser(binding)
        with(binding) {
            repeatOnViewStarted {
                viewModel.myInfo.collect {

                    tvNameOrLogin.text = it.name
                    tvReviewCnt.text = it.reviewNum.toString()
                    tvRegisteredData.text = "${it.date + 1}일째"

                    Glide.with(binding.imgProfile.context)
                        .load(it.profileImg)
                        .fallback(R.drawable.default_profile)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imgProfile)
                }
            }
        }
    }

    private fun setUiLoginRequiredState(binding: FragmentMyInfoMainBinding) {
        with(binding) {
            userContainer.visibility = View.GONE
            tvReview.text = getString(R.string.text_NameOrLogin)
            tvReviewCnt.visibility = View.GONE
            tvUserNameTitle.visibility = View.GONE
            textViewMyInfoMainRegister.visibility = View.GONE
            tvNameOrLogin.text = getString(R.string.text_myInfo_Review)
            layoutProfile.setOnClickListener {
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun changeStatusBarColor() {
        activity?.let {
            originalStatusBarColor = it.window.statusBarColor

            it.window.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.profile_background_endColor)
        }
    }

    private fun restoreStatusBarColor() {
        activity?.let {
            originalStatusBarColor?.let { color ->
                it.window.statusBarColor = color
            }
        }
    }

    private fun moveMyInfo(binding: FragmentMyInfoMainBinding) {
        binding.btnLoginOrUpdate.setOnClickListener {
            val intent = Intent(requireActivity(), MyInfoActivity::class.java)
            intent.putExtra("name", binding.tvNameOrLogin.text.toString())
            startActivity(intent)
        }
    }

    private fun moveConcernType(binding: FragmentMyInfoMainBinding) {
        binding.layoutConcernType.setOnClickListener {
            val intent = Intent(requireActivity(), ConcernTypeActivity::class.java)
            intent.putExtra("nickName", binding.tvNameOrLogin.text.toString())
            startActivity(intent)
        }
    }

    private fun moveBookmark(binding: FragmentMyInfoMainBinding) {
        binding.layoutBookmark.setOnClickListener {
            val intent = Intent(requireActivity(), BookmarkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun moveMyReview(binding: FragmentMyInfoMainBinding) {
        binding.layoutMyReview.setOnClickListener {
            val intent = Intent(requireActivity(), MyReviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun moveDeleteUser(binding: FragmentMyInfoMainBinding) {
        binding.layoutDelete.setOnClickListener {
            val intent = Intent(requireActivity(), DeleteUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logoutDialog(binding: FragmentMyInfoMainBinding) {
        binding.layoutLogout.setOnClickListener {
            val dialog = ConfirmDialog(
                this,
                "로그아웃",
                "해당 기기에서 로그아웃 됩니다.",
                "로그아웃",
                R.color.button_tertiary,
                R.color.white
            )
            dialog.isCancelable = false
            dialog.show(requireActivity().supportFragmentManager, "MyPageDialog")
        }
    }

    override fun onPosBtnClick() {
        logout()
    }

    private fun logout() {
        Snackbar.make(requireView(), "로그아웃", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        restoreStatusBarColor()
    }
}