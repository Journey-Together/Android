package kr.tekit.lion.daongil.presentation.myinfo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentPersonalInfoModifyBinding

class PersonalInfoModifyFragment : Fragment(R.layout.fragment_personal_info_modify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPersonalInfoModifyBinding.bind(view)

        initView(binding)
        PersonalInfoModify(binding)
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
                showSnackbar(binding, "개인 정보가 수정되었습니다.")
                findNavController().navigate(R.id.action_personalInfoModifyFragment_to_myInfoFragment, null)
            }
        }
    }

    private fun showSnackbar(binding: FragmentPersonalInfoModifyBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            .show()
    }
}