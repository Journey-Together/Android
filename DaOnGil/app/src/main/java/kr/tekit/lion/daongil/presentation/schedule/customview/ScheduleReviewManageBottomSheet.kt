package kr.tekit.lion.daongil.presentation.schedule.customview

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.BottomSheetScheduleReviewManageBinding
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface

class ScheduleReviewManageBottomSheet(
    private val onReviewDeleteClickListener: () -> Unit,
    private val onReviewEditClickListener: () -> Unit,
) :
    BottomSheetDialogFragment(R.layout.bottom_sheet_schedule_review_manage),
    ConfirmDialogInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = BottomSheetScheduleReviewManageBinding.bind(view)

        initView(binding)
    }

    private fun initView(binding: BottomSheetScheduleReviewManageBinding) {
        binding.apply {
            textViewScheduleReviewManageEdit.setOnClickListener {
                onReviewEditClickListener()
            }

            textViewScheduleReviewManageDelete.setOnClickListener {
                // 리뷰 삭제 다이얼로그
                showScheduleReviewDeleteDialog()
            }
        }
    }

    private fun showScheduleReviewDeleteDialog() {
        val dialog = ConfirmDialog(
            this,
            "여행 후기 삭제",
            "삭제한 후기는 되돌릴 수 없습니다.",
            "삭제하기",
            R.color.button_tertiary,
            R.color.white
        )
        dialog.isCancelable = false
        dialog.show(requireActivity().supportFragmentManager, "ScheduleReviewManageDialog")
    }

    override fun onPosBtnClick() {
        onReviewDeleteClickListener()
        dismiss()
    }

    override fun getTheme(): Int {
        return R.style.category_bottom_sheet_dialog_theme
    }
}