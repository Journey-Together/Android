package kr.tekit.lion.daongil.presentation.myinfo.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyInfoBinding
import kr.tekit.lion.daongil.presentation.ext.repeatOnViewStarted
import kr.tekit.lion.daongil.presentation.myinfo.vm.MyInfoViewModel
import kr.tekit.lion.daongil.presentation.myinfo.vm.MyInfoViewModelFactory

class MyInfoFragment : Fragment(R.layout.fragment_my_info) {
    private val viewModel: MyInfoViewModel by activityViewModels { MyInfoViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyInfoBinding.bind(view)

        with(binding) {
            toolbarMyInfo.setNavigationOnClickListener {
                handleBackPress()
            }

            repeatOnViewStarted {
                viewModel.name.collect {
                    tvName.text = it
                }
            }

            repeatOnViewStarted {
                viewModel.myPersonalInfo.collect{
                    tvNickname.text = it.nickname
                    tvPhone.text = it.phone
                }
            }

            repeatOnViewStarted {
                viewModel.myIceInfo.collect{
                    tvBirth.text = it.birth
                    tvBloodType.text = it.bloodType
                    tvDisease.text = it.disease
                    tvAllergy.text = it.allergy
                    tvMedicine.text = it.medication
                    tvRelation1.text = it.part1Rel
                    tvContact1.text = it.part1Phone
                    tvRelation2.text = it.part2Rel
                    tvContact2.text = it.part2Phone
                }
            }

            btnPersonalInfoModify.setOnClickListener {
                findNavController().navigate(R.id.action_myInfoFragment_to_personalInfoModifyFragment)
            }

            bntIceModify.setOnClickListener {
                findNavController().navigate(R.id.action_myInfoFragment_to_iceModifyFragment)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleBackPress()
        }
    }

    private fun handleBackPress() {
        if (viewModel.isPersonalInfoModified.value) {
            requireActivity().setResult(Activity.RESULT_OK)
        }
        requireActivity().finish()
    }
}