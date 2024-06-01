package kr.tekit.lion.daongil.presentation.concerntype.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentConcernTypeModifyBinding

class ConcernTypeModifyFragment : Fragment(R.layout.fragment_concern_type_modify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentConcernTypeModifyBinding.bind(view)

        initView(binding)
        concernTypeModify(binding)
    }

    private fun initView(binding: FragmentConcernTypeModifyBinding) {
        binding.toolbarConcernTypeModify.setNavigationIcon(R.drawable.back_icon)
        binding.toolbarConcernTypeModify.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_concernTypeModifyFragment_to_concernTypeFragment)
        }
    }

    private fun concernTypeModify(binding: FragmentConcernTypeModifyBinding) {
        binding.buttonConcernTypeModify.setOnClickListener {
            showSnackbar(binding, "관심 유형이 수정되었습니다.")
            findNavController().navigate(R.id.action_concernTypeModifyFragment_to_concernTypeFragment)
        }
    }

    private fun showSnackbar(binding: FragmentConcernTypeModifyBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            .show()
    }
}