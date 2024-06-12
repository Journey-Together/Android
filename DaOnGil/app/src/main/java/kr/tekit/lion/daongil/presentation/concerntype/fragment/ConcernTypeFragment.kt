package kr.tekit.lion.daongil.presentation.concerntype.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentConcernTypeBinding
import kr.tekit.lion.daongil.databinding.FragmentConcernTypeModifyBinding
import kr.tekit.lion.daongil.presentation.concerntype.vm.ConcernTypeViewModel
import kr.tekit.lion.daongil.presentation.concerntype.vm.ConcernTypeViewModelFactory

class ConcernTypeFragment : Fragment(R.layout.fragment_concern_type) {

    private val viewModel: ConcernTypeViewModel by viewModels { ConcernTypeViewModelFactory() }

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
        viewModel.concernType.observe(viewLifecycleOwner) { concernType ->
            val concernMap = mapOf(
                concernType.isPhysical to Pair(binding.imageViewConcernTypePhysical, R.drawable.sv_selected_physical_disability_icon),
                concernType.isHear to Pair(binding.imageViewConcernTypeHearing, R.drawable.sv_selected_hearing_impairment_icon),
                concernType.isVisual to Pair(binding.imageViewConcernTypeVisual, R.drawable.sv_selected_visual_impairment_icon),
                concernType.isElderly to Pair(binding.imageViewConcernTypeElderly, R.drawable.sv_selected_elderly_people_icon),
                concernType.isChild to Pair(binding.imageViewConcernTypeInfant, R.drawable.sv_selected_infant_family_icon)
            )

            concernMap.forEach { (isConcerned, viewAndDrawable) ->
                if (isConcerned) {
                    settingSelected(viewAndDrawable.first, viewAndDrawable.second)
                }
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