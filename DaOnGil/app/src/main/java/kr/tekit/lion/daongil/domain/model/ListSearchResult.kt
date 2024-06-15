package kr.tekit.lion.daongil.domain.model

data class ListSearchResult (
    val place: AroundPlace,
    val isLastPage: Boolean = false
)