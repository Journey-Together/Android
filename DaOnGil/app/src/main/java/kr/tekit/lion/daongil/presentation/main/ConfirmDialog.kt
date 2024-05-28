package kr.tekit.lion.daongil.presentation.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import kr.tekit.lion.daongil.databinding.DialogConfirmBinding

class ConfirmDialog(
    private val confirmDialogInterface: ConfirmDialogInterface,
    private val title: String,
    private val subtitle: String,
    private val posBtnTitle: String,
    private val posBtnColorResId: Int,
    private val posBtnTextColorResId: Int,
) : DialogFragment() {

    private var _binding: DialogConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogConfirmBinding.inflate(inflater, container, false)
        val view = binding.root

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

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface ConfirmDialogInterface {
    fun onPosBtnClick()
}
