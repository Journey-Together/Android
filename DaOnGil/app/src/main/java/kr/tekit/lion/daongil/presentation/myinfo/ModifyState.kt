package kr.tekit.lion.daongil.presentation.myinfo

sealed class ModifyState {
    data object ImgSelected : ModifyState()
    data object ImgUnSelected : ModifyState()
}