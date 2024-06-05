package kr.tekit.lion.daongil.presentation.myreview.customview

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.BottomSheetMyReviewDetailBinding
import kr.tekit.lion.daongil.presentation.main.customview.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.customview.ConfirmDialogInterface
import kr.tekit.lion.daongil.presentation.myreview.fragment.MyReviewDetailFragmentDirections

class MyReviewDetailBottomSheet : BottomSheetDialogFragment(R.layout.bottom_sheet_my_review_detail),
    ConfirmDialogInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = BottomSheetMyReviewDetailBinding.bind(view)

        initView(binding)
    }

    private fun initView(binding: BottomSheetMyReviewDetailBinding) {
        binding.layoutMyReviewModify.setOnClickListener {
            modifyMyReview()
        }

        binding.layoutMyReviewDelete.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun modifyMyReview() {
    }

    private fun showDeleteDialog() {
        val dialog = ConfirmDialog(
            this,
            "여행지 후기 삭제",
            "삭제한 데이터는 되돌릴 수 없습니다.",
            "삭제하기",
            R.color.button_tertiary,
            R.color.white)
        dialog.isCancelable = false
        dialog.show(requireActivity().supportFragmentManager, "MyPageDialog")
    }

    override fun onPosBtnClick() {
        deleteMyReview()

        dismiss()

        val action = MyReviewDetailFragmentDirections.actionMyReviewDetailFragmentToMyReviewFragment(reviewDeleted = true)
        findNavController().navigate(action)
    }

    private fun deleteMyReview() {
    }

    override fun getTheme(): Int {
        return R.style.category_bottom_sheet_dialog_theme
    }
}