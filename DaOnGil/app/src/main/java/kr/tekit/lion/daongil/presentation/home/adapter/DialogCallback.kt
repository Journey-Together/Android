package kr.tekit.lion.daongil.presentation.home.adapter

import kr.tekit.lion.daongil.presentation.main.dialog.ConfirmDialogInterface

interface DialogCallback {
    fun showConfirmDialog(dialogInterface: ConfirmDialogInterface)
}