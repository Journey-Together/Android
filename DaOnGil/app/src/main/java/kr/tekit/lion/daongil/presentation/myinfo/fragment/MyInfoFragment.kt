package kr.tekit.lion.daongil.presentation.myinfo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
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
            toolbarMyInfo.setNavigationOnClickListener { requireActivity().finish() }

            repeatOnViewStarted {
                viewModel.myInfo.collect{
                    tvName.text = it.name
                    tvPhone.text = it.phone ?: "전화번호를 입력해주세요"
                    tvBirth.text = it.birth ?: "생년월일을 입력해주세요"
                    tvBloodType.text = it.bloodType ?: "혈액형을 입력해주세요"
                    tvDisease.text = it.disease ?: "가지고 계신 질환을 입력해주세요"
                    tvAllergy.text = it.allergy ?: "가지고 계신 알레르기 또는 질환을 입력해주세요"
                    tvMedicine.text = it.medication ?: "투약중이신 약을 입력해주세요."
                    tvRelation1.text = it.part1Rel ?: "관계"
                    tvContact1.text = it.part1Phone ?: "긴급 연락처를 입력해주세요"
                    tvRelation2.text = it.part2Rel ?: "관계"
                    tvContact2.text = it.part2Phone ?: "긴급 연락처를 입력해주세요"
                }
            }
        }
        movePersonalInfoModify(binding)
        moveIceModify(binding)
    }


    private fun movePersonalInfoModify(binding: FragmentMyInfoBinding) {
        with(binding) {
            btnPersonalInfoModify.setOnClickListener {
                findNavController().navigate(R.id.action_myInfoFragment_to_personalInfoModifyFragment)
            }
        }
    }

    private fun moveIceModify(binding: FragmentMyInfoBinding) {
        with(binding) {
            bntIceModify.setOnClickListener {
                findNavController().navigate(R.id.action_myInfoFragment_to_iceModifyFragment)
            }
        }
    }
}