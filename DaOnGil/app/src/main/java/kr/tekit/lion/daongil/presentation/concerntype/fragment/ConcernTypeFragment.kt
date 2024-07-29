package kr.tekit.lion.daongil.presentation.concerntype.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentConcernTypeBinding
import kr.tekit.lion.daongil.domain.model.ConcernType
import kr.tekit.lion.daongil.presentation.concerntype.vm.ConcernTypeViewModel
import kr.tekit.lion.daongil.presentation.concerntype.vm.ConcernTypeViewModelFactory

class ConcernTypeFragment : Fragment(R.layout.fragment_concern_type) {

    private val viewModel: ConcernTypeViewModel by activityViewModels { ConcernTypeViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentConcernTypeBinding.bind(view)

        val nickName = requireActivity().intent.getStringExtra("nickName")

        initView(binding, nickName)
        observeSelection(binding)
        moveConcernTypeModify(binding)
    }

    private fun initView(binding: FragmentConcernTypeBinding, nickName: String?) {
        with(binding) {
            toolbarConcernType.setNavigationIcon(R.drawable.back_icon)
            toolbarConcernType.setNavigationOnClickListener {
                requireActivity().finish()
            }

            textViewConcernTypeUseNickname.text = nickName ?: ""
        }
    }

    private fun observeSelection(binding: FragmentConcernTypeBinding) {
        viewModel.concernType.observe(viewLifecycleOwner) { concernType ->
            initSelection(binding, concernType)
        }
    }

    private fun initSelection(binding: FragmentConcernTypeBinding, concernType: ConcernType) {
        with(binding) {
            if (concernType.isPhysical) {
                settingSelected(imageViewConcernTypePhysical, R.drawable.sv_selected_physical_disability_icon)
            }
            if (concernType.isVisual) {
                settingSelected(imageViewConcernTypeVisual, R.drawable.sv_selected_visual_impairment_icon)
            }
            if (concernType.isHear) {
                settingSelected(imageViewConcernTypeHearing, R.drawable.sv_selected_hearing_impairment_icon)
            }
            if (concernType.isChild) {
                settingSelected(imageViewConcernTypeInfant, R.drawable.sv_selected_infant_family_icon)
            }
            if (concernType.isElderly) {
                settingSelected(imageViewConcernTypeElderly, R.drawable.sv_selected_elderly_people_icon)
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