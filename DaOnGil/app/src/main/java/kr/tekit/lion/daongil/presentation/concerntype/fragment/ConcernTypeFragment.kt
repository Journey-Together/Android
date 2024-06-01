package kr.tekit.lion.daongil.presentation.concerntype.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentConcernTypeBinding

class ConcernTypeFragment : Fragment(R.layout.fragment_concern_type) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentConcernTypeBinding.bind(view)

        initView(binding)
        moveConcernTypeModify(binding)
    }

    private fun initView(binding: FragmentConcernTypeBinding) {
        with(binding) {
            toolbarConcernType.setNavigationIcon(R.drawable.back_icon)
            toolbarConcernType.setNavigationOnClickListener {
                requireActivity().finish()
            }

            textViewConcernTypeUseNickname.text = "다온길"
        }
    }

    private fun moveConcernTypeModify(binding: FragmentConcernTypeBinding) {
        binding.buttonConcernType.setOnClickListener {
            findNavController().navigate(R.id.action_concernTypeFragment_to_concernTypeModifyFragment)
        }
    }
}