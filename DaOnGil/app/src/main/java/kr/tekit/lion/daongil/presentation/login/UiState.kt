package kr.tekit.lion.daongil.presentation.login

sealed class UiState {
    data object Checking : UiState()
    data object LoggedIn : UiState()
    data object LoginRequired : UiState()
}