package kr.tekit.lion.daongil.presentation.schedule.customview

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.tekit.lion.daongil.R
import kr.tekit.lion.daongil.databinding.BottomSheetScheduleManageBinding
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialog
import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface

class ScheduleManageBottomSheet(
    private val isPublic:Boolean,
    private val onScheduleStateToggleListener: () -> Unit,
    private val onScheduleDeleteClickListener: () -> Unit,
    private val onScheduleEditClickListener: () -> Unit
) : BottomSheetDialogFragment(R.layout.bottom_sheet_schedule_manage),

    ConfirmDialogInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = BottomSheetScheduleManageBinding.bind(view)

        initView(binding, isPublic)
    }

    private fun initView(binding: BottomSheetScheduleManageBinding, isPublic:Boolean) {
        binding.apply {
            when(isPublic){
                true -> {
                    iconScheduleManagePublicToggle.setImageResource(R.drawable.icon_lock)
                    textViewScheduleManagePublicToggle.text = getString(R.string.text_schedule_make_private)
                }
                false -> {
                    iconScheduleManagePublicToggle.setImageResource(R.drawable.icon_lock_open)
                    textViewScheduleManagePublicToggle.text = getString(R.string.text_schedule_make_public)
                }
            }

            layoutScheduleManageEdit.setOnClickListener {
                onScheduleEditClickListener()
            }
            layoutScheduleManageDelete.setOnClickListener {
                showScheduleDeleteDialog()
            }

            layoutScheduleManagePublicToggle.setOnClickListener {
                onScheduleStateToggleListener()
                dismiss()
            }

        }
    }

    private fun showScheduleDeleteDialog() {
        val dialog = ConfirmDialog(
            this,
            "여행 일정 삭제",
            "삭제한 일정은 되돌릴 수 없습니다.",
            "삭제하기",
            R.color.button_tertiary,
            R.color.white
        )
        dialog.isCancelable = false
        dialog.show(requireActivity().supportFragmentManager, "ScheduleManageDialog")
    }

    override fun onPosBtnClick() {
        onScheduleDeleteClickListener()
        dismiss()
    }

    override fun getTheme(): Int {
        return R.style.category_bottom_sheet_dialog_theme
    }
}