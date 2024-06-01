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
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.FragmentIceModifyBinding

class IceModifyFragment : Fragment(R.layout.fragment_ice_modify) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentIceModifyBinding.bind(view)

        initView(binding)
        IceInfoModify(binding)
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
            showSnackbar(binding, "나의 응급 정보가 수정되었습니다.")
            findNavController().navigate(R.id.action_iceModifyFragment_to_myInfoFragment, null)
        }
    }

    private fun showSnackbar(binding: FragmentIceModifyBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            .show()
    }
}