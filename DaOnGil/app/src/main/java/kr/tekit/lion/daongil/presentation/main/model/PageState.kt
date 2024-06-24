package kr.tekit.lion.daongil.presentation.main.model

sealed class PageState {
    data object Loading : PageState()
    data object Valid : PageState()
    data object Invalid : PageState()
}
