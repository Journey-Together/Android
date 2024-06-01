package kr.tekit.lion.daongil.presentation.myinfo.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentIceModifyBinding
import kr.tekit.lion.daongil.presentation.ext.showSoftInput

class IceModifyFragment : Fragment(R.layout.fragment_ice_modify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentIceModifyBinding.bind(view)

        initView(binding)
        IceInfoModify(binding)
        setupErrorHandling(binding)
    }

    private fun initView(binding: FragmentIceModifyBinding) {
        binding.toolbarIceModify.apply {
            setNavigationIcon(R.drawable.back_icon)
            setNavigationOnClickListener {
                findNavController().navigate(R.id.action_iceModifyFragment_to_myInfoFragment, null)
            }
        }

        settingTextField(binding)
        handleTextFieldEditorActions(binding)
    }

    private fun settingTextField(binding: FragmentIceModifyBinding) {
        val bloodType = resources.getStringArray(R.array.blood_type)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item_blood_type, bloodType)

        with(binding.textFieldIceBloodType) {
            setDropDownBackgroundResource(R.color.background_color)
            setAdapter(arrayAdapter)

            setOnClickListener {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)

                showDropDown()
            }
        }
    }

    private fun handleTextFieldEditorActions(binding: FragmentIceModifyBinding) {
        with(binding) {
            textFieldIceBirthday.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)

                    textFieldIceBirthday.clearFocus()

                    true
                } else {
                    false
                }
            }

            textFieldIceRelation1.setOnEditorActionListener { v, actionId, event ->
                textFieldIceContact1.requestFocus()
                true
            }

            textFieldIceContact1.setOnEditorActionListener { v, actionId, event ->
                textFieldIceRelation2.requestFocus()
                true
            }

            with(textFieldIceRelation2) {
                imeOptions = EditorInfo.IME_ACTION_NEXT
                setOnEditorActionListener { v, actionId, event ->
                    textFieldIceContact2.requestFocus()
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

        val birthday = binding.textFieldIceBirthday.text.toString()
        if (birthday.isNotBlank()) {
            val birthdayPattern = "^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$"
            if (!birthday.matches(birthdayPattern.toRegex())) {
                binding.textInputLayoutBirthday.error = "올바른 생년월일 형식을 입력해주세요.\n예: 19700101"
                if (firstInvalidField == null) {
                    firstInvalidField = binding.textFieldIceBirthday
                }
                isValid = false
            }
        }

        val phoneNumber1 = binding.textFieldIceContact1.text.toString()
        val relation1 = binding.textFieldIceRelation1.text.toString()
        if (phoneNumber1.isNotBlank() || relation1.isNotBlank()) {
            if (phoneNumber1.isBlank() || relation1.isBlank()) {
                binding.textInputLayoutContact1.error = "관계와 연락처를 모두 입력해주세요."
                if (firstInvalidField == null) {
                    firstInvalidField = binding.textFieldIceContact1
                }
                isValid = false
            } else {
                val phonePattern = "^010-\\d{4}-\\d{4}$"
                if (!phoneNumber1.matches(phonePattern.toRegex())) {
                    binding.textInputLayoutContact1.error = "올바른 전화번호 형식을 입력해주세요.\n예: 010-1234-5678"
                    if (firstInvalidField == null) {
                        firstInvalidField = binding.textFieldIceContact1
                    }
                    isValid = false
                }
            }
        }

        val phoneNumber2 = binding.textFieldIceContact2.text.toString()
        val relation2 = binding.textFieldIceRelation2.text.toString()
        if (phoneNumber2.isNotBlank() || relation2.isNotBlank()) {
            if (phoneNumber2.isBlank() || relation2.isBlank()) {
                binding.textInputLayoutContact2.error = "관계와 연락처를 모두 입력해주세요."
                if (firstInvalidField == null) {
                    firstInvalidField = binding.textFieldIceContact2
                }
                isValid = false
            } else {
                val phonePattern = "^010-\\d{4}-\\d{4}$"
                if (!phoneNumber2.matches(phonePattern.toRegex())) {
                    binding.textInputLayoutContact2.error = "올바른 전화번호 형식을 입력해주세요. 예: 010-1234-5678"
                    if (firstInvalidField == null) {
                        firstInvalidField = binding.textFieldIceContact2
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
        return binding.textFieldIceBirthday.text.isNullOrBlank() &&
                binding.textFieldIceRelation1.text.isNullOrBlank() &&
                binding.textFieldIceContact1.text.isNullOrBlank() &&
                binding.textFieldIceRelation2.text.isNullOrBlank() &&
                binding.textFieldIceContact2.text.isNullOrBlank() &&
                binding.textFieldIceBloodType.text.isNullOrBlank()
    }

    private fun setupErrorHandling(binding: FragmentIceModifyBinding) {
        with(binding) {
            textFieldIceBirthday.doOnTextChanged { text, start, before, count ->
                textInputLayoutBirthday.isErrorEnabled = false
            }

            textFieldIceContact1.doOnTextChanged { text, start, before, count ->
                textInputLayoutContact1.isErrorEnabled = false
            }

            textFieldIceContact2.doOnTextChanged { text, start, before, count ->
                textInputLayoutContact2.isErrorEnabled = false
            }
        }
    }
}