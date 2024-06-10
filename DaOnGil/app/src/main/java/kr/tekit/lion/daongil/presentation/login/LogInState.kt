package kr.tekit.lion.daongil.presentation.login

sealed class LogInState {
    data object Checking : LogInState()
    data object LoggedIn : LogInState()
    data object LoginRequired : LogInState()
}