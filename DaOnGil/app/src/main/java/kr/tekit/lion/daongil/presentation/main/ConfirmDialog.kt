package kr.tekit.lion.daongil.presentation.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.tekit.lion.daongil.databinding.DialogConfirmBinding

class ConfirmDialog(
        confirmDialogInterface: ConfirmDialogInterface,
        title: String,
        subtitle: String,
        yesButtonTitle: String)
    : DialogFragment() {

    private var _binding: DialogConfirmBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: ConfirmDialogInterface? = null

    private var title: String? = null
    private var subtitle: String? = null
    private var yesButtonTitle: String? = null

    init {
        this.title = title
        this.subtitle = subtitle
        this.yesButtonTitle = yesButtonTitle
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
        binding.yesButton.text = yesButtonTitle

        // 취소 버튼 클릭
        binding.noButton.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.yesButton.setOnClickListener {
            this.confirmDialogInterface?.onYesButtonClick()
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
    fun onYesButtonClick()
}