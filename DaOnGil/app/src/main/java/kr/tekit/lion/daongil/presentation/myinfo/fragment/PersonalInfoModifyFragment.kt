package kr.tekit.lion.daongil.presentation.myinfo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentPersonalInfoModifyBinding
import kr.tekit.lion.daongil.presentation.ext.showSoftInput

class PersonalInfoModifyFragment : Fragment(R.layout.fragment_personal_info_modify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPersonalInfoModifyBinding.bind(view)

        initView(binding)
        PersonalInfoModify(binding)
        setupErrorHandling(binding)
    }

    private fun initView(binding: FragmentPersonalInfoModifyBinding) {
        with(binding) {
            toolbarPersonalInfoModify.apply {
                setNavigationIcon(R.drawable.back_icon)
                setNavigationOnClickListener {
                    findNavController().navigate(R.id.action_personalInfoModifyFragment_to_myInfoFragment, null)
                }
            }
        }
    }

    private fun PersonalInfoModify(binding: FragmentPersonalInfoModifyBinding) {
        with(binding) {
            buttonPersonalInfoSubmit.setOnClickListener {
                if (isFormValid(binding)) {
                    showSnackbar(binding, "개인 정보가 수정되었습니다.")
                    findNavController().navigate(R.id.action_personalInfoModifyFragment_to_myInfoFragment, null)
                }
            }
        }
    }

    private fun showSnackbar(binding: FragmentPersonalInfoModifyBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            .show()
    }

    private fun isFormValid(binding: FragmentPersonalInfoModifyBinding): Boolean {
        var isValid = true

        if (binding.textFieldUserNickname.text.isNullOrBlank()) {
            view?.requestFocus()
            context?.showSoftInput(binding.textFieldUserNickname)
            binding.textInputLayoutUserNickname.error = "닉네임을 입력해주세요."
            isValid = false
        }

        return isValid
    }

    private fun setupErrorHandling(binding: FragmentPersonalInfoModifyBinding) {
        binding.apply {
            textFieldUserNickname.doOnTextChanged { text, start, before, count ->
                textInputLayoutUserNickname.isErrorEnabled = false
            }
        }
    }
}