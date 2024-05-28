package kr.tekit.lion.daongil.presentation.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.DialogConfirmBinding

class ConfirmDialog(
    confirmDialogInterface: ConfirmDialogInterface,
    title: String,
    subtitle: String,
    posBtnTitle: String,
    posBtnColorResId: Int,
    posBtnTextColorResId: Int)
    : DialogFragment() {

    private var _binding: DialogConfirmBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: ConfirmDialogInterface? = null

    private var title: String? = null
    private var subtitle: String? = null
    private var posBtnTitle: String? = null
    private var posBtnColorResId: Int? = null
    private var posBtnTextColorResId: Int? = null

    init {
        this.title = title
        this.subtitle = subtitle
        this.posBtnTitle = posBtnTitle
        this.posBtnColorResId = posBtnColorResId
        this.posBtnTextColorResId = posBtnTextColorResId
        this.confirmDialogInterface = confirmDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogConfirmBinding.inflate(inflater, container, false)
        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.textVeiwDialogTitle.text = title
        binding.textVeiwDialogSubtitle.text = subtitle
        binding.buttonPositivie.text = posBtnTitle
        binding.buttonPositivie.backgroundTintList = ContextCompat.getColorStateList(requireContext(), posBtnColorResId ?: R.color.primary)
        binding.buttonPositivie.setTextColor(ContextCompat.getColor(requireContext(), posBtnTextColorResId ?: R.color.text_primary))

        // 취소 버튼 클릭
        binding.buttonNegative.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.buttonPositivie.setOnClickListener {
            this.confirmDialogInterface?.onPosBtnClick()
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