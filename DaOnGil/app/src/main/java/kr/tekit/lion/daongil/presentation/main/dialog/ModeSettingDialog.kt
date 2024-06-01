package kr.tekit.lion.daongil.presentation.main.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import kr.tekit.lion.daongil.HighThemeApp
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.DialogModeSettingBinding

class ModeSettingDialog() : DialogFragment(R.layout.dialog_mode_setting) {
    private val app = HighThemeApp.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DialogModeSettingBinding.bind(view)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogModeNegativeBtn.setOnClickListener {
            app.setThemePrefs("basic")
            app.setFirstLogin(false)
            dismiss()
            requireActivity().recreate()
        }

        binding.dialogModePositiveBtn.setOnClickListener {
            app.setThemePrefs("night")
            app.setFirstLogin(false)
            dismiss()
            requireActivity().recreate()
        }
    }
}