package kr.tekit.lion.daongil.presentation.main.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.DialogModeSettingBinding

class ModeSettingDialog(private val modeSettingDialogInterface: ModeSettingDialogInterface) : DialogFragment(R.layout.dialog_mode_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DialogModeSettingBinding.bind(view)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.dialogModeNegativeBtn.setOnClickListener {
            dismiss()
        }

        binding.dialogModePositiveBtn.setOnClickListener {
            modeSettingDialogInterface.onSettingBtn()
            dismiss()
        }
    }

    interface ModeSettingDialogInterface{
        fun onSettingBtn()
    }
}