package kr.tekit.lion.daongil.presentation.main.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.DialogConfirmBinding

class ConfirmDialog(
    private val confirmDialogInterface: ConfirmDialogInterface,
    private val title: String,
    private val subtitle: String,
    private val posBtnTitle: String,
    private val posBtnColorResId: Int,
    private val posBtnTextColorResId: Int,
) : DialogFragment(R.layout.dialog_confirm) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DialogConfirmBinding.bind(view)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.textViewDialogTitle.text = title
        binding.textViewDialogSubtitle.text = subtitle
        binding.buttonPositive.text = posBtnTitle
        binding.buttonPositive.backgroundTintList = ContextCompat.getColorStateList(requireContext(), posBtnColorResId)
        binding.buttonPositive.setTextColor(ContextCompat.getColor(requireContext(), posBtnTextColorResId))

        binding.buttonNegative.setOnClickListener {
            dismiss()
        }

        binding.buttonPositive.setOnClickListener {
            confirmDialogInterface.onPosBtnClick()
            dismiss()
        }
    }
}

interface ConfirmDialogInterface {
    fun onPosBtnClick()
}
