package kr.tekit.lion.daongil.domain.model

data class ReviewDetail (
    val nickname: String,
    val content : String,
    val date : String,
    val imageList : List<String>
)