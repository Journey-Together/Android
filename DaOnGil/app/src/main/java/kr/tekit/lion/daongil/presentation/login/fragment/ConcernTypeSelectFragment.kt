package kr.tekit.lion.daongil.presentation.login.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentConcernTypeSelectBinding
import kr.tekit.lion.daongil.domain.model.ConcernType
import kr.tekit.lion.daongil.presentation.concerntype.vm.ConcernTypeViewModel
import kr.tekit.lion.daongil.presentation.concerntype.vm.ConcernTypeViewModelFactory
import kr.tekit.lion.daongil.presentation.main.MainActivity

class ConcernTypeSelectFragment : Fragment(R.layout.fragment_concern_type_select) {

    private val viewModel: ConcernTypeViewModel by activityViewModels { ConcernTypeViewModelFactory() }

    private val selectedConcernType = mutableSetOf<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentConcernTypeSelectBinding.bind(view)

        initSelectConcernType(binding)
        observeSelection(binding)
        concernTypeSelect(binding)
    }

    private fun initSelectConcernType(binding: FragmentConcernTypeSelectBinding) {
        with(binding) {
            physicalDisabilityImageView.setOnClickListener {
                toggleSelection(it as ImageView, R.drawable.physical_no_select, R.drawable.physical_select, binding)
            }

            visualImpairmentImageView.setOnClickListener {
                toggleSelection(it as ImageView, R.drawable.visual_no_select, R.drawable.visual_select, binding)
            }

            hearingImpairmentImageView.setOnClickListener {
                toggleSelection(it as ImageView, R.drawable.hearing_no_select, R.drawable.hearing_select, binding)
            }

            infantFamilyImageView.setOnClickListener {
                toggleSelection(it as ImageView, R.drawable.infant_family_no_select, R.drawable.infant_family_select, binding)
            }

            elderlyPeopleImageView.setOnClickListener {
                toggleSelection(it as ImageView, R.drawable.elderly_people_no_select, R.drawable.elderly_people_select, binding)
            }
        }
    }

    private fun observeSelection(binding: FragmentConcernTypeSelectBinding) {
        viewModel.concernType.observe(viewLifecycleOwner) { concernType ->
            initSelectionState(binding, concernType)
        }
    }

    private fun initSelectionState(binding: FragmentConcernTypeSelectBinding, concernType: ConcernType) {
        with(binding) {
            if (concernType.isPhysical) {
                settingSelected(physicalDisabilityImageView, R.drawable.physical_select)
            }
            if (concernType.isVisual) {
                settingSelected(visualImpairmentImageView, R.drawable.visual_select)
            }
            if (concernType.isHear) {
                settingSelected(hearingImpairmentImageView, R.drawable.hearing_select)
            }
            if (concernType.isChild) {
                settingSelected(infantFamilyImageView, R.drawable.infant_family_select)
            }
            if (concernType.isElderly) {
                settingSelected(elderlyPeopleImageView, R.drawable.elderly_people_select)
            }
        }
        updateCompleteButtonState(binding)
    }

    private fun settingSelected(imageView: ImageView, selectedDrawable: Int) {
        imageView.setImageResource(selectedDrawable)
        imageView.tag = "true"
        selectedConcernType.add(imageView.id)
    }

    private fun toggleSelection(imageView: ImageView, unselectedDrawable: Int, selectedDrawable: Int, binding: FragmentConcernTypeSelectBinding) {
        if (imageView.tag == "true") {
            imageView.setImageResource(unselectedDrawable)
            imageView.tag = "false"
            selectedConcernType.remove(imageView.id)
        } else {
            imageView.setImageResource(selectedDrawable)
            imageView.tag = "true"
            selectedConcernType.add(imageView.id)
        }

        updateCompleteButtonState(binding)
    }

    private fun updateCompleteButtonState(binding: FragmentConcernTypeSelectBinding) {
        binding.selectConcernTypeCompleteButton.isEnabled = selectedConcernType.isNotEmpty()
    }

    private fun concernTypeSelect(binding: FragmentConcernTypeSelectBinding) {
        with(binding) {
             selectConcernTypeCompleteButton.setOnClickListener {
                 val isPhysical = binding.physicalDisabilityImageView.tag?.toString()?.toBoolean() ?: false
                 val isHear = binding.hearingImpairmentImageView.tag?.toString()?.toBoolean() ?: false
                 val isVisual = binding.visualImpairmentImageView.tag?.toString()?.toBoolean() ?: false
                 val isChild = binding.infantFamilyImageView.tag?.toString()?.toBoolean() ?: false
                 val isElderly = binding.elderlyPeopleImageView.tag?.toString()?.toBoolean() ?: false

                 viewModel.updateConcernType(ConcernType(isPhysical, isHear, isVisual, isElderly, isChild))

                 val intent = Intent(requireActivity(), MainActivity::class.java)
                 startActivity(intent)
                 requireActivity().finish()
             }
        }
    }
}

