package kr.tekit.lion.daongil.presentation.myinfo.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentIceModifyBinding
import kr.tekit.lion.daongil.presentation.ext.repeatOnViewStarted
import kr.tekit.lion.daongil.presentation.ext.showSoftInput
import kr.tekit.lion.daongil.presentation.myinfo.vm.MyInfoViewModel
import kr.tekit.lion.daongil.presentation.myinfo.vm.MyInfoViewModelFactory

class IceModifyFragment : Fragment(R.layout.fragment_ice_modify) {
    private val viewModel: MyInfoViewModel by activityViewModels { MyInfoViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentIceModifyBinding.bind(view)

        initView(binding)
        IceInfoModify(binding)
        setupErrorHandling(binding)
    }

    private fun initView(binding: FragmentIceModifyBinding) {
        binding.toolbarIceModify.apply {
            setNavigationOnClickListener {
                findNavController().navigate(R.id.action_iceModifyFragment_to_myInfoFragment, null)
            }
        }

        settingTextField(binding)
        handleTextFieldEditorActions(binding)
    }

    private fun settingTextField(binding: FragmentIceModifyBinding) {
        val bloodType = resources.getStringArray(R.array.blood_type)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item_blood_type, bloodType)

        with(binding.tvBloodType) {
            setDropDownBackgroundResource(R.color.background_color)
            setAdapter(arrayAdapter)

            setOnClickListener {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                showDropDown()
            }
        }


        repeatOnViewStarted {
            with(binding) {
                viewModel.myInfo.collect {
                    tvBirthday.setText(it.birth)
                    tvBloodType.setText(it.bloodType)
                    tvDisease.setText(it.disease)
                    tvAllergy.setText(it.allergy)
                    tvMedicine.setText(it.medication)
                    tvRelation1.setText(it.part1Rel)
                    tvContact1.setText(it.part1Phone)
                    tvRelation2.setText(it.part2Rel)
                    tvContact2.setText(it.part2Phone)
                }
            }
        }
    }

    private fun handleTextFieldEditorActions(binding: FragmentIceModifyBinding) {
        with(binding) {
            tvBirthday.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
                ) {

                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)

                    tvBirthday.clearFocus()

                    true
                } else {
                    false
                }
            }

            tvRelation1.setOnEditorActionListener { v, actionId, event ->
                tvContact1.requestFocus()
                true
            }

            tvContact1.setOnEditorActionListener { v, actionId, event ->
                tvContact2.requestFocus()
                true
            }

            with(tvRelation2) {
                imeOptions = EditorInfo.IME_ACTION_NEXT
                setOnEditorActionListener { v, actionId, event ->
                    tvContact2.requestFocus()
                    true
                }
            }
        }
    }

    private fun IceInfoModify(binding: FragmentIceModifyBinding) {
        binding.buttonIceSubmit.setOnClickListener {
            if (areAllFieldsEmpty(binding)) {
                showSnackbar(binding, "입력된 정보가 없습니다. 정보를 입력해주세요!")
            } else if (isFormValid(binding)) {
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

    private fun isFormValid(binding: FragmentIceModifyBinding): Boolean {
        var isValid = true

        var firstInvalidField: View? = null

        val birthday = binding.tvBirthday.text.toString()
        if (birthday.isNotBlank()) {
            val birthdayPattern = "^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$"
            if (!birthday.matches(birthdayPattern.toRegex())) {
                binding.textInputLayoutBirthday.error = "올바른 생년월일 형식을 입력해주세요.\n예: 19700101"
                if (firstInvalidField == null) {
                    firstInvalidField = binding.tvBirthday
                }
                isValid = false
            }
        }

        val phoneNumber1 = binding.tvContact1.text.toString()
        val relation1 = binding.tvRelation2.text.toString()
        if (phoneNumber1.isNotBlank() || relation1.isNotBlank()) {
            if (phoneNumber1.isBlank() || relation1.isBlank()) {
                binding.tvContact1.error = "관계와 연락처를 모두 입력해주세요."
                if (firstInvalidField == null) {
                    firstInvalidField = binding.tvContact1
                }
                isValid = false
            } else {
                val phonePattern = "^010-\\d{4}-\\d{4}$"
                if (!phoneNumber1.matches(phonePattern.toRegex())) {
                    binding.tvContact1.error = "올바른 전화번호 형식을 입력해주세요.\n예: 010-1234-5678"
                    if (firstInvalidField == null) {
                        firstInvalidField = binding.tvContact1
                    }
                    isValid = false
                }
            }
        }

        val phoneNumber2 = binding.tvContact2.text.toString()
        val relation2 = binding.tvRelation2.text.toString()
        if (phoneNumber2.isNotBlank() || relation2.isNotBlank()) {
            if (phoneNumber2.isBlank() || relation2.isBlank()) {
                binding.textInputLayoutContact2.error = "관계와 연락처를 모두 입력해주세요."
                if (firstInvalidField == null) {
                    firstInvalidField = binding.tvContact2
                }
                isValid = false
            } else {
                val phonePattern = "^010-\\d{4}-\\d{4}$"
                if (!phoneNumber2.matches(phonePattern.toRegex())) {
                    binding.textInputLayoutContact2.error = "올바른 전화번호 형식을 입력해주세요.\n예: 010-1234-5678"
                    if (firstInvalidField == null) {
                        firstInvalidField = binding.tvContact2
                    }
                    isValid = false
                }
            }
        }

        if (!isValid && firstInvalidField != null) {
            firstInvalidField.requestFocus()
            context?.showSoftInput(firstInvalidField)
        }

        return isValid
    }


    private fun areAllFieldsEmpty(binding: FragmentIceModifyBinding): Boolean {
        return binding.tvBirthday.text.isNullOrBlank() &&
                binding.tvRelation1.text.isNullOrBlank() &&
                binding.tvContact1.text.isNullOrBlank() &&
                binding.tvRelation2.text.isNullOrBlank() &&
                binding.tvContact2.text.isNullOrBlank() &&
                binding.tvBloodType.text.isNullOrBlank()
    }

    private fun setupErrorHandling(binding: FragmentIceModifyBinding) {
        with(binding) {
            tvBirthday.doOnTextChanged { text, start, before, count ->
                textInputLayoutBirthday.isErrorEnabled = false
            }

            tvContact1.doOnTextChanged { text, start, before, count ->
                tvContact1Layout.isErrorEnabled = false
            }

            tvContact2.doOnTextChanged { text, start, before, count ->
                textInputLayoutContact2.isErrorEnabled = false
            }
        }
    }
}