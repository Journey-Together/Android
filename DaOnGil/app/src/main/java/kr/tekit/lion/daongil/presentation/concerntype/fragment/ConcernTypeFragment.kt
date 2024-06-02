package kr.tekit.lion.daongil.presentation.concerntype.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentConcernTypeBinding
import kr.tekit.lion.daongil.databinding.FragmentConcernTypeModifyBinding

class ConcernTypeFragment : Fragment(R.layout.fragment_concern_type) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentConcernTypeBinding.bind(view)

        initView(binding)
        initSelection(binding)
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

    private fun initSelection(binding: FragmentConcernTypeBinding) {
        val userConcernType = listOf("고령자")

        userConcernType.forEach { concernType ->
            when (concernType) {
                "지체장애" -> settingSelected(binding.imageViewConcernTypePhysical, R.drawable.sv_selected_physical_disability_icon)
                "시각장애" -> settingSelected(binding.imageViewConcernTypeVisual, R.drawable.sv_selected_visual_impairment_icon)
                "청각장애" -> settingSelected(binding.imageViewConcernTypeHearing, R.drawable.sv_selected_hearing_impairment_icon)
                "영유아 가족" -> settingSelected(binding.imageViewConcernTypeInfant, R.drawable.sv_selected_infant_family_icon)
                "고령자" -> settingSelected(binding.imageViewConcernTypeElderly, R.drawable.sv_selected_elderly_people_icon)
            }
        }
    }

    private fun settingSelected(imageView: ImageView, selectedDrawable: Int) {
        imageView.setImageResource(selectedDrawable)
    }

    private fun moveConcernTypeModify(binding: FragmentConcernTypeBinding) {
        binding.buttonConcernType.setOnClickListener {
            findNavController().navigate(R.id.action_concernTypeFragment_to_concernTypeModifyFragment)
        }
    }
}