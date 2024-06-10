package kr.tekit.lion.daongil.presentation.schedulereview.customview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.DialogReviewPublicBinding

class ReviewPublicDialog(private val onReviewPublicSettingListener: (Boolean) -> Unit) :
    DialogFragment(R.layout.dialog_review_public){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DialogReviewPublicBinding.bind(view)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initView(binding)
    }

    private fun initView(binding: DialogReviewPublicBinding){
        binding.apply {
            buttonDialogReviewPublicNegative.setOnClickListener { isPublic ->
                onReviewPublicSettingListener(false)
                dismiss()
            }
            buttonDialogReviewPublicPositive.setOnClickListener {  isPublic ->
                onReviewPublicSettingListener(true)
                dismiss()
            }
        }
    }

}