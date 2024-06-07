package kr.tekit.lion.daongil.domain.model

data class ReverseGecodes(
    val code: Int?,
    val results: List<ReverseGecode>,
)

data class ReverseGecode(
    val area: String?,
    val areaDetail: String?
)