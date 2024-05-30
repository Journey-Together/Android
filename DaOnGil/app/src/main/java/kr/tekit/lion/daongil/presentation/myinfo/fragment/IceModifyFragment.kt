package kr.tekit.lion.daongil.presentation.myinfo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentIceModifyBinding

class IceModifyFragment : Fragment(R.layout.fragment_ice_modify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentIceModifyBinding.bind(view)

        initView(binding)
        IceInfoModify(binding)
    }

    private fun initView(binding: FragmentIceModifyBinding) {
        with(binding) {
            toolbarIceModify.apply {
                setNavigationIcon(R.drawable.back_icon)
                setNavigationOnClickListener {
                    findNavController().navigate(R.id.action_iceModifyFragment_to_myInfoFragment, null)
                }
            }
        }

        handleTextFieldEditorActions(binding)
    }

    private fun handleTextFieldEditorActions(binding: FragmentIceModifyBinding) {
        with(binding) {
            textFieldIceRelation1.apply {
                setOnEditorActionListener { v, actionId, event ->
                    textFieldIceContact1.requestFocus()
                    true
                }
            }

            textFieldIceContact1.apply {
                setOnEditorActionListener { v, actionId, event ->
                    textFieldIceRelation2.requestFocus()
                    true
                }
            }

            textFieldIceRelation2.apply {
                imeOptions = EditorInfo.IME_ACTION_NEXT
                setOnEditorActionListener { v, actionId, event ->
                    textFieldIceContact2.requestFocus()
                    true
                }
            }
        }
    }

    private fun IceInfoModify(binding: FragmentIceModifyBinding) {
        with(binding) {
            buttonIceSubmit.setOnClickListener {
                showSnackbar(binding, "나의 응급 정보가 수정되었습니다.")
                findNavController().navigate(R.id.action_iceModifyFragment_to_myInfoFragment, null)
            }
        }
    }

    private fun showSnackbar(binding: FragmentIceModifyBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            .show()
    }
}