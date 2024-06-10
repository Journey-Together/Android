package kr.tekit.lion.daongil.presentation.myinfo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentMyInfoBinding

class MyInfoFragment : Fragment(R.layout.fragment_my_info) {

    private val isExistUserNumber = false
    private val isExistIceInfo = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMyInfoBinding.bind(view)

        initView(binding)
        movePersonalInfoModify(binding)
        moveIceModify(binding)
    }

    private fun initView(binding: FragmentMyInfoBinding) {
        with(binding) {
            toolbarMyInfo.apply {
                setNavigationIcon(R.drawable.back_icon)
                setNavigationOnClickListener {
                    requireActivity().finish()
                }
            }

            if (isExistUserNumber) {
                textViewMyInfoUserName.text = "김사자"
                textViewMyInfoUserPhoneNumber.text = "010-1234-5678"
                textViewMyInfoUserNickname.text = "다온길"
            } else {
                textViewMyInfoUserName.text = "김사자"
                textViewMyInfoUserPhoneNumber.text = "전화번호를 입력해주세요"
                textViewMyInfoUserNickname.text = "다온길"
            }

            if(isExistIceInfo) {
                textViewMyInfoBirthday.text = "1999.12.31"
                textViewMyInfoBloodType.text = "O+"
                textViewMyInfoDisease.text = "없음"
                textViewMyInfoAllergy.text = "없음"
                textViewMyInfoMedicine.text = "없음"
                textViewMyInfoRelation1.text = "엄마"
                textViewMyInfoContact1.text = "010-4321-9876"
                textViewMyInfoRelation2.text = "아빠"
                textViewMyInfoContact2.text = "010-8520-3698"
            } else {
                textViewMyInfoBirthday.text = "생년월일을 입력해주세요"
                textViewMyInfoBloodType.text = "혈액형을 입력해주세요"
                textViewMyInfoDisease.text = "질환을 입력해주세요"
                textViewMyInfoAllergy.text = "알레르기 및 부작용을 입력해주세요"
                textViewMyInfoMedicine.text = "투여약을 입력해주세요"
                textViewMyInfoRelation1.text = "긴급 연락처를 입력해주세요"
                textViewMyInfoContact1.isVisible = false
                layoutMyInfoRelation2.isVisible = false
            }
        }
    }

    private fun movePersonalInfoModify(binding: FragmentMyInfoBinding) {
        with(binding) {
            buttonPersonalInfoModify.setOnClickListener {
                findNavController().navigate(R.id.action_myInfoFragment_to_personalInfoModifyFragment, null)
            }
        }
    }

    private fun moveIceModify(binding: FragmentMyInfoBinding) {
        with(binding) {
            buttonIceModify.setOnClickListener {
                findNavController().navigate(R.id.action_myInfoFragment_to_iceModifyFragment, null)
            }
        }
    }
}